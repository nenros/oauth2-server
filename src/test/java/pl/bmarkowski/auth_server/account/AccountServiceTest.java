package pl.bmarkowski.auth_server.account;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AccountService accountService;

    @Test
    public void createNewUser() {
        final var request = UserTestFactory.registerRequest();
        final var encodedPassword = "encoded" + request.getPassword();
        final var expectedUser = Account.builder()
                .password(encodedPassword)
                .email(request.getEmail())
                .build();
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedPassword);

        accountService.registerUser(request);

        verify(accountRepository).save(refEq(expectedUser));
        verify(accountRepository).existsByEmailIgnoreCase(request.getEmail());
    }

    @Test
    public void throwExceptionWhenUserExists() {
        final var request = UserTestFactory.registerRequest();
        when(accountRepository.existsByEmailIgnoreCase(request.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> accountService.registerUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User already exists");
    }

}
