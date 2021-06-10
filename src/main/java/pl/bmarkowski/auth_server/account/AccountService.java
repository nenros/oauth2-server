package pl.bmarkowski.auth_server.account;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bmarkowski.auth_server.account.request.RegisterUser;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(final RegisterUser request) {
     if(accountRepository.existsByEmailIgnoreCase(request.getEmail())){
         throw new IllegalArgumentException("User already exists");
     }
     final var user = Account.builder()
             .email(request.getEmail())
             .password(passwordEncoder.encode(request.getPassword()))
             .build();

     accountRepository.save(user);
    }

    public Optional<Account> findUser(final String email) {
        return accountRepository.findByEmailIgnoreCase(email);
    }
}
