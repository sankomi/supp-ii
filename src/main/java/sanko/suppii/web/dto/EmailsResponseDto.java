package sanko.suppii.web.dto;

import lombok.Getter;

import sanko.suppii.domain.emails.Emails;

@Getter
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
