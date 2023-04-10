package sanko.suppii.service;

import java.util.*;
import java.util.stream.Collectors;
import java.lang.IllegalArgumentException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import sanko.suppii.domain.emails.Emails;
import sanko.suppii.domain.emails.EmailsRepository;
import sanko.suppii.domain.emails.EmailsConnection;
import sanko.suppii.web.dto.EmailsResponseDto;
import sanko.suppii.web.dto.EmailsListResponseDto;
import sanko.suppii.web.dto.EmailsReplyRequestDto;

@RequiredArgsConstructor
@Service
public class EmailsService {

	private final EmailsRepository emailsRepository;
	private final EmailsConnection emailsConnection;

	public List<EmailsListResponseDto> listEmails() {
		return emailsRepository.findAll()
			.stream()
			.map(EmailsListResponseDto::new)
			.collect(Collectors.toList());
	}

	public EmailsResponseDto getEmailsById(Long id) {
		Emails entity = emailsRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("no emails with id = " + id));

		return new EmailsResponseDto(entity);
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
		Emails reply = Emails.builder()
			.subject(emails.getSubject())
			.sender(emails.getSender())
			.text(requestDto.getText())
			.build();
		emailsConnection.sendEmails(reply);
		return emailsRepository.save(reply).getId();
	}

}
