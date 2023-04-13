package sanko.suppii.web.dto;

import java.time.LocalDateTime;

import lombok.*; //Getter, Builder

import sanko.suppii.domain.emails.Emails;

@Getter
public class EmailsListResponseDto {

	private final Long id;
	private final String subject;
	private final String sender;
	private final LocalDateTime modifiedDate;

	public EmailsListResponseDto(Emails entity) {
		this.id = entity.getId();
		this.subject = entity.getSubject();
		this.sender = entity.getSender();
		this.modifiedDate = entity.getModifiedDate();
	}

	@Builder
	public EmailsListResponseDto(Long id, String subject, String sender, LocalDateTime modifiedDate) {
		this.id = id;
		this.subject = subject;
		this.sender = sender;
		this.modifiedDate = modifiedDate;
	}

}
