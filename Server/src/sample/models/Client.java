package sample.models;

import java.util.Objects;

import static sample.additions.Codes.*;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
class Client {

    private String login;
    private String password;
    private String code;

    Client(String login, String password, String code) {

        this.login = login;
        this.password = password;
        this.code = code;

        if (Objects.equals(code, CODE_AUTHORIZATION)) {
            if (authorization()) this.code = CODE_CONNECTED;
            else this.code = CODE_ERROR_AUTHORIZATION;
        }
        if (Objects.equals(code, CODE_REGISTRATION)) {
            if (registration()) this.code = CODE_CONNECTED;
            else this.code = CODE_ERROR_REGISTRATION;
        }
    }

    private boolean authorization() {
        return login.equals("GUSARITO") && password.equals("123");
    }

    private boolean registration() {
        return !login.equals("Vasya");
    }

    String getLogin() {
        return login;
    }

    String getCode() {
        return code;
    }
}
