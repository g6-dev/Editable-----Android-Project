package android.g6.cricspot;

import android.content.Intent;
import android.g6.cricspot.CricObjects.MatchDetails;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MatchSummaryActivity extends AppCompatActivity {

    TextView summaryTxt, winnerTxt, bestBatsmanTxt, bestBowlerTxt, loserTxt;
    EditText winner, bestBatsman, bestBowler, loser;
    Button continueBtn, shareBtn;
    Intent intent;
    MatchDetails md = MatchActivity.getMatchDetails();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);

        summaryTxt = findViewById(R.id.summaryTxtInMatchSummary);
        winnerTxt = findViewById(R.id.winnnerTxtInMatchSummary);
        bestBatsmanTxt = findViewById(R.id.bestBatsmanTxtInMatchSummary);
        bestBowlerTxt = findViewById(R.id.bestBowlerTxtInMatchSummary);
        loserTxt = findViewById(R.id.loserTxtInMatchSummary);

        winner = findViewById(R.id.winnerInMatchSummary);
        bestBatsman = findViewById(R.id.bestBatsmanInMatchSummary);
        bestBowler = findViewById(R.id.bestBowlerInMatchSummary);
        loser = findViewById(R.id.loserInMatchSummary);

        continueBtn = findViewById(R.id.continueBtnInMatchSummary);
        shareBtn = findViewById(R.id.shareBtnInMatchSummary);

        System.out.println(">>>>> yoPL runs "+md.getYoPl1Runs()+", to int  -> "+Integer.valueOf(md.getYoPl1Runs()));

        int yoTeamTotal = Integer.valueOf(md.getYoPl1Runs()) + Integer.valueOf(md.getYoPl2Runs()) +
                Integer.valueOf(md.getYoPl3Runs()) + Integer.valueOf(md.getYoPl4Runs()) +
                Integer.valueOf(md.getYoPl5Runs());
        System.out.println(">>>>> yo team total : " + yoTeamTotal);

        int chlTeamTotal = Integer.valueOf(md.getChlPl1Runs()) + Integer.valueOf(md.getChlPl2Runs()) +
                Integer.valueOf(md.getChlPl3Runs()) + Integer.valueOf(md.getChlPl4Runs()) +
                Integer.valueOf(md.getChlPl5Runs());
        System.out.println(">>>>> chl team total : " + chlTeamTotal);

        if (yoTeamTotal > chlTeamTotal){

            winner.setText(md.getYoTeam());

            findBestBatsman(md.getYoPl1(), Integer.valueOf(md.getYoPl1Runs()),
                    md.getYoPl2(), Integer.valueOf(md.getYoPl2Runs()),
                    md.getYoPl3(), Integer.valueOf(md.getYoPl3Runs()),
                    md.getYoPl4(), Integer.valueOf(md.getYoPl4Runs()),
                    md.getYoPl5(), Integer.valueOf(md.getYoPl5Runs()));

            findBestBowler(md.getYoPl1(),Double.valueOf(md.getYoPl1Overs()),Double.valueOf(md.getYoPl1Wkts()),
                    md.getYoPl2(),Double.valueOf(md.getYoPl2Overs()),Double.valueOf(md.getYoPl2Wkts()),
                    md.getYoPl3(),Double.valueOf(md.getYoPl3Overs()),Double.valueOf(md.getYoPl3Wkts()),
                    md.getYoPl4(),Double.valueOf(md.getYoPl4Overs()),Double.valueOf(md.getYoPl4Wkts()),
                    md.getYoPl5(),Double.valueOf(md.getYoPl5Overs()),Double.valueOf(md.getYoPl5Wkts()));

            loser.setText(md.getChlTeam());

        }else{

            winner.setText(md.getChlTeam());

            findBestBatsman(md.getChlPl1(), Integer.valueOf(md.getChlPl1Runs()),
                    md.getChlPl2(), Integer.valueOf(md.getChlPl2Runs()),
                    md.getChlPl3(), Integer.valueOf(md.getChlPl3Runs()),
                    md.getChlPl4(), Integer.valueOf(md.getChlPl4Runs()),
                    md.getChlPl5(), Integer.valueOf(md.getChlPl5Runs()));

            findBestBowler(md.getChlPl1(),Double.valueOf(md.getChlPl1Overs()),Double.valueOf(md.getChlPl1Wkts()),
                    md.getChlPl2(),Double.valueOf(md.getChlPl2Overs()),Double.valueOf(md.getChlPl2Wkts()),
                    md.getChlPl3(),Double.valueOf(md.getChlPl3Overs()),Double.valueOf(md.getChlPl3Wkts()),
                    md.getChlPl4(),Double.valueOf(md.getChlPl4Overs()),Double.valueOf(md.getChlPl4Wkts()),
                    md.getChlPl5(),Double.valueOf(md.getChlPl5Overs()),Double.valueOf(md.getChlPl5Wkts()));

            loser.setText(md.getYoTeam());

        }

        isEditable(false);

    }

    public void continueClickedInMatchSummary(View view) {
        intent = new Intent(MatchSummaryActivity.this, UserWithTeamActivity.class);
        startActivity(intent);
    }

    public void shareClickedInMatchSummary(View view) {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP,0,50);

        TextView text = new TextView(MatchSummaryActivity.this);
        text.setBackgroundColor(Color.rgb(206,205,205));

        Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
        text.setTypeface(typeface);
        text.setTextColor(Color.rgb(190,39,39));
        text.setTextSize(13);
        text.setPadding(10,10,10,10);
        text.setText("Sharing in maintenance!");
        toast.setView(text);
        toast.show();
    }

    protected void findBestBatsman(String pl1, int pl1Runs, String pl2, int pl2Runs,
                                   String pl3, int pl3Runs, String pl4, int pl4Runs,
                                   String pl5, int pl5Runs){

        System.out.println(">>>>> MatchSum : starting bats : ");
        if(pl1Runs > pl2Runs && pl1Runs > pl3Runs && pl1Runs > pl4Runs && pl1Runs > pl5Runs){
            bestBatsman.setText(pl1);
            System.out.println(">>>>> MatchSum : starting bats : "+pl1);
        }else if(pl2Runs > pl3Runs && pl2Runs > pl4Runs && pl2Runs > pl5Runs){
            bestBatsman.setText(pl2);
            System.out.println(">>>>> MatchSum : starting bats : "+pl2);
        }else if(pl3Runs > pl4Runs && pl3Runs > pl5Runs) {
            bestBatsman.setText(pl3);
            System.out.println(">>>>> MatchSum : starting bats : "+pl3);
        }else if(pl4Runs > pl5Runs){
            bestBatsman.setText(pl4);
            System.out.println(">>>>> MatchSum : starting bats : "+pl4);
        }else{
            bestBatsman.setText(pl5);
            System.out.println(">>>>> MatchSum : starting bats : "+pl5);
        }

        System.out.println(">>>>> best batsman: " + bestBatsman.getText());
    }

    protected void findBestBowler(String pl1, double pl1Ovs, double pl1Wkt,
                                  String pl2, double pl2Ovs, double pl2Wkt,
                                  String pl3, double pl3Ovs, double pl3Wkt,
                                  String pl4, double pl4Ovs, double pl4Wkt,
                                  String pl5, double pl5Ovs, double pl5Wkt){

        double pl1Rate, pl2Rate, pl3Rate, pl4Rate, pl5Rate;

        if(pl1Wkt != 0) {
            pl1Rate = (pl1Ovs*6) / pl1Wkt;
        }else{
            pl1Rate = pl1Ovs*6;
        }

        if(pl2Wkt != 0) {
            pl2Rate = (pl2Ovs*6) / pl2Wkt;
        }else{
            pl2Rate = pl2Ovs*6;
        }

        if(pl3Wkt != 0) {
            pl3Rate = (pl3Ovs*6) / pl3Wkt;
        }else{
            pl3Rate = pl3Ovs*6;
        }

        if(pl4Wkt != 0) {
            pl4Rate = (pl4Ovs*6) / pl4Wkt;
        }else{
            pl4Rate = pl4Ovs*6;
        }

        if(pl5Wkt != 0) {
            pl5Rate = (pl5Ovs*6) / pl5Wkt;
        }else{
            pl5Rate = pl5Ovs*6;
        }


        if (pl1Rate<pl2Rate && pl1Rate<pl3Rate && pl1Rate<pl4Rate && pl1Rate<pl5Rate){
            bestBowler.setText(pl1);
        }else if(pl2Rate<pl3Rate && pl2Rate<pl4Rate && pl2Rate<pl5Rate){
            bestBowler.setText(pl2);
        }else if(pl3Rate<pl4Rate && pl3Rate<pl5Rate){
            bestBowler.setText(pl3);
        }else if(pl4Rate<pl5Rate){
            bestBowler.setText(pl4);
        }else{
            bestBowler.setText(pl5);
        }

        System.out.println(">>>>> best bowler: "+bestBowler.getText());
    }

    protected void isEditable(Boolean io){
        winner.setEnabled(io);
        bestBatsman.setEnabled(io);
        bestBowler.setEnabled(io);
        loser.setEnabled(io);
    }
}
