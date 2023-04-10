package sanko.suppii.domain.emails;

import java.util.*; //List, ArrayList
import javax.mail.*; //Address, Authenticator, Message, Session, PasswordAuthentication, Transport
import javax.mail.search.FlagTerm;
import javax.mail.internet.*; //InternetAddress, MimeMessage

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.jsoup.Jsoup;

@Component
public class EmailsConnection {
	
	@Value("${email.host}")
	private String emailHost;
	@Value("${email.port}")
	private String emailPort;
	@Value("${email.smtp-host}")
	private String smtpHost;
	@Value("${email.smtp-port}")
	private String smtpPort;
	@Value("${email.username}")
	private String emailUsername;
	@Value("${email.password}")
	private String emailPassword;

	public List<Emails> fetchEmails() {
		Properties props = new Properties();
		props.put("mail.imap.host", emailHost);
		props.put("mail.imap.port", emailPort);
		props.put("mail.imap.ssl.enable", true);
		props.put("mail.store.protocol", "imap");

		Session session = Session.getInstance(props);

		try {
			Store store = session.getStore("imap");
			store.connect(emailHost, emailUsername, emailPassword);

			/* get all folders
			Folder defaultFolder = store.getDefaultFolder();
			folder[] folders = defaultFolder.list("*");
			for (Folder folder : folders) {
				System.out.println(folder.getFullName() + ": " + folder.getMessageCount());
			}
			*/

			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);

			/* get all messages
			Message[] messages = folder.getMessages();
			*/
			Message[] messages = folder.search(
				new FlagTerm(new Flags(Flags.Flag.SEEN), false)
			);

			List<Emails> emailsList = new ArrayList<>();
			for (int i = 0; i < messages.length; i++) {
				String text = getText(messages[i]);
				String cleanText = Jsoup.parse(text).text();
				Address[] froms = messages[i].getFrom();
				String sender = froms == null? null: ((InternetAddress) froms[0]).getAddress();

				emailsList.add(
					Emails.builder()
						.subject(messages[i].getSubject())
						.sender(sender)
						.text(cleanText)
						.build()
				);
				messages[i].setFlag(Flags.Flag.SEEN, true);
			}

			folder.close();
			store.close();

			return emailsList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Emails>();
		}
	}

	/**
	* Return the primary text content of the message.
	* from https://javaee.github.io/javamail/FAQ#mainbody
	*/
	private String getText(Part p) throws Exception {
		if (p.isMimeType("text/*")) {
			String s = (String)p.getContent();
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

	public void sendEmails(Emails emails) {
		Properties props = new Properties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.auth", true);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailUsername, emailPassword);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(emailUsername));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emails.getSender()));
			message.setSubject(emails.getSubject());
			message.setText(emails.getText());

			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
