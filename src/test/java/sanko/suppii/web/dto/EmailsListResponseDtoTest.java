package sanko.suppii.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

import sanko.suppii.domain.emails.Emails;

public class EmailsListResponseDtoTest {

	@Test
	public void testEmailsListResponse() {
		//givenS
		String subject = "test subject";
		String text = "test text";
		Emails emails = Emails.builder().subject(subject).text(text).build();
		
		//when
		EmailsListResponseDto dto = new EmailsListResponseDto(emails);
		
		//then
		assertThat(dto.getSubject()).isEqualTo(subject);
	}

}
