package com.poolbets.application.models;

import com.poolbets.application.additions.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import static com.poolbets.application.additions.Codes.*;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
public class Client extends Thread {

    private int port = 11296;
    private String ipAddress = "95.37.138.89";

    private String login;
    private String id;
    String password;
    private String cash;
    private String code;

    public Client(String login, String password, String Code) {

        this.login = login;
        this.password = password;

        connect(Code);
    }

    public String getID() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getCash() {
        return cash;
    }

    public String getCode() {
        return code;
    }

    public void connect(String Code) {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(CODE_LOGIN + this.login + "|" +
                    CODE_PASSWORD + this.password + "|" +
                    Code + "|");
            out.flush();

            String line = in.readUTF();
            analise(line);
        } catch (Throwable throwable) {
            code = CODE_ERROR_CONNECTION;
        }
    }

    public void disconnect() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(CODE_ID + this.id + "|" +
                    CODE_DISCONNECTED + "|");
            out.flush();

            code = in.readUTF();
        } catch (Throwable throwable) {
            code = CODE_ERROR_CONNECTION;
        }
    }

    private void analise(String line) {

        ArrayList<Pair<String, String>> messages = decoder(line);

        for (Pair<String, String> message : messages) {

            if (message.getCode().equals(CODE_CONNECTED) ||
                    message.getCode().equals(CODE_ERROR_REGISTRATION) ||
                    message.getCode().equals(CODE_ERROR_AUTHORIZATION))
                code = message.getCode();
            else if (message.getCode().equals(CODE_ID))
                id = message.getValue();
            else if (message.getCode().equals(CODE_CASH))
                cash = message.getValue();
        }
    }

    private ArrayList<Pair<String, String>> decoder(String inputLine) {

        ArrayList<Pair<String, String>> messages = new ArrayList<Pair<String, String>>();

        int j = 0;

        for (int i = 0; i < inputLine.length(); i++) {

            if (inputLine.charAt(i) != '|') continue;

            messages.add(new Pair<String, String>(inputLine.substring(j, j + 3),
                    ((inputLine.length() >= i + 1) ? inputLine.substring(j + 3, i) : "")));
            j = i + 1;
        }

        return messages;
    }
}
