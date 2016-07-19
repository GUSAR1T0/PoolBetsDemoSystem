package sample.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static sample.additions.Codes.*;
import static sample.additions.Utils.analise;

/**
 * Created by Mashenkin Roman on 19.07.2016.
 */
public class Client {

    private String ipAddress;
    private int port;
    private String code;

    public Client(String ipAddress, int port, String code) {

        this.ipAddress = ipAddress;
        this.port = port;
        this.code = code;

        connect();
    }

    public void connect() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(CODE_ID + "0" + "|");
            out.flush();

            String line = in.readUTF();
            code = analise(line);
        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
        }
    }

    public void disconnect() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(CODE_ID + "0" + "|" +
                    CODE_EDITOR_DISCONNECTED + "|");
            out.flush();

            this.code = in.readUTF();
        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
        }
    }

    public String getCode() {
        return code;
    }
}
