package sanko.suppii.domain.emails;

import jakarta.persistence.*;

import lombok.*;

@Getter
@NoArgsConstructor
@Entity
public class Emails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String subject;

	@Column(columnDefinition = "TEXT")
	private String text;

	@Builder
	public Emails(String subject, String text) {
		this.subject = subject;
		this.text = text;
	}

}
