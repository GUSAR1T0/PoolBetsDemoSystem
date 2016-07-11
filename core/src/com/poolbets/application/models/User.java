package com.poolbets.application.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Mashenkin Roman on 11.07.16.
 */
public class User {

    private boolean isExist;
    private String path;
    private String login;
    private String password;

    public User(String path) {

        this.path = path;

        isExist = isExist(path);
        if (isExist)
            readTextFile(Gdx.files.local(path));
    }

    public void readTextFile(FileHandle file) {

        String fileData = file.readString();

        int i = 0;

        while (fileData.length() > i) {

            String flag = "", key = "";

            if (fileData.charAt(i) == '[') {
                while (fileData.charAt(i + 1) != ']')
                    flag += fileData.charAt(++i);
                i += 2;
            }

            while (fileData.charAt(i + 1) != '\n') {
                key += fileData.charAt(++i);
                if (fileData.length() <= i + 1) break;
            }

            i += 2;

            if (flag.equals("LOGIN")) login = key;
            if (flag.equals("PASSWORD")) password = key;
        }
    }

    public boolean isExist(String path) {
        return Gdx.files.local(path).length() > 0;
    }

    public boolean isExist() {
        return isExist;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveData() {

        FileHandle file = Gdx.files.local(path);
        file.writeString("[LOGIN]=" + login + "\n" +
                "[PASSWORD]=" + password, false);
    }

    public void deleteData() {

        FileHandle file = Gdx.files.local(path);
        file.delete();
    }
}
