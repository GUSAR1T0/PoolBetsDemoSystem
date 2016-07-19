package sample.models;

import javafx.application.Platform;
import sample.Main;
import sample.additions.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import static sample.additions.Codes.*;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
public class Server extends Thread {

    private Main main;
    private ServerSocket ss;
    private ArrayList<Client> clients;

    public Server(Main main, int port, String ipAddress) throws IOException {

        ss = new ServerSocket(port, 0, InetAddress.getByName(ipAddress));
        ss.setSoTimeout(0);

        this.main = main;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void stopServer() throws IOException {
        ss.close();
    }

    @Override
    public void run() {

        clients = new ArrayList<>();

        while (true) {
            try {
                Socket socket = ss.accept();

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String line = in.readUTF();
                analise(line, out);

            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private ArrayList<Pair<String, String>> decoder(String inputLine) {

        ArrayList<Pair<String, String>> messages = new ArrayList<>();

        int j = 0;

        for (int i = 0; i < inputLine.length(); i++) {

            if (inputLine.charAt(i) != '|') continue;

            messages.add(new Pair<>(inputLine.substring(j, j + 3),
                    ((inputLine.length() >= i + 1) ? inputLine.substring(j + 3, i) : "")));
            j = i + 1;
        }

        return messages;
    }

    private void analise(String line, DataOutputStream out) throws IOException {

        String id = "", login = "", password = "";
        ArrayList<Pair<String, String>> messages = decoder(line);

        for (Pair<String, String> message : messages) {

            switch (message.getCode()) {

                case CODE_ID: {

                    if (!message.getValue().equals("0")) {
                        id = message.getValue();
                        login = Client.getLogin(id);
                        password = Client.getPassword(id);
                    } else {
                        boolean flag = false;

                        for (Client client : clients)
                            if (Objects.equals(client.getLogin(), login)) {
                                flag = true;

                                out.writeUTF(CODE_EDITOR_ERROR_AUTHORIZATION + "|");

                                break;
                            }

                        if (!flag) {
                            clients.add(new Client("0", "PoolBets Database Editor", "", CODE_EDITOR_CONNECTED));

                            Platform.runLater(() -> main.connectInfo(clients.get(clients.size() - 1).getLogin(),
                                        clients.get(clients.size() - 1).getID(), false));
                            out.writeUTF(CODE_EDITOR_CONNECTED + "|");
                        }

                        out.flush();
                    }

                    break;
                }

                case CODE_LOGIN: {
                    login = message.getValue();
                    break;
                }

                case CODE_PASSWORD: {
                    password = message.getValue();
                    break;
                }

                case CODE_AUTHORIZATION: case CODE_REGISTRATION: {
                    boolean flag = false;

                    for (Client client : clients)
                        if (Objects.equals(client.getLogin(), login)) {
                            flag = true;

                            out.writeUTF(((Objects.equals(message.getCode(), CODE_AUTHORIZATION)) ?
                                    CODE_ERROR_AUTHORIZATION :
                                    CODE_ERROR_REGISTRATION) + "|");

                            break;
                        }

                    if (!flag) {
                        clients.add(new Client(id, login, password, message.getCode()));

                        out.writeUTF(clients.get(clients.size() - 1).getCode() + "|" +
                                CODE_ID + clients.get(clients.size() - 1).getID() + "|" +
                                CODE_CASH + clients.get(clients.size() - 1).getCash() + "|");

                        if (checkOnErrors())
                            clients.remove(clients.get(clients.size() - 1));
                        else if (checkOnSuccess(clients.get(clients.size() - 1).getLogin())) {
                            Platform.runLater(() -> main.connectInfo(clients.get(clients.size() - 1).getLogin(),
                                    clients.get(clients.size() - 1).getID(),
                                    Objects.equals(message.getCode(), CODE_REGISTRATION)));
                        }
                    }

                    out.flush();

                    break;
                }

                case CODE_DISCONNECTED: {

                    for (Client i : clients) {
                        if (Objects.equals(i.getID(), id)) {
                            clients.remove(i);

                            String finalLogin1 = login;
                            String finalId1 = id;
                            Platform.runLater(() -> main.disconnectInfo(finalLogin1, finalId1));
                            out.writeUTF(CODE_SUCCESS);
                            out.flush();

                            break;
                        }
                    }

                    break;
                }

                case CODE_EDITOR_DISCONNECTED: {

                    for (Client i : clients) {
                        if (Objects.equals(i.getID(), "0")) {
                            clients.remove(i);

                            Platform.runLater(() -> main.disconnectInfo("PoolBets Database Editor", "0"));
                            out.writeUTF(CODE_SUCCESS);
                            out.flush();

                            break;
                        }
                    }

                    break;
                }
            }
        }
    }

    private boolean checkOnErrors() {

        return Objects.equals(clients.get(clients.size() - 1).getCode(),
                CODE_ERROR_AUTHORIZATION) ||
                (Objects.equals(clients.get(clients.size() - 1).getCode(),
                        CODE_ERROR_REGISTRATION));
    }

    private boolean checkOnSuccess(String login) {

        return (Objects.equals(clients.get(clients.size() - 1).getLogin(), login)) &&
                Objects.equals(clients.get(clients.size() - 1).getCode(), CODE_CONNECTED);
    }
}
