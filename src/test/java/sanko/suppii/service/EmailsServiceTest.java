package sanko.suppii.service;

import java.util.List;

import org.junit.*; //Test, After
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import sanko.suppii.domain.emails.Emails;
import sanko.suppii.domain.emails.EmailsRepository;
import sanko.suppii.web.dto.EmailsResponseDto;
import sanko.suppii.web.dto.EmailsListResponseDto;
import sanko.suppii.web.dto.EmailsReplyRequestDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailsServiceTest {

	@Autowired
	private EmailsService emailsService;

	@Autowired
	private EmailsRepository emailsRepository;

	@After
	public void cleanup() {
		emailsRepository.deleteAll();
	}

	private Long saveEmails(String subject, String text) {
		Emails emails = Emails.builder()
			.subject(subject)
			.text(text)
			.build();
		return emailsRepository.save(emails).getId();
	}

	@Test
	public void testListEmails() {
		//given
		String subject = "test subject";
		String text = "test text";
		Long id = saveEmails(subject, text);

		//when
		List<EmailsListResponseDto> responseDtoList = emailsService.listEmails();

		//then
		EmailsListResponseDto responseDto = responseDtoList.get(0);
		assertThat(responseDto.getSubject()).isEqualTo(subject);
	}

	@Test
	public void testGetEmailsById() {
		//given
		String subject = "test subject";
		String text = "test text";
		Long id = saveEmails(subject, text);

		//when
		EmailsResponseDto responseDto = emailsService.getEmailsById(id);

		//then
		assertThat(responseDto.getId()).isEqualTo(id);
		assertThat(responseDto.getSubject()).isEqualTo(subject);
		assertThat(responseDto.getText()).isEqualTo(text);
	}

	@Test
	public void testReplyEmails() {
		//given
		String subject = "test subject";
		String text = "test text";
		Long id = saveEmails(subject, text);
		
		//when
		String replyText = "test reply";
		EmailsReplyRequestDto requestDto = EmailsReplyRequestDto.builder()
			.text(replyText)
			.build();
		Long replyId = emailsService.replyEmails(id, requestDto);
		EmailsResponseDto responseDto = emailsService.getEmailsById(replyId);

		//then
		assertThat(responseDto.getId()).isEqualTo(replyId);
		assertThat(responseDto.getSubject()).isEqualTo(subject);
		assertThat(responseDto.getText()).isEqualTo(replyText);
	}

}
