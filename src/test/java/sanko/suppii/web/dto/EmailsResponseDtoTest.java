package sanko.suppii.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import sanko.suppii.domain.emails.Emails;

public class EmailsResponseDtoTest {

	@Test
	public void testEmailsResponse() {
		//given
		String subject = "test subject";
		String sender = "test@example.com";
		String text = "test text";
		Emails emails = Emails.builder()
			.subject(subject)
			.sender(sender)
			.text(text)
			.build();
		
		//when
		EmailsResponseDto dto = new EmailsResponseDto(emails);
		
		//then
		assertThat(dto.getSubject()).isEqualTo(subject);
		assertThat(dto.getSender()).isEqualTo(sender);
		assertThat(dto.getText()).isEqualTo(text);
	}

}
