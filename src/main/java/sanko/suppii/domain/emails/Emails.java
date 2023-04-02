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

	@Builder
	public Emails(String subject) {
		this.subject = subject;
	}

}
