package sample.models;

import java.util.Objects;

import static sample.additions.Codes.*;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
class Client {

    private String id;
    private String login;
    private String password;
    private String cash;
    private String code;

    Client(String id, String login, String password, String code) {

        this.id = id;
        this.login = login;
        this.password = password;
        this.code = code;

        checkClient();
    }

    private void checkClient() {

        if (Objects.equals(this.code, CODE_AUTHORIZATION)) {
            if (authorization()) {
                this.code = CODE_CONNECTED;
                if (Objects.equals(this.id, ""))
                    this.id = "0";
                this.cash = "63794";
            }
            else {
                this.code = CODE_ERROR_AUTHORIZATION;
                this.id = "";
                this.cash = "";
            }
        }
        if (Objects.equals(this.code, CODE_REGISTRATION)) {
            if (registration()) {
                this.code = CODE_CONNECTED;
                if (Objects.equals(this.id, ""))
                    this.id = "1";
                this.cash = "0";
            }
            else {
                this.code = CODE_ERROR_REGISTRATION;
                this.id = "";
                this.cash = "";
            }
        }
    }

    private boolean authorization() {
        return ((login.equals("GUSARITO") && password.equals("123")) ||
                (login.equals("GUSARITO1") && password.equals("123")));
    }

    private boolean registration() {
        return !login.equals("Vasya");
    }

    String getID() {
        return id;
    }

    String getLogin() {
        return login;
    }

    static String getLogin(String id) {

        String login = "";

        if (Objects.equals(id, "0"))
            login = "GUSARITO";
        if (Objects.equals(id, "1"))
            login = "GUSARITO1";

        return login;
    }

    static String getPassword(String id) {

        String password = "";

        if (Objects.equals(id, "0"))
            password = "123";
        if (Objects.equals(id, "1"))
            password = "123";

        return password;
    }

    String getCash() {
        return cash;
    }

    String getCode() {
        return code;
    }
}
