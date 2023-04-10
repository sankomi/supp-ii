package sanko.suppii.domain.emails;

import jakarta.persistence.*;

import lombok.*;

import sanko.suppii.domain.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
public class Emails extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String subject;

	@Column
	private String sender;

	@Column
	private Long start;

	@Column(columnDefinition = "TEXT")
	private String text;

	@Builder
	public Emails(String subject, String sender, Long start, String text) {
		this.subject = subject;
		this.sender = sender;
		this.start = start;
		this.text = text;
	}

}
