package sanko.suppii.service.impl;

import java.util.*;
import javax.mail.*;

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
		Message[] messages = getMessages();
		return messages.length + " messages";
	}

	private Message[] getMessages() {
		Properties props = new Properties();
		props.put("mail.pop3.host", emailHost);
		props.put("mail.pop3.port", emailPort);
		props.put("mail.pop3.starttls.enable", true);
		props.put("mail.store.protocol", "pop3");

		Session session = Session.getInstance(props);

		try {
			Store store = session.getStore("pop3s");
			store.connect(emailHost, emailUsername, emailPassword);

			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);

			Message[] messages = folder.getMessages();

			return messages;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
