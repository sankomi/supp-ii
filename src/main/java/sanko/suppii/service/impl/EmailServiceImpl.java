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
			string += emails.getSubject() + "<br>";
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

}
