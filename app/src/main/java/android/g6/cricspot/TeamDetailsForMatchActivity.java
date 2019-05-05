package android.g6.cricspot;

import android.content.Context;
import android.content.Intent;
import android.g6.cricspot.CricClasses.DatabaseManager;
import android.g6.cricspot.CricObjects.Team;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TeamDetailsForMatchActivity extends AppCompatActivity {

    TextView teamName, teamLocation, txtErr;
    ListView playersList;
    Button matchRequestBtn;
    Intent intent;
    String intentString;
    List<Team> listOfAllTeams = new ArrayList<>();
    List<String> listOfPlayers = new ArrayList<>();
    Team challengerTeam;

    ArrayAdapter<String> listAdapter;
    DatabaseManager dbManager = new DatabaseManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details_for_match);

        teamName = findViewById(R.id.teamNameTxtInTeamDetailsForMatchPage);
        teamLocation = findViewById(R.id.locationTxtInTeamDetailsForMatchPage);
        txtErr = findViewById(R.id.txtErrInTeamDetailsForMatchPage);
        playersList = findViewById(R.id.playerListInTeamDetailsForMatchPage);
        matchRequestBtn = findViewById(R.id.requestMatchBtnInTeamDetailsForMatchPage);

        intentString = getIntent().getStringExtra("tester");

        listOfAllTeams = DatabaseManager.getTeamsList();

        for (Team team: listOfAllTeams){
            if(team.getName().equalsIgnoreCase(intentString)){
                challengerTeam = team;
                System.out.println("Selected Team: "+challengerTeam);
            }
        }

        teamName.setText("Team Name : "+challengerTeam.getName());
        teamLocation.setText("Location : "+challengerTeam.getLocation());

        listOfPlayers.add(challengerTeam.getPlayer1());
        listOfPlayers.add(challengerTeam.getPlayer2());
        listOfPlayers.add(challengerTeam.getPlayer3());
        listOfPlayers.add(challengerTeam.getPlayer4());
        listOfPlayers.add(challengerTeam.getPlayer5());

        listAdapter = new ArrayAdapter<String>(TeamDetailsForMatchActivity.this,
                android.R.layout.simple_list_item_1, listOfPlayers);

        playersList.setAdapter(listAdapter);
        playersList.setBackgroundColor(Color.rgb(255,255,255));

    }

    public void requestMatchBtnClickedInTeamDetailsForMatchPage(View view) {

        if (isInternetOn()){
            Team selectedTeam = MainActivity.getUserTeamObject();

            /* TODO: Update MainActivity challenger + true */
            selectedTeam.setPlaying(true);
            selectedTeam.setChallenger(challengerTeam.getName());
            MainActivity.setUserTeamObject(selectedTeam); // Main activity update

            /* TODO: Update firebase challenger + true */
            dbManager.updateTeamAttributeInFirebase(DatabaseManager.getDbMemberNameForTeam(), selectedTeam);

            /* TODO: Update challengers firebase + false */
            challengerTeam.setChallenger(selectedTeam.getName());
            dbManager.updateTeamAttributeInFirebase(DatabaseManager.getDbMemberNameForTeam(), challengerTeam);

            intent = new Intent(TeamDetailsForMatchActivity.this, MatchActivity.class);
            startActivity(intent);

        }else{

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(TeamDetailsForMatchActivity.this);
            text.setBackgroundColor(Color.rgb(206,205,205));

            Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
            text.setTypeface(typeface);
            text.setTextColor(Color.rgb(190,39,39));
            text.setTextSize(13);
            text.setPadding(10,10,10,10);
            text.setText("Network Error");
            toast.setView(text);
            toast.show();

        }
    }

    /* To check the internet connection */
    public Boolean isInternetOn(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else {
            return false;
        }

    }
}
