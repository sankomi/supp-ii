package sanko.suppii.domain.emails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailsRepository extends JpaRepository<Emails, Long> {

	List<Emails> findByStartIsNull();
	List<Emails> findByStartEqualsOrderByIdAsc(Long id);
	Emails findFirstByStartOrderByModifiedDateDesc(Long id);

}
