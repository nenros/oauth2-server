package pl.bmarkowski.auth_server.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.bmarkowski.auth_server.account.Account;

@Getter
@AllArgsConstructor
public class UserResponse {
    private final String email;
    private final String fullName;
    public static UserResponse of(final Account account) {
        return new UserResponse(account.getEmail(), "test testerski");
    }
}
