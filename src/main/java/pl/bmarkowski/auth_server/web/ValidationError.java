package pl.bmarkowski.auth_server.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public final class ValidationError {
    private final String field;
    private final String message;

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ValidationError) obj;
        return Objects.equals(this.field, that.field) &&
                Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, message);
    }

    @Override
    public String toString() {
        return "ValidationError[" +
                "field=" + field + ", " +
                "message=" + message + ']';
    }

}
