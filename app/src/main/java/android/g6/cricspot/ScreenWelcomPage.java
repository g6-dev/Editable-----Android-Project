package android.g6.cricspot;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import static java.lang.Thread.sleep;

public class ScreenWelcomPage extends AppCompatActivity {

    ProgressBar progressBar;
    private int ProgressPersent = 0;
    private static int Time = 0;

    //welcome screen timing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_welcom_page);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenWelcomPage.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },Time);

        //progress Bar
        progressBar = (ProgressBar)findViewById(R.id.Progress_Bar);
        progressBar.setProgress(0);
        progressBar.setMax(100);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(ProgressPersent < 100){
                    try {
                        sleep(100);
                        ProgressPersent+=3;
                        progressBar.setProgress(ProgressPersent);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

}
