package com.poolbets.application.models;

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

    public String getSeason() {
        return season;
    }

    public String getFirstTeam() {
        return firstTeam;
    }

    public String getSecondTeam() {
        return secondTeam;
    }

    public String getWinFirstTeam() {
        return winFirstTeam;
    }

    public String getDraw() {
        return draw;
    }

    public String getWinSecondTeam() {
        return winSecondTeam;
    }

    public String getResult() {
        return result;
    }
}
