package sanko.suppii.domain.emails;

import java.util.*; //List, ArrayList
import java.time.LocalDateTime;

import org.junit.*; //After, Test
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailsRepositoryTest {

	@Autowired
	EmailsRepository emailsRepository;

	@After
	public void cleanup() {
		emailsRepository.deleteAll();
	}

	@Test
	public void testSaveFind() {
		emailsRepository.deleteAll();

		//given
		String subject = "test subject";
		String text = "test text";
		emailsRepository.save(
			Emails
				.builder()
				.subject(subject)
				.text(text)
				.build()
		);

		//when
		List<Emails> emailsList = emailsRepository.findAll();
	
		//then
		Emails emails = emailsList.get(0);
		assertThat(emails.getSubject()).isEqualTo(subject);
		assertThat(emails.getText()).isEqualTo(text);
	}

	@Test
	public void testDates() {
		emailsRepository.deleteAll();

		//given
		LocalDateTime now = LocalDateTime.of(2023, 4, 6, 0, 0, 0);
		emailsRepository.save(
			Emails
				.builder()
				.subject("test subject")
				.text("test text")
				.build()
		);

		//when
		List<Emails> emailsList = emailsRepository.findAll();

		//then
		Emails emails = emailsList.get(0);
		assertThat(emails.getCreatedDate()).isAfter(now);
		assertThat(emails.getModifiedDate()).isAfter(now);
	}

}
