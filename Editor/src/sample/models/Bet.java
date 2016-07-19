package sample.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Mashenkin Roman on 19.07.2016.
 */
public class Bet {

    private SimpleStringProperty league;
    private SimpleStringProperty season;
    private SimpleStringProperty firstTeam;
    private SimpleStringProperty secondTeam;
    private SimpleStringProperty winFirstTeam;
    private SimpleStringProperty draw;
    private SimpleStringProperty winSecondTeam;
    private SimpleStringProperty result;

    public Bet(String league, String season, String firstTeam, String secondTeam, String winFirstTeam, String draw,
               String winSecondTeam, String result) {

        this.league = new SimpleStringProperty(league);
        this.season = new SimpleStringProperty(season);
        this.firstTeam = new SimpleStringProperty(firstTeam);
        this.secondTeam = new SimpleStringProperty(secondTeam);
        this.winFirstTeam = new SimpleStringProperty(winFirstTeam);
        this.draw = new SimpleStringProperty(draw);
        this.winSecondTeam = new SimpleStringProperty(winSecondTeam);
        this.result = new SimpleStringProperty(result);
    }

    public String getLeague() {
        return league.get();
    }

    public SimpleStringProperty leagueProperty() {
        return league;
    }

    public void setLeague(String league) {
        this.league.set(league);
    }

    public String getSeason() {
        return season.get();
    }

    public SimpleStringProperty seasonProperty() {
        return season;
    }

    public void setSeason(String season) {
        this.season.set(season);
    }

    public String getFirstTeam() {
        return firstTeam.get();
    }

    public SimpleStringProperty firstTeamProperty() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam.set(firstTeam);
    }

    public String getSecondTeam() {
        return secondTeam.get();
    }

    public SimpleStringProperty secondTeamProperty() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam.set(secondTeam);
    }

    public String getWinFirstTeam() {
        return winFirstTeam.get();
    }

    public SimpleStringProperty winFirstTeamProperty() {
        return winFirstTeam;
    }

    public void setWinFirstTeam(String winFirstTeam) {
        this.winFirstTeam.set(winFirstTeam);
    }

    public String getDraw() {
        return draw.get();
    }

    public SimpleStringProperty drawProperty() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw.set(draw);
    }

    public String getWinSecondTeam() {
        return winSecondTeam.get();
    }

    public SimpleStringProperty winSecondTeamProperty() {
        return winSecondTeam;
    }

    public void setWinSecondTeam(String winSecondTeam) {
        this.winSecondTeam.set(winSecondTeam);
    }

    public String getResult() {
        return result.get();
    }

    public SimpleStringProperty resultProperty() {
        return result;
    }

    public void setResult(String result) {
        this.result.set(result);
    }
}
