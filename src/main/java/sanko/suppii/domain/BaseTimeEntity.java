package sanko.suppii.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*; //EntityListeners, MappedSupercClass

import lombok.Getter;
import org.springframework.data.annotation.*; //CreatedDate, LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
