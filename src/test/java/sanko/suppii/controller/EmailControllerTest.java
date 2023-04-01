package sanko.suppii.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import sanko.suppii.service.EmailService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EmailControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private EmailService emailService;

	@Test
	public void testTest() throws Exception {
		String test = "test";
		mvc.perform(get("/test"))
			.andExpect(status().isOk())
			.andExpect(content().string(test));
	}

}
