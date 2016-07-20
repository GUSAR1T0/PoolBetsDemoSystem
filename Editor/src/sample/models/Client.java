package sample.models;

import sample.additions.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

import static sample.additions.Codes.*;
import static sample.additions.Utils.analise;

/**
 * Created by Mashenkin Roman on 19.07.2016.
 */
public class Client {

    private String ipAddress;
    private int port;
    private String code;
    private ArrayList<String> seasons;
    private ArrayList<String> leagues;
    private ArrayList<Pair<Integer, String>> teams;
    private ArrayList<Bet> events;

    public Client(String ipAddress, int port, String code) {

        this.ipAddress = ipAddress;
        this.port = port;
        this.code = code;

        connect();
        setSeasons();
        setLeagues();
        setTeams();
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

    public void setSeasons() {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            seasons = new ArrayList<>();

            out.writeUTF(CODE_ID + "0" + "|" +
                    SQL_GET_SEASONS + "|");
            out.flush();

            code = analise(in.readUTF());

            int count = in.readInt();

            for (int i = 0; i < count; i++)
                seasons.add(in.readUTF());
        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
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

            out.writeUTF(CODE_ID + "0" + "|" +
                    SQL_GET_LEAGUES + "|");
            out.flush();

            code = analise(in.readUTF());

            int count = in.readInt();

            for (int i = 0; i < count; i++)
                leagues.add(in.readUTF());
        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
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

            out.writeUTF(CODE_ID + "0" + "|" +
                    SQL_GET_TEAMS + "|");
            out.flush();

            code = analise(in.readUTF());

            int count = in.readInt();

            for (int i = 0; i < count; i++) {

                int leagueID = Integer.parseInt(in.readUTF());
                String team = in.readUTF();

                teams.add(new Pair<>(leagueID, team));
            }
        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
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

            out.writeUTF(CODE_ID + "0" + "|" +
                    SQL_GET_EVENTS + "|");
            out.flush();

            code = analise(in.readUTF());

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
            this.code = CODE_EDITOR_ERROR_CONNECTION;
        }
    }

    public void sendNewBetOnMySQLServerEvent(Bet bet) {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            events = new ArrayList<>();

            out.writeUTF(CODE_ID + "0" + "|" +
                    SQL_ADD_EVENT + "|");

            code = analise(in.readUTF());

            out.writeInt(getSeasonID(bet.getSeason()));
            out.writeInt(getLeagueID(bet.getLeague()));
            out.writeInt(getTeamID(bet.getFirstTeam()));
            out.writeInt(getTeamID(bet.getSecondTeam()));
            out.writeUTF(bet.getWinFirstTeam());
            out.writeUTF(bet.getDraw());
            out.writeUTF(bet.getWinSecondTeam());
            out.writeUTF(bet.getResult());

            out.flush();

        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
        }
    }

    public void sendUpdatedBetOnMySQLServerEvent(Bet bet, int index) {

        try {
            InetAddress inetAddress = InetAddress.getByName(this.ipAddress);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(inetAddress, this.port), 5000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream in = new DataInputStream(socket.getInputStream());

            events = new ArrayList<>();

            out.writeUTF(CODE_ID + "0" + "|" +
                    SQL_UPDATED_EVENT + "|");

            code = analise(in.readUTF());

            out.writeInt(index);
            out.writeInt(getSeasonID(bet.getSeason()));
            out.writeInt(getLeagueID(bet.getLeague()));
            out.writeInt(getTeamID(bet.getFirstTeam()));
            out.writeInt(getTeamID(bet.getSecondTeam()));
            out.writeUTF(bet.getWinFirstTeam());
            out.writeUTF(bet.getDraw());
            out.writeUTF(bet.getWinSecondTeam());
            out.writeUTF(bet.getResult());

            out.flush();

        } catch (Throwable throwable) {
            this.code = CODE_EDITOR_ERROR_CONNECTION;
        }
    }

    public int getSeasonID(String season) {

        for (int i = 0; i < seasons.size(); i++)
            if (season.equals(seasons.get(i))) return i + 1;

        return 0;
    }

    public int getLeagueID(String league) {

        for (int i = 0; i < leagues.size(); i++)
            if (league.equals(leagues.get(i))) return i + 1;

        return 0;
    }

    public int getTeamID(String team) {

        for (int i = 0; i < teams.size(); i++)
            if (team.equals(teams.get(i).getValue())) return i + 1;

        return 0;
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

    public String getCode() {
        return code;
    }
}
