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
    private MySQLServer sql;
    private ServerSocket ss;
    private ArrayList<Client> clients;

    public Server(Main main, MySQLServer sql, int port, String ipAddress) throws IOException {

        ss = new ServerSocket(port, 0, InetAddress.getByName(ipAddress));
        ss.setSoTimeout(0);

        this.main = main;
        this.sql = sql;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void stopServer() throws IOException {
        ss.close();
    }

    public ServerSocket getServerSocket() {
        return ss;
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
                analise(line, out, in);

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

    private void analise(String line, DataOutputStream out, DataInputStream in) throws IOException {

        String id = "", login = "", password = "";
        ArrayList<Pair<String, String>> messages = decoder(line);

        for (Pair<String, String> message : messages) {

            switch (message.getCode()) {

                case CODE_ID: {

                    if (!message.getValue().equals("0")) {
                        id = message.getValue();
                        login = Client.getLogin(sql, id);
                        password = Client.getPassword(sql, id);
                    } else {
                        boolean flag = false;

                        for (Client client : clients)
                            if (Objects.equals(client.getLogin(), login)) {
                                flag = true;

                                out.writeUTF(CODE_EDITOR_ERROR_AUTHORIZATION + "|");

                                break;
                            }

                        if (!flag) {
                            clients.add(new Client(sql, "0", "PoolBets Database Editor", "", CODE_EDITOR_CONNECTED));

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
                        clients.add(new Client(sql, id, login, password, message.getCode()));

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
                            String finalLogin1 = i.getLogin();
                            String finalId1 = id;

                            clients.remove(i);
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

                case SQL_GET_SEASONS: {

                    ArrayList<String> seasons = sql.getSeasons();

                    out.writeInt(seasons.size());

                    for (String i : seasons)
                        out.writeUTF(i);

                    out.flush();

                    break;
                }

                case SQL_GET_LEAGUES: {

                    ArrayList<String> leagues = sql.getLeagues();

                    out.writeInt(leagues.size());

                    for (String i : leagues)
                        out.writeUTF(i);

                    out.flush();

                    break;
                }

                case SQL_GET_TEAMS: {

                    ArrayList<Pair<Integer, String>> teams = sql.getTeams();

                    out.writeInt(teams.size());

                    for (Pair<Integer, String> i : teams) {
                        out.writeUTF(i.getCode() + "");
                        out.writeUTF(i.getValue());
                    }

                    out.flush();

                    break;
                }

                case SQL_GET_BETS_HISTORY: {

                    ArrayList<BetsHistory> betsHistory = sql.getBetsHistory();

                    out.writeInt(betsHistory.size());

                    for (BetsHistory i : betsHistory) {
                        out.writeUTF(i.getPersonID() + "");
                        out.writeUTF(i.getEventID() + "");
                        out.writeUTF(i.getChosenResult() + "");
                        out.writeUTF(i.getPayments());
                        out.writeUTF(i.isPaid() + "");
                    }

                    out.flush();

                    break;
                }

                case SQL_GET_EVENTS: {

                    ArrayList<Bet> events = sql.getEvents();

                    out.writeInt(events.size());

                    for (Bet i : events) {
                        out.writeUTF(i.getLeague());
                        out.writeUTF(i.getSeason());
                        out.writeUTF(i.getFirstTeam());
                        out.writeUTF(i.getSecondTeam());
                        out.writeUTF(i.getWinFirstTeam());
                        out.writeUTF(i.getDraw());
                        out.writeUTF(i.getWinSecondTeam());
                        out.writeUTF(i.getResult());
                    }

                    out.flush();

                    break;
                }

                case SQL_ADD_EVENT: {

                    int league = in.readInt();
                    int season = in.readInt();
                    int firstTeam = in.readInt();
                    int secondTeam = in.readInt();
                    String winFirstTeam = in.readUTF();
                    String draw = in.readUTF();
                    String winSecondTeam = in.readUTF();
                    String result = in.readUTF();

                    sql.sendOnMySQLServerNewEvent(league, season, firstTeam, secondTeam,
                            winFirstTeam, draw, winSecondTeam, result);

                    break;
                }

                case SQL_UPDATED_EVENT: {

                    int index = in.readInt();
                    int season = in.readInt();
                    int league = in.readInt();
                    int firstTeam = in.readInt();
                    int secondTeam = in.readInt();
                    String winFirstTeam = in.readUTF();
                    String draw = in.readUTF();
                    String winSecondTeam = in.readUTF();
                    String result = in.readUTF();

                    sql.sendOnMySQLServerUpdatedEvent(index, league, season, firstTeam, secondTeam,
                            winFirstTeam, draw, winSecondTeam, result);

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
