package android.g6.cricspot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Creaters extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creaters);

        Button scroll = (Button)findViewById(R.id.button);
        scroll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }

    }
}
