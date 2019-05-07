package android.g6.cricspot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PerformanceDisplayScreen extends AppCompatActivity {

    MatchActivity matchActivity = new MatchActivity();

    TextView teamnameprint,runsprint,playernameprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_display_screen);

        teamnameprint = (TextView) findViewById(R.id.teamname);
        playernameprint = (TextView) findViewById(R.id.playername);
        runsprint = (TextView) findViewById(R.id.runs);

        calculation();

    }

    private void calculation() {

        for(int i = 1; i <= matchActivity.performanceList.size();i++){
            int highscore = Integer.parseInt(matchActivity.performanceList.get(0).getRuns());

            if(highscore < Integer.parseInt(matchActivity.performanceList.get(i).getRuns())){
                highscore = Integer.parseInt(matchActivity.performanceList.get(i).getRuns());
                int s = i;

                if(i==4){
                    int score = highscore;
                    String playernames = matchActivity.performanceList.get(s).getPlayername();
                    String team = matchActivity.performanceList.get(s).getTeamname();

                    teamnameprint.setText(team);
                    playernameprint.setText(playernames);
                    runsprint.setText(score);
                }
            }else{
                int score = Integer.parseInt(matchActivity.performanceList.get(0).getRuns());
                String playernames = matchActivity.performanceList.get(0).getPlayername();
                String team = matchActivity.performanceList.get(0).getTeamname();

                teamnameprint.setText(team);
                playernameprint.setText(playernames);
                runsprint.setText(score);

            }
        }
    }

    public void back(View view) {
        Intent intent = new Intent(PerformanceDisplayScreen.this, UserWithTeamActivity.class);
        startActivity(intent);
    }
}
