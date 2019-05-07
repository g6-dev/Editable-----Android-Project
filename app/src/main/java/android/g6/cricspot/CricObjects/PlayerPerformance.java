package android.g6.cricspot.CricObjects;

public class PlayerPerformance {

    private String teamname;
    private String playername;
    private String runs;

    public PlayerPerformance(String teamname, String playername, String runs) {
        this.teamname = teamname;
        this.playername = playername;
        this.runs = runs;
    }

    public String getTeamname() {
        return teamname;
    }

    public String getPlayername() {
        return playername;
    }

    public String getRuns() {
        return runs;
    }

}
