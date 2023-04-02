package sanko.suppii.web.dto;

import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailResponseDtoTest {

	@Test
	public void emailResponseTest() {
		//given
		String subject = "test subject";

		//when
		EmailResponseDto dto = new EmailResponseDto(subject);

		//then
		assertThat(dto.getSubject()).isEqualTo(subject);
	}

}
