package pl.bmarkowski.auth_server.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import pl.bmarkowski.auth_server.account.UserTestFactory;

@TestConfiguration
public class TestSecurityUsers {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        final var dbUser = UserTestFactory.user();
        final var user = User.withUsername(dbUser.getEmail())
                .password(dbUser.getPassword())
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
