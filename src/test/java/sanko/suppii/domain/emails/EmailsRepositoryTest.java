package sanko.suppii.domain.emails;

import java.util.*; //List, ArrayList

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
		//given
		String subject = "test subject";

		emailsRepository.save(Emails.builder().subject(subject).build());

		//when
		List<Emails> emailsList = emailsRepository.findAll();

		//then
		Emails emails = emailsList.get(0);
		assertThat(emails.getSubject()).isEqualTo(subject);
	}

}
