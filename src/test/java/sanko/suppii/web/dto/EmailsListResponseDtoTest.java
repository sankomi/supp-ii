package sanko.suppii.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import sanko.suppii.domain.emails.Emails;

public class EmailsListResponseDtoTest {

	@Test
	public void testEmailsListResponse() {
		//givenS
		String subject = "test subject";
		String sender = "test@example.com";
		Long start = 1L;
		String text = "test text";
		Emails emails = Emails.builder()
			.subject(subject)
			.sender(sender)
			.start(start)
			.text(text)
			.build();
		
		//when
		EmailsListResponseDto dto = new EmailsListResponseDto(emails);
		
		//then
		assertThat(dto.getSubject()).isEqualTo(subject);
		assertThat(dto.getSender()).isEqualTo(sender);
	}

}
