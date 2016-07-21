package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static sample.additions.Codes.*;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
class Client {

    private MySQLServer sql;
    private String id;
    private String login;
    private String password;
    private String cash;
    private String code;

    Client(MySQLServer sql, String id, String login, String password, String code) {

        this.sql = sql;
        this.id = id;
        this.login = login;
        this.password = password;
        this.code = code;

        checkClient();
    }

    private void checkClient() {

        if (Objects.equals(this.code, CODE_AUTHORIZATION)) {
            if (authorization()) {
                code = CODE_CONNECTED;
            }
            else {
                code = CODE_ERROR_AUTHORIZATION;
                id = "";
                cash = "";
            }
        }
        if (Objects.equals(this.code, CODE_REGISTRATION)) {
            if (registration()) {
                code = CODE_CONNECTED;
            }
            else {
                code = CODE_ERROR_REGISTRATION;
                id = "";
                cash = "";
            }
        }
    }

    private boolean authorization() {

        try {
            ResultSet res = sql.getStatement().executeQuery("SELECT * FROM persons");

            while (res.next()) {
                if (login.equals(res.getString(2)))
                    if (password.equals(res.getString(3))) {
                        id = res.getInt(1) + "";
                        cash = res.getInt(4) + "";
                        return true;
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean registration() {
        try {
            ResultSet res = sql.getStatement().executeQuery("SELECT * FROM persons");

            while (res.next()) {
                if (login.equals(res.getString(2)))
                        return false;
            }

            String query = "INSERT INTO persons (login, password, cash) VALUES (?, ?, ?)";
            sql.setPreparedStatement(sql.getConnection().prepareStatement(query));
            sql.getPreparedStatement().setString(1, login);
            sql.getPreparedStatement().setString(2, password);
            sql.getPreparedStatement().setInt(3, 0);

            sql.getPreparedStatement().execute();

            res = sql.getStatement().executeQuery("SELECT * FROM persons WHERE id=LAST_INSERT_ID();");
            if (res.next()) id = res.getInt(1) + "";
            cash = "0";
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    String getID() {
        return id;
    }

    String getLogin() {
        return login;
    }

    static String getLogin(MySQLServer sql, String id) {

        String login = "";

        try {
            ResultSet res = sql.getStatement().executeQuery("SELECT * FROM persons");

            for (int i = 0; i < Integer.parseInt(id); i++) {
                if (res.next())
                    login = res.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login;
    }

    static String getPassword(MySQLServer sql, String id) {

        String password = "";

        try {
            ResultSet res = sql.getStatement().executeQuery("SELECT * FROM persons");

            for (int i = 0; i < Integer.parseInt(id); i++) {
                if (res.next())
                    password = res.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    String getCash() {
        return cash;
    }

    String getCode() {
        return code;
    }
}
