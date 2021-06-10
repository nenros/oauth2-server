package pl.bmarkowski.auth_server.account.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@AllArgsConstructor
public final class RegisterUser {
    private final @NotBlank String email;
    private final @Length(min = 6, max = 12) @NotBlank  String password;
    private final @NotBlank String firstName;
    private final @NotBlank String lastName;


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RegisterUser) obj;
        return Objects.equals(this.email, that.email) &&
                Objects.equals(this.password, that.password) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstName, lastName);
    }

    @Override
    public String toString() {
        return "RegisterUser[" +
                "email=" + email + ", " +
                "password=" + password + ", " +
                "firstName=" + firstName + ", " +
                "lastName=" + lastName + ']';
    }

}
