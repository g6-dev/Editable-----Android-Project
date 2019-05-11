package android.g6.cricspot;

import android.content.Intent;
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
}
