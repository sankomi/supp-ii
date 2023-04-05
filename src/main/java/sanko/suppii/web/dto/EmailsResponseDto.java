package sanko.suppii.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import sanko.suppii.domain.emails.Emails;

@Getter
@RequiredArgsConstructor
public class EmailsResponseDto {

	private final Long id;
	private final String subject;
	private final String text;

	public EmailsResponseDto(Emails entity) {
		this.id = entity.getId();
		this.subject = entity.getSubject();
		this.text = entity.getText();
	}

}
