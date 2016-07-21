package sample.models;

import sample.additions.Pair;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Roman_Mashenkin on 19.07.2016.
 */
public class MySQLServer extends Thread {

    private final String ipAddress;
    private final int port;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public MySQLServer(String ipAddress, int port) throws ClassNotFoundException, SQLException {

        this.ipAddress = ipAddress;
        this.port = port;

        Class.forName("com.mysql.jdbc.Driver");

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }

    public void stopConnection() throws SQLException {
        statement.close();
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public ArrayList<String> getSeasons() {

        ArrayList<String> seasons = new ArrayList<>();

        try {
            ResultSet res = statement.executeQuery("SELECT * FROM seasons");

            while (res.next()) {
                seasons.add(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seasons;
    }

    public ArrayList<String> getLeagues() {

        ArrayList<String> leagues = new ArrayList<>();

        try {
            ResultSet res = statement.executeQuery("SELECT * FROM leagues");

            while (res.next()) {
                leagues.add(res.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return leagues;
    }

    public ArrayList<Pair<Integer, String>> getTeams() {

        ArrayList<Pair<Integer, String>> teams = new ArrayList<>();

        try {
            ResultSet res = statement.executeQuery("SELECT * FROM teams");

            while (res.next())
                teams.add(new Pair<>(res.getInt(3), res.getString(2)));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }

    public ArrayList<Bet> getEvents() {

        ArrayList<Bet> events = new ArrayList<>();

        try {
            ResultSet res = statement.executeQuery("SELECT * FROM events");

            while (res.next())
                events.add(new Bet(res.getInt(2) + "",
                        res.getInt(3) + "",
                        res.getInt(4) + "",
                        res.getInt(5) + "",
                        res.getString(6),
                        res.getString(7),
                        res.getString(8),
                        res.getString(9)
                        ));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    public ArrayList<BetsHistory> getBetsHistory() {

        ArrayList<BetsHistory> betsHistory = new ArrayList<>();

        try {
            ResultSet res = statement.executeQuery("SELECT * FROM bets_history");

            while (res.next())
                betsHistory.add(new BetsHistory(
                        res.getInt(2),
                        res.getInt(3),
                        res.getInt(4),
                        res.getString(5),
                        res.getBoolean(6)
                ));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return betsHistory;
    }

    public void sendOnMySQLServerNewEvent(int league, int season, int firstTeam, int secondTeam,
                                        String winFirstTeam, String draw, String winSecondTeam, String result) {

        String query = "INSERT INTO events (league_id, season_id, first_team_id, second_team_id, " +
                "win_first_team, draw, win_second_team, result) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, league);
            preparedStatement.setInt(2, season);
            preparedStatement.setInt(3, firstTeam);
            preparedStatement.setInt(4, secondTeam);
            preparedStatement.setString(5, winFirstTeam);
            preparedStatement.setString(6, draw);
            preparedStatement.setString(7, winSecondTeam);
            preparedStatement.setString(8, result);

            preparedStatement.execute();
        } catch (SQLException ignored) {}
    }

    public void sendOnMySQLServerUpdatedEvent(int index, int league, int season, int firstTeam, int secondTeam,
                                        String winFirstTeam, String draw, String winSecondTeam, String result) {

        String query = "UPDATE events SET league_id=?, season_id=?, first_team_id=?, second_team_id=?, " +
                "win_first_team=?, draw=?, win_second_team=?, result=? WHERE id=?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, league);
            preparedStatement.setInt(2, season);
            preparedStatement.setInt(3, firstTeam);
            preparedStatement.setInt(4, secondTeam);
            preparedStatement.setString(5, winFirstTeam);
            preparedStatement.setString(6, draw);
            preparedStatement.setString(7, winSecondTeam);
            preparedStatement.setString(8, result);
            preparedStatement.setInt(9, index);

            preparedStatement.execute();
        } catch (SQLException ignored) {}
    }

    @Override
    public void run() {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + ipAddress + ":" + port + "/poolbets?" +
                            "autoReconnect=true&useSSL=false",
                    "root", "1234x5q7a8r10");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
