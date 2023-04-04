package sanko.suppii.service.impl;

import java.util.*;
import javax.mail.*;
import javax.mail.search.FlagTerm;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*; //Autowired, Value

import sanko.suppii.service.EmailService;
import sanko.suppii.domain.emails.Emails;
import sanko.suppii.domain.emails.EmailsRepository;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${email.host}")
	private String emailHost;
	@Value("${email.port}")
	private String emailPort;
	@Value("${email.username}")
	private String emailUsername;
	@Value("${email.password}")
	private String emailPassword;

	@Autowired
	private EmailsRepository emailsRepository;

	@Override
	public String listMessages() {
		List<Emails> emailsList = emailsRepository.findAll();

		String string = "";
		for (Emails emails : emailsList) {
			string += emails.getSubject() + ": " + emails.getText() + "<br>";
		}

		return string;
	}

	@Override
	public boolean fetchMessages() {
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
				emailsList.add(
					Emails.builder()
						.subject(messages[i].getSubject())
						.text(getText(messages[i]))
						.build()
				);
				messages[i].setFlag(Flags.Flag.SEEN, true);
			}
			emailsRepository.saveAll(emailsList);

			folder.close();
			store.close();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

}
