package ru.niiar.cdr.dto;

import ru.niiar.cdr.utils.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserDto {
    @NotEmpty(message = "Поле имя пользователя не должно быть пустым")
    @Size(min=3, message = "Имя пользователя должно быть не менее 3-х символов")
    private String username;

    @NotEmpty(message = "Поле пароль не должно быть пустым")
    @Size(min=6, message = "Пароль должен быть не менее 6-х символов")
    private String password;

    @NotEmpty(message = "Поле подтверждение пароля не должно быть пустым")
    @Size(min=6, message = "Пароль должен быть не менее 6-х символов")
    private String matchingPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
