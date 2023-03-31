package sanko.suppii.service.impl;

import java.util.*;
import javax.mail.*;
import javax.mail.search.FlagTerm;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import sanko.suppii.service.EmailService;

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

	@Override
	public String countMessages() {
		String[] messages = getMessages();

		String string = "";
		for (String message : messages) {
			string += message + "<br>";
		}

		return string;
	}

	private String[] getMessages() {
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

			//Message[] messages = folder.getMessages();
			Message[] messages = folder.search(
				new FlagTerm(new Flags(Flags.Flag.SEEN), false)
			);

			String[] strings = new String[messages.length];
			for (int i = 0; i < messages.length; i++) {
				strings[i] = messages[i].getSubject();
				messages[i].setFlag(Flags.Flag.SEEN, true);
			}

			folder.close();
			store.close();

			return strings;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[]{};
		}
	}

}
