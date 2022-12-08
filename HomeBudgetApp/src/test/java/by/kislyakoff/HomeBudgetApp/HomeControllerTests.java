package by.kislyakoff.HomeBudgetApp;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import by.kislyakoff.HomeBudgetApp.controllers.HomeController;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("test")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create_user_before.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create_user_after.sql"}, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class HomeControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private HomeController controller;
	
//	@Autowired
//	private WebApplicationContext context;
	
//	@BeforeEach
//	void setup() {
//		MockitoAnnotations.openMocks(this);
//	    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
//	    					.apply(springSecurity()).build();
//	}
	
	@Test
	void mainPageTest() throws Exception {
		this.mockMvc.perform(get("/homebudget"))
			.andDo(print())
			.andExpect(authenticated())
			.andExpect(xpath("//div[@id='test-case']").string("Добро пожаловать, test!"));
	}

}
