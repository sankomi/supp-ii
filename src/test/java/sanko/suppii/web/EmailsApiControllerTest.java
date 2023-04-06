package sanko.suppii.web;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.*; //HttpStatus, ResponseEntity

import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

import sanko.suppii.service.EmailsService;
import sanko.suppii.web.dto.EmailsResponseDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private EmailsService emailsService;

	@Test
	public void testTest() throws Exception {
		//given
		String url = "http://localhost:" + port + "/api/v1/emails/test";
		String test = "test";

		//when
		ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

		//then
		assertThat(responseEntity.getStatusCode())
			.isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody())
			.isEqualTo(test);
	}

}

