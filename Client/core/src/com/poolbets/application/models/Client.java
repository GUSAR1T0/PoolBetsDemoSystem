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
    private ArrayList<String> seasons;
    private ArrayList<String> leagues;
    private ArrayList<Pair<Integer, String>> teams;
    private ArrayList<Bet> events;

    public Client(String login, String password, String Code) {

        this.login = login;
        this.password = password;

        connect(Code);
        setSeasons();
        setLeagues();
        setTeams();
        setEvents();
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

    public ArrayList<String> getSeasons() {
        return seasons;
    }

    public ArrayList<String> getLeagues() {
        return leagues;
    }

    public ArrayList<Pair<Integer, String>> getTeams() {
        return teams;
    }

    public ArrayList<Bet> getEvents() {
        return events;
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

    public void restartConnection() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            out.writeUTF(CODE_ID + this.id + "|" +
                    CODE_AUTHORIZATION + "|");
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

            switch (message.getCode()) {
                case CODE_CONNECTED:
                case CODE_ERROR_REGISTRATION:
                case CODE_ERROR_AUTHORIZATION:
                    code = message.getCode();
                    break;
                case CODE_ID:
                    id = message.getValue();
                    break;
                case CODE_CASH:
                    cash = message.getValue();
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

    public void setSeasons() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            seasons = new ArrayList<>();

            out.writeUTF(CODE_ID + this.id + "|" +
                    SQL_GET_SEASONS + "|");
            out.flush();

            int count = in.readInt();

            for (int i = 0; i < count; i++)
                seasons.add(in.readUTF());
        } catch (Throwable throwable) {
            this.code = CODE_ERROR_CONNECTION;
        }
    }

    public void setLeagues() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            leagues = new ArrayList<>();

            out.writeUTF(CODE_ID + this.id + "|" +
                    SQL_GET_LEAGUES + "|");
            out.flush();

            int count = in.readInt();

            for (int i = 0; i < count; i++)
                leagues.add(in.readUTF());
        } catch (Throwable throwable) {
            this.code = CODE_ERROR_CONNECTION;
        }
    }

    public void setTeams() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            teams = new ArrayList<>();

            out.writeUTF(CODE_ID + this.id + "|" +
                    SQL_GET_TEAMS + "|");
            out.flush();

            int count = in.readInt();

            for (int i = 0; i < count; i++) {

                int leagueID = Integer.parseInt(in.readUTF());
                String team = in.readUTF();

                teams.add(new Pair<>(leagueID, team));
            }
        } catch (Throwable throwable) {
            this.code = CODE_ERROR_CONNECTION;
        }
    }

    public void setEvents() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            events = new ArrayList<>();

            out.writeUTF(CODE_ID + this.id + "|" +
                    SQL_GET_EVENTS + "|");
            out.flush();

            int count = in.readInt();

            for (int i = 0; i < count; i++) {

                String league = in.readUTF();
                String season = in.readUTF();
                String firstTeam = in.readUTF();
                String secondTeam = in.readUTF();
                String winFirstTeam = in.readUTF();
                String draw = in.readUTF();
                String winSecondTeam = in.readUTF();
                String result = in.readUTF();

                int leagueID = Integer.parseInt(league);
                int seasonID = Integer.parseInt(season);
                int firstTeamID = Integer.parseInt(firstTeam);
                int secondTeamID = Integer.parseInt(secondTeam);

                events.add(new Bet(
                        leagues.get(leagueID - 1),
                        seasons.get(seasonID - 1),
                        teams.get(firstTeamID - 1).getValue(),
                        teams.get(secondTeamID - 1).getValue(),
                        winFirstTeam,
                        draw,
                        winSecondTeam,
                        result
                ));
            }
        } catch (Throwable throwable) {
            this.code = CODE_ERROR_CONNECTION;
        }
    }
}
