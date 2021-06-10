package pl.bmarkowski.auth_server.account;


import pl.bmarkowski.auth_server.account.request.RegisterUser;

import java.util.UUID;


public class UserTestFactory {

    public static Account user() {
        return Account.builder()
                .id(UUID.randomUUID())
                .email("test@test.pl")
                .password("{noop}password")
                .build();
    }
    public static RegisterUser registerRequest() {
        return new RegisterUser(
                "test@test.pl",
                "password",
                "Test",
                "Testerski");
    }
}
