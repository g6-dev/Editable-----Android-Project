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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener{

    static final String dbMemberName = "Team";

    private static Team thisTeam;

    public static Team getThisTeam() {
        return thisTeam;
    }

    public static void setThisTeam(Team thisTeam) {
        CreateTeamActivity.thisTeam = thisTeam;
    }

    TextView teamNameTxt, teamLocationTxt, txtErr;
    EditText teamNameE, teamLocationE;
    Button nextBtn;

    String teamName, teamLocation;
    Team team;
    Intent intent;
    List<Team> listOfAllTeams = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);

        teamNameTxt = findViewById(R.id.teamNameTxtInCreateTeamPage);
        teamLocationTxt = findViewById(R.id.teamLocationTxtInCreateTeamPage);
        txtErr = findViewById(R.id.txtErrInCreateTeamPage);
        teamNameE = findViewById(R.id.teamNameInCreateTeamPage);
        teamLocationE = findViewById(R.id.teamLocationInCreateTeamPage);
        nextBtn = findViewById(R.id.nextBtnInCreateTeamPage);

        Button scroll = (Button)findViewById(R.id.back);
        scroll.setOnClickListener(this);
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

    public void nextClickedInCreateTeamPage(View view) {
        if(isInternetOn()){
            teamName = teamNameE.getText().toString();
            teamLocation = teamLocationE.getText().toString();

            if(!(teamName.equalsIgnoreCase("") || teamLocation.equalsIgnoreCase(""))){
                if (! teamName.equalsIgnoreCase("no")) {
                    txtErr.setText("");
                    //Toast.makeText(CreateTeamActivity.this, "In maintenance", Toast.LENGTH_SHORT).show();

                    if (DatabaseManager.getIsTeamsRetrieved()) {
                        listOfAllTeams = DatabaseManager.getTeamsList();
                    }

                    Boolean isTeamExist = false;
                    for(Team thisTeam: listOfAllTeams){
                        if(thisTeam.getName().equalsIgnoreCase(teamName)){
                            isTeamExist = true;
                        }
                    }

                    if( ! isTeamExist ) {
                        // Creating a new Team with User as the first member
                        team = new Team(teamName, teamLocation,
                                MainActivity.getUserPlayerObject().getUserName(),
                                "no", "no", "no", "no",
                                "no", false);

                        setThisTeam(team);

                        intent = new Intent(CreateTeamActivity.this, ConfirmCreatedTeamActivity.class);
                        startActivity(intent);
                    }else{

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP,0,920);

                        TextView text = new TextView(CreateTeamActivity.this);
                        text.setBackgroundColor(Color.rgb(206,205,205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190,39,39));
                        text.setTextSize(13);
                        text.setPadding(10,10,10,10);
                        text.setText("Team name already exist!");
                        toast.setView(text);
                        toast.show();

                    }
                }else{

                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP,0,50);

                    TextView text = new TextView(CreateTeamActivity.this);
                    text.setBackgroundColor(Color.rgb(206,205,205));

                    Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                    text.setTypeface(typeface);
                    text.setTextColor(Color.rgb(190,39,39));
                    text.setTextSize(13);
                    text.setPadding(10,10,10,10);
                    text.setText("Can't create team name like NO");
                    toast.setView(text);
                    toast.show();

                }
            }else{

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP,0,50);

                TextView text = new TextView(CreateTeamActivity.this);
                text.setBackgroundColor(Color.rgb(206,205,205));

                Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                text.setTypeface(typeface);
                text.setTextColor(Color.rgb(190,39,39));
                text.setTextSize(13);
                text.setPadding(10,10,10,10);
                text.setText("Some Fields Are Empty!");
                toast.setView(text);
                toast.show();

            }
        }else{

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(CreateTeamActivity.this);
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

    //back page
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
