package by.kislyakoff.HomeBudgetApp;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class LoginTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void unloggedAccessTest() throws Exception {
		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}

	@Test
	@Sql(value = {"/create_user_before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = {"/create_user_after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	void correctLoginTest() throws Exception {
		this.mockMvc.perform(formLogin("/process_login").user("test").password("test"))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/homebudget"));
	}
	
	@Test
	@Sql(value = {"/create_user_before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(value = {"/create_user_after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	void badCredentials() throws Exception {
		this.mockMvc.perform(post("/process_login")
								.param("username", "test")
								.param("password", "test_miss"))
			.andDo(print())
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/login?error"));
	}
}
