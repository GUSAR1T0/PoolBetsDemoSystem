package com.poolbets.application.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.poolbets.application.additions.Codes.*;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
public class Client extends Thread {

    private int port = 11296;
    private String ipAddress = "95.37.138.89";

    private String login;
    String password;
    private String code;

    public Client(String login, String password, String Code) {

        this.login = login;
        this.password = password;

        connect(Code);
    }

    public String getCode() {
        return code;
    }

    public String getLogin() {
        return login;
    }

    public void connect(String Code) {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(CODE_LOGIN + Client.this.login + "|" +
                    CODE_PASSWORD + Client.this.password + "|" +
                    Code + "|");
            out.flush();

            code = in.readUTF();
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

            out.writeUTF(CODE_LOGIN + login + "|" +
                    CODE_DISCONNECTED + "|");
            out.flush();

            code = in.readUTF();
        } catch (Throwable throwable) {
            code = CODE_ERROR_CONNECTION;
        }
    }
}
