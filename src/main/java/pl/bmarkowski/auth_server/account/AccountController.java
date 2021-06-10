package pl.bmarkowski.auth_server.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.bmarkowski.auth_server.account.request.RegisterUser;
import pl.bmarkowski.auth_server.account.request.UserResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody @Validated final RegisterUser registerUser) {
        accountService.registerUser(registerUser);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/me")
    public UserResponse getUser(final @AuthenticationPrincipal UserDetails principal) {

        return accountService.findUser(principal.getUsername())
                .map(UserResponse::of).
                        orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User with email" + principal.getUsername() + "not exists."
                        ));

    }
}
