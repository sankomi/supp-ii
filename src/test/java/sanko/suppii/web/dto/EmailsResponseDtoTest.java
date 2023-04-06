package sanko.suppii.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import sanko.suppii.domain.emails.Emails;

public class EmailsResponseDtoTest {

	@Test
	public void testEmailsResponse() {
		//givenS
		String subject = "test subject";
		String text = "test text";
		Emails emails = Emails.builder().subject(subject).text(text).build();
		
		//when
		EmailsResponseDto dto = new EmailsResponseDto(emails);
		
		//then
		assertThat(dto.getSubject()).isEqualTo(subject);
		assertThat(dto.getText()).isEqualTo(text);
	}

}
