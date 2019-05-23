package android.g6.cricspot;

import android.content.Context;
import android.content.Intent;
import android.g6.cricspot.CricClasses.DatabaseManager;
import android.g6.cricspot.CricObjects.MatchDetails;
import android.g6.cricspot.CricObjects.PlayerPerformance;
import android.g6.cricspot.CricObjects.Team;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MatchActivity extends AppCompatActivity {

    TextView yoTeam, chlTeam, statusTxt,
            yoPlayer1, yoPlayer2, yoPlayer3, yoPlayer4, yoPlayer5,
            chlPlayer1, chlPlayer2, chlPlayer3, chlPlayer4, chlPlayer5;

    EditText yoPl1RunsE, yoPl1OversE, yoPl1WktsE,
            yoPl2RunsE, yoPl2OversE, yoPl2WktsE,
            yoPl3RunsE, yoPl3OversE, yoPl3WktsE,
            yoPl4RunsE, yoPl4OversE, yoPl4WktsE,
            yoPl5RunsE, yoPl5OversE, yoPl5WktsE,
            chlPl1RunsE, chlPl1OversE, chlPl1WktsE,
            chlPl2RunsE, chlPl2OversE, chlPl2WktsE,
            chlPl3RunsE, chlPl3OversE, chlPl3WktsE,
            chlPl4RunsE, chlPl4OversE, chlPl4WktsE,
            chlPl5RunsE, chlPl5OversE, chlPl5WktsE;

    Button startMatch, cancelMatch;
    Team selectedTeam;
    Team challengerTeam;
    List<Team> listOfAllTeams = new ArrayList<>();
    DatabaseManager dbManager = new DatabaseManager();
    Intent intent;

    private static MatchDetails matchDetails = new MatchDetails();

    public static MatchDetails getMatchDetails() {
        return matchDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        assignLayouts(); //Assign layouts

        selectedTeam = MainActivity.getUserTeamObject(); // Your Team
        listOfAllTeams = DatabaseManager.getTeamsList();

        for (Team team : listOfAllTeams) {
            if (team.getName().equalsIgnoreCase(selectedTeam.getChallenger())) {
                challengerTeam = team; // ChallengerTeam
            }
        }

        /*TODO: Set out the layouts here*/
        yoTeam.setText(selectedTeam.getName());
        yoPlayer1.setText(selectedTeam.getPlayer1());
        yoPlayer2.setText(selectedTeam.getPlayer2());
        yoPlayer3.setText(selectedTeam.getPlayer3());
        yoPlayer4.setText(selectedTeam.getPlayer4());
        yoPlayer5.setText(selectedTeam.getPlayer5());

        chlTeam.setText(challengerTeam.getName());
        chlPlayer1.setText(challengerTeam.getPlayer1());
        chlPlayer2.setText(challengerTeam.getPlayer2());
        chlPlayer3.setText(challengerTeam.getPlayer3());
        chlPlayer4.setText(challengerTeam.getPlayer4());
        chlPlayer5.setText(challengerTeam.getPlayer5());

        /*TODO: If you sent request, wait for response*/
        if (selectedTeam.getPlaying()) { // You sent the request
            if (challengerTeam.getPlaying()) { // Challenger too accepted the match
                /*TODO: Challenger is accepted part here*/
                statusTxt.setText("Challenger accepted your request...");

                startMatch.setText("Finish Match"); // 'Start Match' button to 'Finish Match'
                cancelMatch.setEnabled(false); // No need 'Cancel Match'

                setEditors(true); // Can edit
            } else {
                /*TODO: Challenger not yet accepted part here*/
                statusTxt.setText("Challenger yet not accepted!");

                setEditors(false); //Can not be edited right now
                startMatch.setEnabled(false); // Ypu should wait till he accepts
            }
        } else {
            if (challengerTeam.getPlaying()) { /*TODO: You havn't accepted yet*/
                statusTxt.setText("You have not accepted yet!");

                setEditors(false); // Can not be edited right now
            }
        }

    }

    public void startOrFinishClickedInMatchPageButton(View view) {

        int yop1 = Integer.parseInt(yoPl1RunsE.getText().toString());
        int yop2 = Integer.parseInt(yoPl1RunsE.getText().toString());
        int yop3 = Integer.parseInt(yoPl1RunsE.getText().toString());
        int yop4 = Integer.parseInt(yoPl1RunsE.getText().toString());
        int yop5 = Integer.parseInt(yoPl1RunsE.getText().toString());

        int op1 = Integer.parseInt(chlPl1RunsE.getText().toString());
        int op2 = Integer.parseInt(chlPl2RunsE.getText().toString());
        int op3 = Integer.parseInt(chlPl3RunsE.getText().toString());
        int op4 = Integer.parseInt(chlPl4RunsE.getText().toString());
        int op5 = Integer.parseInt(chlPl5RunsE.getText().toString());

        int yop1over = Integer.parseInt(yoPl1OversE.getText().toString());
        int yop2over = Integer.parseInt(yoPl2OversE.getText().toString());
        int yop3over = Integer.parseInt(yoPl3OversE.getText().toString());
        int yop4over = Integer.parseInt(yoPl4OversE.getText().toString());
        int yop5over = Integer.parseInt(yoPl5OversE.getText().toString());

        int op1over = Integer.parseInt(chlPl1OversE.getText().toString());
        int op2over = Integer.parseInt(chlPl2OversE.getText().toString());
        int op3over = Integer.parseInt(chlPl3OversE.getText().toString());
        int op4over = Integer.parseInt(chlPl4OversE.getText().toString());
        int op5over = Integer.parseInt(chlPl5OversE.getText().toString());

        int yop1wkt = Integer.parseInt(yoPl1WktsE.getText().toString());
        int yop2wkt= Integer.parseInt(yoPl2WktsE.getText().toString());
        int yop3wkt = Integer.parseInt(yoPl3WktsE.getText().toString());
        int yop4wkt = Integer.parseInt(yoPl4WktsE.getText().toString());
        int yop5wkt = Integer.parseInt(yoPl5WktsE.getText().toString());

        int op1wkt = Integer.parseInt(chlPl1WktsE.getText().toString());
        int op2wkt = Integer.parseInt(chlPl2WktsE.getText().toString());
        int op3wkt = Integer.parseInt(chlPl3WktsE.getText().toString());
        int op4wkt = Integer.parseInt(chlPl4WktsE.getText().toString());
        int op5wkt = Integer.parseInt(chlPl5WktsE.getText().toString());

        int yoteamwick = yop1wkt + yop2wkt + yop3wkt + yop4wkt + yop5wkt;
        int oppteamwick = op1wkt + op2wkt + op3wkt + op4wkt + op5wkt;

        int yoteam0ver = yop1over + yop2over + yop3over + yop4over + yop5over;
        int oppteamover = op1over + op2over + op3over + op4over + op5over;

        if (isInternetOn()) {
            if(MainActivity.getUserPlayerObject().getType().equalsIgnoreCase("admin")) {

                if (startMatch.getText().toString().equalsIgnoreCase("Start Match")) {
                    /*TODO: Accepting the request part here*/
                    // Accepting the request part

                    selectedTeam.setPlaying(true); // Make changes here
                    MainActivity.setUserTeamObject(selectedTeam); // Make changes in MainActivity
                    dbManager.updateTeamAttributeInFirebase(DatabaseManager.getDbMemberNameForTeam(),
                            selectedTeam); // Make changes in Fire Base

                    setEditors(true); // now it is editable

                    statusTxt.setText("You Accepted The Match...");
                    startMatch.setText("Finish Match"); // Change the button now

                } else if (startMatch.getText().toString().equalsIgnoreCase("Finish Match")) {
                    /*TODO: Finishing the match part here*/
                    // Finishing the Match part
                    if (isEmptyFields()) {

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206, 205, 205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190, 39, 39));
                        text.setTextSize(13);
                        text.setPadding(10, 10, 10, 10);
                        text.setText("Some fields are empty");
                        toast.setView(text);
                        toast.show();

                    } else if(yop1 < 0 || yop2 < 0 || yop3 < 0 || yop4 < 0 || yop5 < 0 || op1 < 0 || op2 < 0 || op3 < 0 || op4 < 0 || op5 < 0) {

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206, 205, 205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190, 39, 39));
                        text.setTextSize(13);
                        text.setPadding(10, 10, 10, 10);
                        text.setText("Player can score minimum runs is 0");
                        toast.setView(text);
                        toast.show();

                    }else if(yop1over < 0 || yop2over < 0 || yop3over < 0 || yop4over < 0 || yop5over < 0 || op1over < 0 || op2over < 0
                            || op3over < 0 || op4over < 0 || op5over < 0) {

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206, 205, 205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190, 39, 39));
                        text.setTextSize(13);
                        text.setPadding(10, 10, 10, 10);
                        text.setText("Minimum over can play by bowler is 0");
                        toast.setView(text);
                        toast.show();

                    }else if(yop1wkt < 0 || yop2wkt < 0 || yop3wkt < 0 || yop4wkt < 0 || yop5wkt < 0 || op1wkt < 0 || op2wkt < 0
                            || op3wkt < 0 || op4wkt < 0 || op5wkt < 0) {

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206, 205, 205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190, 39, 39));
                        text.setTextSize(13);
                        text.setPadding(10, 10, 10, 10);
                        text.setText("Minimum wickets bowler can get 0");
                        toast.setView(text);
                        toast.show();

                    }else if(yoteam0ver > 10 || oppteamover > 10){

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206, 205, 205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190, 39, 39));
                        text.setTextSize(13);
                        text.setPadding(10, 10, 10, 10);
                        text.setText("One team can play maximum overs is 10");
                        toast.setView(text);
                        toast.show();

                    }else if(yoteamwick > 5 || oppteamwick > 5){

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP, 0, 50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206, 205, 205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps", Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(190, 39, 39));
                        text.setTextSize(13);
                        text.setPadding(10, 10, 10, 10);
                        text.setText("One team can get maximum wicket is 5");
                        toast.setView(text);
                        toast.show();

                    }else{

                        dbManager.addMatchDetailsToFirebase(DatabaseManager.getDbMemberNameForMatchDetails(),
                                matchDetails);

                        updateEndingMatch(); // All database changes are here

                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.TOP,0,50);

                        TextView text = new TextView(MatchActivity.this);
                        text.setBackgroundColor(Color.rgb(206,205,205));

                        Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                        text.setTypeface(typeface);
                        text.setTextColor(Color.rgb(0,128,0));
                        text.setTextSize(13);
                        text.setPadding(10,10,10,10);
                        text.setText("Match finished successfully ");
                        toast.setView(text);
                        toast.show();

                        intent = new Intent(MatchActivity.this, MatchSummaryActivity.class);
                        startActivity(intent);

                    }
                }

            }else{

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP,0,50);

                TextView text = new TextView(MatchActivity.this);
                text.setBackgroundColor(Color.rgb(206,205,205));

                Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                text.setTypeface(typeface);
                text.setTextColor(Color.rgb(190,39,39));
                text.setTextSize(13);
                text.setPadding(10,10,10,10);
                text.setText("Only Admin Is Allowed!");
                toast.setView(text);
                toast.show();

            }

        } else {

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(MatchActivity.this);
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

    public void cancelClickedInMatchPage(View view) {
        if (isInternetOn()) {
            if (MainActivity.getUserPlayerObject().getType().equalsIgnoreCase("admin")) {
                updateEndingMatch(); // Database changing's are here

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP,0,50);

                TextView text = new TextView(MatchActivity.this);
                text.setBackgroundColor(Color.rgb(206,205,205));

                Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                text.setTypeface(typeface);
                text.setTextColor(Color.rgb(0,128,0));
                text.setTextSize(13);
                text.setPadding(10,10,10,10);
                text.setText("Match cancelled successfully!");
                toast.setView(text);
                toast.show();

                // Go back to the home
                intent = new Intent(MatchActivity.this, UserWithTeamActivity.class);
                startActivity(intent);
            }else{
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.TOP,0,50);

                TextView text = new TextView(MatchActivity.this);
                text.setBackgroundColor(Color.rgb(206,205,205));

                Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
                text.setTypeface(typeface);
                text.setTextColor(Color.rgb(190,39,39));
                text.setTextSize(13);
                text.setPadding(10,10,10,10);
                text.setText("Only Admin is allowed!");
                toast.setView(text);
                toast.show();
            }

        } else {

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(MatchActivity.this);
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

    protected void updateEndingMatch() {
        /*TODO: Update your team + false*/

        //Updating the your team object
        selectedTeam.setPlaying(false);
        selectedTeam.setChallenger("no");

        MainActivity.setUserTeamObject(selectedTeam); // set in MainActivity
        dbManager.updateTeamAttributeInFirebase(DatabaseManager.getDbMemberNameForTeam(),
                selectedTeam); // set in fire base


        /*TODO: Update challenger + if(false)false*/
        challengerTeam.setChallenger("no");
        challengerTeam.setPlaying(false);

        //set challenger team in fire base
        dbManager.updateTeamAttributeInFirebase(DatabaseManager.getDbMemberNameForTeam(),
                challengerTeam);
    }

    protected void assignLayouts() {
        statusTxt = findViewById(R.id.statusTxtInMatchPage);

        yoTeam = findViewById(R.id.yourTeamNameTxtInMatchPage);
        yoPlayer1 = findViewById(R.id.yourPlayer1txtInMatchPage);
        yoPlayer2 = findViewById(R.id.yourPlayer2txtInMatchPage);
        yoPlayer3 = findViewById(R.id.yourPlayer3txtInMatchPage);
        yoPlayer4 = findViewById(R.id.yourPlayer4txtInMatchPage);
        yoPlayer5 = findViewById(R.id.yourPlayer5txtInMatchPage);

        chlTeam = findViewById(R.id.challengerTeamNameTxtInMatchPage);
        chlPlayer1 = findViewById(R.id.challengerPlayer1txtInMatchPage);
        chlPlayer2 = findViewById(R.id.challengerPlayer2txtInMatchPage);
        chlPlayer3 = findViewById(R.id.challengerPlayer3txtInMatchPage);
        chlPlayer4 = findViewById(R.id.challengerPlayer4txtInMatchPage);
        chlPlayer5 = findViewById(R.id.challengerPlayer5txtInMatchPage);

        startMatch = findViewById(R.id.startMatchBtnInMatchPage);
        cancelMatch = findViewById(R.id.cancelMatchBtnInMatchPage);

        yoPl1RunsE = findViewById(R.id.yourPlayer1RunsInMatchPage);
        yoPl1OversE = findViewById(R.id.yourPlayer1OversInMatchPage);
        yoPl1WktsE = findViewById(R.id.yourPlayer1WktsInMatchPage);

        yoPl2RunsE = findViewById(R.id.yourPlayer2RunsInMatchPage);
        yoPl2OversE = findViewById(R.id.yourPlayer2OversInMatchPage);
        yoPl2WktsE = findViewById(R.id.yourPlayer2WktsInMatchPage);

        yoPl3RunsE = findViewById(R.id.yourPlayer3RunsInMatchPage);
        yoPl3OversE = findViewById(R.id.yourPlayer3OversInMatchPage);
        yoPl3WktsE = findViewById(R.id.yourPlayer3WktsInMatchPage);

        yoPl4RunsE = findViewById(R.id.yourPlayer4RunsInMatchPage);
        yoPl4OversE = findViewById(R.id.yourPlayer4OversInMatchPage);
        yoPl4WktsE = findViewById(R.id.yourPlayer4WktsInMatchPage);

        yoPl5RunsE = findViewById(R.id.yourPlayer5RunsInMatchPage);
        yoPl5OversE = findViewById(R.id.yourPlayer5OversInMatchPage);
        yoPl5WktsE = findViewById(R.id.yourPlayer5WktsInMatchPage);

        chlPl1RunsE = findViewById(R.id.challengerPlayer1RunsInMatchPage);
        chlPl1OversE = findViewById(R.id.challengerPlayer1OversInMatchPage);
        chlPl1WktsE = findViewById(R.id.challengerPlayer1WktsInMatchPage);

        chlPl2RunsE = findViewById(R.id.challengerPlayer2RunsInMatchPage);
        chlPl2OversE = findViewById(R.id.challengerPlayer2OversInMatchPage);
        chlPl2WktsE = findViewById(R.id.challengerPlayer2WktsInMatchPage);

        chlPl3RunsE = findViewById(R.id.challengerPlayer3RunsInMatchPage);
        chlPl3OversE = findViewById(R.id.challengerPlayer3OversInMatchPage);
        chlPl3WktsE = findViewById(R.id.challengerPlayer3WktsInMatchPage);

        chlPl4RunsE = findViewById(R.id.challengerPlayer4RunsInMatchPage);
        chlPl4OversE = findViewById(R.id.challengerPlayer4OversInMatchPage);
        chlPl4WktsE = findViewById(R.id.challengerPlayer4WktsInMatchPage);

        chlPl5RunsE = findViewById(R.id.challengerPlayer5RunsInMatchPage);
        chlPl5OversE = findViewById(R.id.challengerPlayer5OversInMatchPage);
        chlPl5WktsE = findViewById(R.id.challengerPlayer5WktsInMatchPage);
    }

    protected void setEditors(Boolean io) {
        yoPl1RunsE.setEnabled(io);
        yoPl1OversE.setEnabled(io);
        yoPl1WktsE.setEnabled(io);

        yoPl2RunsE.setEnabled(io);
        yoPl2OversE.setEnabled(io);
        yoPl2WktsE.setEnabled(io);

        yoPl3RunsE.setEnabled(io);
        yoPl3OversE.setEnabled(io);
        yoPl3WktsE.setEnabled(io);

        yoPl4RunsE.setEnabled(io);
        yoPl4OversE.setEnabled(io);
        yoPl4WktsE.setEnabled(io);

        yoPl5RunsE.setEnabled(io);
        yoPl5OversE.setEnabled(io);
        yoPl5WktsE.setEnabled(io);

        chlPl1RunsE.setEnabled(io);
        chlPl1OversE.setEnabled(io);
        chlPl1WktsE.setEnabled(io);

        chlPl2RunsE.setEnabled(io);
        chlPl2OversE.setEnabled(io);
        chlPl2WktsE.setEnabled(io);

        chlPl3RunsE.setEnabled(io);
        chlPl3OversE.setEnabled(io);
        chlPl3WktsE.setEnabled(io);

        chlPl4RunsE.setEnabled(io);
        chlPl4OversE.setEnabled(io);
        chlPl4WktsE.setEnabled(io);

        chlPl5RunsE.setEnabled(io);
        chlPl5OversE.setEnabled(io);
        chlPl5WktsE.setEnabled(io);
    }

    public void goToHomeClickedInMatchPage(View view) {
        intent = new Intent(MatchActivity.this, UserWithTeamActivity.class);
        startActivity(intent);
    }

    protected Boolean isEmptyFields() {
        matchDetails.setYoTeam(selectedTeam.getName());

        matchDetails.setYoPl1(yoPlayer1.getText().toString());
        matchDetails.setYoPl1Runs(yoPl1RunsE.getText().toString());
        matchDetails.setYoPl1Overs(yoPl1OversE.getText().toString());
        matchDetails.setYoPl1Wkts(yoPl1WktsE.getText().toString());

        matchDetails.setYoPl2(yoPlayer2.getText().toString());
        matchDetails.setYoPl2Runs(yoPl2RunsE.getText().toString());
        matchDetails.setYoPl2Overs(yoPl2OversE.getText().toString());
        matchDetails.setYoPl2Wkts(yoPl2WktsE.getText().toString());

        matchDetails.setYoPl3(yoPlayer3.getText().toString());
        matchDetails.setYoPl3Runs(yoPl3RunsE.getText().toString());
        matchDetails.setYoPl3Overs(yoPl3OversE.getText().toString());
        matchDetails.setYoPl3Wkts(yoPl3WktsE.getText().toString());

        matchDetails.setYoPl4(yoPlayer4.getText().toString());
        matchDetails.setYoPl4Runs(yoPl4RunsE.getText().toString());
        matchDetails.setYoPl4Overs(yoPl4OversE.getText().toString());
        matchDetails.setYoPl4Wkts(yoPl4WktsE.getText().toString());

        matchDetails.setYoPl5(yoPlayer5.getText().toString());
        matchDetails.setYoPl5Runs(yoPl5RunsE.getText().toString());
        matchDetails.setYoPl5Overs(yoPl5OversE.getText().toString());
        matchDetails.setYoPl5Wkts(yoPl5WktsE.getText().toString());

        matchDetails.setChlTeam(challengerTeam.getName());

        matchDetails.setChlPl1(chlPlayer1.getText().toString());
        matchDetails.setChlPl1Runs(chlPl1RunsE.getText().toString());
        matchDetails.setChlPl1Overs(chlPl1OversE.getText().toString());
        matchDetails.setChlPl1Wkts(chlPl1WktsE.getText().toString());

        matchDetails.setChlPl2(chlPlayer2.getText().toString());
        matchDetails.setChlPl2Runs(chlPl2RunsE.getText().toString());
        matchDetails.setChlPl2Overs(chlPl2OversE.getText().toString());
        matchDetails.setChlPl2Wkts(chlPl2WktsE.getText().toString());

        matchDetails.setChlPl3(chlPlayer3.getText().toString());
        matchDetails.setChlPl3Runs(chlPl3RunsE.getText().toString());
        matchDetails.setChlPl3Overs(chlPl3OversE.getText().toString());
        matchDetails.setChlPl3Wkts(chlPl3WktsE.getText().toString());

        matchDetails.setChlPl4(chlPlayer4.getText().toString());
        matchDetails.setChlPl4Runs(chlPl4RunsE.getText().toString());
        matchDetails.setChlPl4Overs(chlPl4OversE.getText().toString());
        matchDetails.setChlPl4Wkts(chlPl4WktsE.getText().toString());

        matchDetails.setChlPl5(chlPlayer5.getText().toString());
        matchDetails.setChlPl5Runs(chlPl5RunsE.getText().toString());
        matchDetails.setChlPl5Overs(chlPl5OversE.getText().toString());
        matchDetails.setChlPl5Wkts(chlPl5WktsE.getText().toString());


        if (matchDetails.getYoPl1Runs().equalsIgnoreCase("") ||
                matchDetails.getYoPl1Overs().equalsIgnoreCase("") ||
                matchDetails.getYoPl1Wkts().equalsIgnoreCase("") ||
                matchDetails.getYoPl2Runs().equalsIgnoreCase("") ||
                matchDetails.getYoPl2Overs().equalsIgnoreCase("") ||
                matchDetails.getYoPl2Wkts().equalsIgnoreCase("") ||
                matchDetails.getYoPl3Runs().equalsIgnoreCase("") ||
                matchDetails.getYoPl3Overs().equalsIgnoreCase("") ||
                matchDetails.getYoPl3Wkts().equalsIgnoreCase("") ||
                matchDetails.getYoPl4Runs().equalsIgnoreCase("") ||
                matchDetails.getYoPl4Overs().equalsIgnoreCase("") ||
                matchDetails.getYoPl4Wkts().equalsIgnoreCase("") ||
                matchDetails.getYoPl5Runs().equalsIgnoreCase("") ||
                matchDetails.getYoPl5Overs().equalsIgnoreCase("") ||
                matchDetails.getYoPl5Wkts().equalsIgnoreCase("") ||
                matchDetails.getChlPl1Runs().equalsIgnoreCase("") ||
                matchDetails.getChlPl1Overs().equalsIgnoreCase("") ||
                matchDetails.getChlPl1Wkts().equalsIgnoreCase("") ||
                matchDetails.getChlPl2Runs().equalsIgnoreCase("") ||
                matchDetails.getChlPl2Overs().equalsIgnoreCase("") ||
                matchDetails.getChlPl2Wkts().equalsIgnoreCase("") ||
                matchDetails.getChlPl3Runs().equalsIgnoreCase("") ||
                matchDetails.getChlPl3Overs().equalsIgnoreCase("") ||
                matchDetails.getChlPl3Wkts().equalsIgnoreCase("") ||
                matchDetails.getChlPl4Runs().equalsIgnoreCase("") ||
                matchDetails.getChlPl4Overs().equalsIgnoreCase("") ||
                matchDetails.getChlPl4Wkts().equalsIgnoreCase("") ||
                matchDetails.getChlPl5Runs().equalsIgnoreCase("") ||
                matchDetails.getChlPl5Overs().equalsIgnoreCase("") ||
                matchDetails.getChlPl5Wkts().equalsIgnoreCase("")
        ) {
            return true; // Yeah, it seems some fields are empty!!!
        } else {
            return false; // Alright here dude!
        }
    }

    /* To check the internet connection */
    public Boolean isInternetOn() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(MatchActivity.this, UserWithTeamActivity.class);
        startActivity(intent);
    }



}
