package sanko.suppii.service;

import java.util.*; //List
import java.util.stream.Collectors;
import java.lang.IllegalArgumentException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*; //Autowired, Value

import sanko.suppii.domain.emails.Emails;
import sanko.suppii.domain.emails.EmailsRepository;
import sanko.suppii.domain.emails.EmailsConnection;
import sanko.suppii.web.dto.EmailsResponseDto;
import sanko.suppii.web.dto.EmailsListResponseDto;
import sanko.suppii.web.dto.EmailsReplyRequestDto;

@RequiredArgsConstructor
@Service
public class EmailsService {

	@Value("${email.username}")
	private String emailUsername;

	private final EmailsRepository emailsRepository;
	private final EmailsConnection emailsConnection;

	public List<EmailsListResponseDto> listEmails() {
		List<Emails> emailsList = emailsRepository.findByStartIsNull();
		List<EmailsListResponseDto> responseList = new ArrayList<>();

		for (Emails emails : emailsList) {
			Emails latest = emailsRepository.findFirstByStartOrderByModifiedDateDesc(emails.getId());
			EmailsListResponseDto response = EmailsListResponseDto.builder()
				.id(emails.getId())
				.subject(emails.getSubject())
				.sender(emails.getSender())
				.modifiedDate(latest != null? latest.getModifiedDate(): emails.getModifiedDate())
				.build();
			responseList.add(response);
		}

		return responseList;
	}

	public EmailsResponseDto getEmailsById(Long id) {
		Emails entity = emailsRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("no emails with id = " + id));

		return new EmailsResponseDto(entity);
	}

	public List<EmailsResponseDto> getReplyEmailsById(Long id) {
		return emailsRepository.findByStartEqualsOrderByIdAsc(id)
			.stream()
			.map(EmailsResponseDto::new)
			.collect(Collectors.toList());
	}

	public boolean fetchEmails() {
		List<Emails> emailsList = emailsConnection.fetchEmails();
		try {
			emailsRepository.saveAll(emailsList);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Long replyEmails(Long id, EmailsReplyRequestDto requestDto) {
		Emails emails = emailsRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("no emails with id = " + id));
		Long start = emails.getStart();
		String subject;
		if (start == null) {
			start = emails.getId();
			subject = "Re: " + emails.getSubject() + " [t#" + start + "]";
		} else {
			subject = emails.getSubject();
		}
		Emails reply = Emails.builder()
			.subject(subject)
			.sender(emailUsername)
			.start(start)
			.text(requestDto.getText())
			.build();
		emailsConnection.sendEmails(emails.getSender(), reply);
		return emailsRepository.save(reply).getId();
	}

}
