package sanko.suppii.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import sanko.suppii.domain.emails.Emails;

@Getter
@RequiredArgsConstructor
public class EmailsListResponseDto {

	private final Long id;
	private final String subject;

	public EmailsListResponseDto(Emails entity) {
		this.id = entity.getId();
		this.subject = entity.getSubject();
	}

}
