package android.g6.cricspot;

import android.content.Context;
import android.content.Intent;
import android.g6.cricspot.CricClasses.DatabaseManager;
import android.g6.cricspot.CricObjects.Player;
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

public class TeamDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    TextView teamName, teamLocation, txtErr;
    Button joinBtn;
    ListView playerListViewer;
    ArrayAdapter<String> listAdapter;
    List<Team> teamsList;
    List<String> playersNameList = new ArrayList<>();
    String intentString;
    Team selectedTeam;
    Player selectedPlayer;
    Boolean canHeJoin = false;
    DatabaseManager dbManager = new DatabaseManager();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        teamName = findViewById(R.id.teamNameTxtInTeamDetailsPage);
        teamLocation = findViewById(R.id.locationTxtInTeamDetailsPage);
        joinBtn = findViewById(R.id.joinBtnInTeamDetailsPage);
        playerListViewer = findViewById(R.id.playerListInTeamDetailsPage);
        txtErr = findViewById(R.id.txtErrInTeamDetailsPage);

        Button scroll = (Button)findViewById(R.id.back);
        scroll.setOnClickListener(this);

        intentString = getIntent().getStringExtra("tester");

        teamsList = UserWithoutTeamActivity.getTeamList();

        for (Team team: teamsList){
            if(team.getName().equalsIgnoreCase(intentString)){
                selectedTeam = team; // Got the team here!
            }
        }

        playersNameList.add(selectedTeam.getPlayer1());
        playersNameList.add(selectedTeam.getPlayer2());
        playersNameList.add(selectedTeam.getPlayer3());
        playersNameList.add(selectedTeam.getPlayer4());
        playersNameList.add(selectedTeam.getPlayer5());

        for(String playerName: playersNameList){
            if(playerName.equalsIgnoreCase("no")){
                canHeJoin = true;
            }
        }

        teamName.setText(selectedTeam.getName());
        teamLocation.setText(selectedTeam.getLocation());

        listAdapter = new ArrayAdapter<String>(TeamDetailsActivity.this,
                android.R.layout.simple_list_item_1,playersNameList);
        playerListViewer.setAdapter(listAdapter);
        playerListViewer.setBackgroundColor(Color.rgb(255,255,255));

    }

    public void joinBtnClickedInTeamDetailsPage(View view) {

        if (isInternetOn()) {
            if (canHeJoin) { // Can he join ?
                txtErr.setText("");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP,0,50);

                TextView text = new TextView(TeamDetailsActivity.this);
                text.setBackgroundColor(Color.rgb(206,205,205));

                Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                text.setTypeface(typeface);
                text.setTextColor(Color.rgb(71,165,74));
                text.setTextSize(13);
                text.setPadding(10,10,10,10);
                text.setText("You Clicked "+intentString);
                toast.setView(text);
                toast.show();

                selectedPlayer = MainActivity.getUserPlayerObject();

                /*TODO: Edit the team and set in MainActivity + update in database*/

                //Edit the current Team object
                if(selectedTeam.getPlayer1().equalsIgnoreCase("no")){
                    selectedTeam.setPlayer1(selectedPlayer.getUserName());
                }else if(selectedTeam.getPlayer2().equalsIgnoreCase("no")){
                    selectedTeam.setPlayer2(selectedPlayer.getUserName());
                }else if(selectedTeam.getPlayer3().equalsIgnoreCase("no")){
                    selectedTeam.setPlayer3(selectedPlayer.getUserName());
                }else if(selectedTeam.getPlayer4().equalsIgnoreCase("no")){
                    selectedTeam.setPlayer4(selectedPlayer.getUserName());
                }else if(selectedTeam.getPlayer5().equalsIgnoreCase("no")){
                    selectedTeam.setPlayer5(selectedPlayer.getUserName());
                }

                // Updating in the firebase
                dbManager.updateTeamAttributeInFirebase(DatabaseManager.getDbMemberNameForTeam(), selectedTeam);

                //Setting TEAM object in MainActivity
                MainActivity.setUserTeamObject(selectedTeam);

                /*TODO: Edit the player and set in MainActivity + update in database*/
                //Edit the current PLAYER object
                selectedPlayer.setTeam(selectedTeam.getName());

                //Update Player in the firebase
                dbManager.updatePlayerAttributeInFirebase(DatabaseManager.getDbMemberNameForPlayer(), selectedPlayer);

                //Update in MainActivity
                MainActivity.setUserPlayerObject(selectedPlayer);

                /* TODO: Redirect the page to UserWithTeam Activity */
                intent = new Intent(TeamDetailsActivity.this, UserWithTeamActivity.class);
                startActivity(intent);

            } else { // If, No he can't join!

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP,0,50);

                TextView text = new TextView(TeamDetailsActivity.this);
                text.setBackgroundColor(Color.rgb(206,205,205));

                Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                text.setTypeface(typeface);
                text.setTextColor(Color.rgb(190,39,39));
                text.setTextSize(13);
                text.setPadding(10,10,10,10);
                text.setText("Cant join at a moment");
                toast.setView(text);
                toast.show();

            }
        }else {

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(TeamDetailsActivity.this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, UserWithoutTeamActivity.class);
                startActivity(intent);
                break;
        }

    }
}
