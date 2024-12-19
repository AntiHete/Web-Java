import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OAuthIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenValidJwtToken_thenAccessGranted() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer valid-jwt-token");

        ResponseEntity<String> response = restTemplate.getForEntity("/secure-endpoint", String.class, headers);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenInvalidJwtToken_thenAccessDenied() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer invalid-jwt-token");

        ResponseEntity<String> response = restTemplate.getForEntity("/secure-endpoint", String.class, headers);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
