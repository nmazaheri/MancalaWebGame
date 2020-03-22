package sample.mancala;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Basic integration tests for Mancala Game application.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class MancalaGameTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testRenderGame() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testHandleUserInput() {
		ResponseEntity<String> entity = this.restTemplate.getForEntity("/input/5", String.class);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}