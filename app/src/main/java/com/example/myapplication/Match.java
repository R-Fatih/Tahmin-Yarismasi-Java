package com.example.myapplication;



public class Match
{
    public Match(String home, String away, String date, String ıD, String mS, String username, String hometeamid, String awayteamid)
    {
        this.home = home;
        this.away = away;
        this.date = date;
        id = ıD;
        ms = mS;
        this.username = username;
        this.hometeamid = hometeamid;
        this.awayteamid = awayteamid;
    }
    public Match(String home, String away, String date, String ıD, String mS, String username, String hometeamid, String awayteamid,int point)
    {
        this.home = home;
        this.away = away;
        this.date = date;
        id = ıD;
        ms = mS;
        this.username = username;
        this.hometeamid = hometeamid;
        this.awayteamid = awayteamid;
        this.point = point;
    }

    public Match(String home, String away, String date, String id, String ms, String username, String hometeamid, String awayteamid, String competition, String competitionWeek)
    {
        this.home = home;
        this.away = away;
        this.date = date;
        this.id = id;
        this.ms = ms;
        this.username = username;
        this.hometeamid = hometeamid;
        this.awayteamid = awayteamid;
        this.competition = competition;
        this.competitionWeek = competitionWeek;
    }

    public Match(String home, String away, String date, String id, String ms, String username, String hometeamid, String awayteamid, int point, String competition, String competitionWeek)
    {
        this.home = home;
        this.away = away;
        this.date = date;
        this.id = id;
        this.ms = ms;
        this.username = username;
        this.hometeamid = hometeamid;
        this.awayteamid = awayteamid;
        this.point = point;
        this.competition = competition;
        this.competitionWeek = competitionWeek;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHometeamid() {
        return hometeamid;
    }

    public void setHometeamid(String hometeamid) {
        this.hometeamid = hometeamid;
    }

    public String getAwayteamid() {
        return awayteamid;
    }

    public void setAwayteamid(String awayteamid) {
        this.awayteamid = awayteamid;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getCompetitionWeek() {
        return competitionWeek;
    }

    public void setCompetitionWeek(String competitionWeek) {
        this.competitionWeek = competitionWeek;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String home ;

    public String away ;
    public String date ;
    public String id ;
    public String ms ;
    public String username ;
    public String hometeamid ;
    public String awayteamid ;
    public String competition ;
    public String competitionWeek;
    public int point ;


}

