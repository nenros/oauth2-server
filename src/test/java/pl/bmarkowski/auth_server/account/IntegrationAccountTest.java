package pl.bmarkowski.auth_server.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationAccountTest {
    private static final String REQUEST_FORMAT = """
             {
                "email": "%s",
                "password": "%s",
                "firstName": "Test",
                "lastName": "Testerski"
             }
            """;
    private static final String EMAIL = "test@test.pl";
    private static final String PASSWORD = "password";


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void registerNewUser() {
        final var requestBody = String.format(REQUEST_FORMAT, EMAIL, PASSWORD);
        final var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        final var request = new HttpEntity<>(requestBody, headers);
        final var response = testRestTemplate.postForEntity("http://localhost:" + port + "/user", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        final var user = accountRepository.findByEmailIgnoreCase(EMAIL);

        assertThat(passwordEncoder.matches(PASSWORD, user.get().getPassword())).isTrue();
    }
}
