package sanko.suppii.domain.user;

import jakarta.persistence.*; //Entity, Column, EnumType, Enumerated, GeneratedValue, GenerationType, Id

import lombok.*; //Builder, Getter, NoArgsConstructor

@Getter
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Builder
	public User(String name, String email, Role role) {
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public User update(String name) {
		this.name = name;

		return this;
	}

	public String getRoleKey() {
		return this.role.getKey();
	}

	public void changeRole(Role role) {
		this.role = role;
	}

}
