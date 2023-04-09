package sanko.suppii.web.dto;

import lombok.*; //Builder, Getter, NoArgsConstructor

import sanko.suppii.domain.emails.Emails;

@Getter
@NoArgsConstructor
public class EmailsReplyRequestDto {

	private String text;

	@Builder
	public EmailsReplyRequestDto(String text) {
		this.text = text;
	}

}
