package pl.bmarkowski.auth_server.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.bmarkowski.auth_server.account.request.RegisterUser;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {
    @MockBean
    AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Stream<Arguments> wrongRequests() {
        return Stream.of(
                Arguments.of(new RegisterUser("", "password", "Test", "Testerski"), "email", "must not be blank"),
                Arguments.of(new RegisterUser("email@mail.pl", "", "Test", "Testerski"), "password", "must not be blank"),
                Arguments.of(new RegisterUser("email@mail.pl", "test", "Test", "Testerski"), "password", "length must be between 6 and 12"),
                Arguments.of(new RegisterUser("email@mail.pl", "passwordtesttest", "Test", "Testerski"), "password", "length must be between 6 and 12")
        );
    }

    @Test
    public void respondWith201OnCreatingUser() throws Exception {
        final var request = UserTestFactory.registerRequest();

        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated());

        verify(accountService).registerUser(refEq(request));
    }

    @ParameterizedTest
    @MethodSource("wrongRequests")
    public void respondWithErrorOnEmptyEmail(final RegisterUser request, final String field, final String message) throws Exception {
        mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", Matchers.is(field)))
                .andExpect(jsonPath("$[0].message", Matchers.is(message)));
    }

    @Test
    public void respondWithRedirectToLogin() throws Exception {
        mockMvc.perform(get("/user/me"))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("location", "http://localhost/login"));
    }

    @Test
    @WithMockUser(username = "test@test.pl")
    public void respondWithUserDetailsForLoggedInUser() throws Exception {
        final var user = UserTestFactory.user();
        when(accountService.findUser(user.getEmail()))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", Matchers.is(user.getEmail())))
                .andExpect(jsonPath("$.fullName", Matchers.is("test testerski")));
    }

}
