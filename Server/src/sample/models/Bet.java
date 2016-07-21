package sample.models;

/**
 * Created by Roman_Mashenkin on 20.07.2016.
 */
public class Bet {

    private String league;
    private String season;
    private String firstTeam;
    private String secondTeam;
    private String winFirstTeam;
    private String draw;
    private String winSecondTeam;
    private String result;

    public Bet(String league, String season, String firstTeam, String secondTeam, String winFirstTeam, String draw,
               String winSecondTeam, String result) {

        this.league = league;
        this.season = season;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        this.winFirstTeam = winFirstTeam;
        this.draw = draw;
        this.winSecondTeam = winSecondTeam;
        this.result = result;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(String firstTeam) {
        this.firstTeam = firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(String secondTeam) {
        this.secondTeam = secondTeam;
    }

    public String getWinFirstTeam() {
        return winFirstTeam;
    }

    public void setWinFirstTeam(String winFirstTeam) {
        this.winFirstTeam = winFirstTeam;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public String getWinSecondTeam() {
        return winSecondTeam;
    }

    public void setWinSecondTeam(String winSecondTeam) {
        this.winSecondTeam = winSecondTeam;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
