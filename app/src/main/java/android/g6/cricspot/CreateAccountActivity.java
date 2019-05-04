package android.g6.cricspot;

import android.content.Context;
import android.content.Intent;
import android.g6.cricspot.CricClasses.DatabaseManager;
import android.g6.cricspot.CricObjects.Player;
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


public class CreateAccountActivity extends AppCompatActivity {

    final static String dbMemberNameForPlayer = "Player";

    TextView nameTxt, userNameTxt, passwordTxt, ageTxt, phoneTxt, txtErr, locationTxt;
    EditText nameE, userNameE, passwordE, ageE, phoneE, locationE;
    String name, userName, password, age, phone, location;
    Button createAccountBtn;
    Player player;
    List<Player> playerList;
    DatabaseManager dbManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        /* Initialize variables */
        nameTxt = findViewById(R.id.nameTxtInCreateAccountPage);
        userNameTxt = findViewById(R.id.userNameTxtInCreateAccountPage);
        passwordTxt = findViewById(R.id.passwordTxtInCreateAccountPage);
        ageTxt = findViewById(R.id.ageTxtInCreateAccountPage);
        phoneTxt = findViewById(R.id.phoneTxtInCreateAccountPage);
        txtErr = findViewById(R.id.txtErrInCreateAccountPage);
        locationTxt = findViewById(R.id.locationTxtInCreateAccountPage);

        nameE = findViewById(R.id.nameInCreateAccountPage);
        userNameE = findViewById(R.id.userNameInCreateAccountPage);
        passwordE = findViewById(R.id.passwordInCreateAccountPage);
        ageE = findViewById(R.id.ageInCreateAccountPage);
        phoneE = findViewById(R.id.phoneInCreateAccountPage);
        locationE = findViewById(R.id.locationInCreateAccountPage);

        createAccountBtn = findViewById(R.id.createAccountBtnInCreateAccountPage);

        playerList = new ArrayList<>();
        dbManager = new DatabaseManager();

        /*
        1) Firebase database reference
        2) Link: https://firebase.google.com/docs/android/setup?authuser=0 */
    }

    public void createAccountClickedInCreateAccountPage(View view) {

        /* TODO: Validate user register with same name again!!! */

        /* Get values from user */
        name = nameE.getText().toString();
        userName = userNameE.getText().toString();
        password = passwordE.getText().toString();
        age = ageE.getText().toString();
        phone = phoneE.getText().toString();
        location = locationE.getText().toString();

        if(!isInternetOn()){

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(CreateAccountActivity.this);
            text.setBackgroundColor(Color.rgb(206,205,205));

            Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
            text.setTypeface(typeface);
            text.setTextColor(Color.WHITE);
            text.setTextSize(13);
            text.setPadding(10,10,10,10);
            text.setText("Network Error");
            toast.setView(text);
            toast.show();

        }else if(fieldsAreEmpty(name, userName, password, age, phone, location)){

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,920);

            TextView text = new TextView(CreateAccountActivity.this);
            text.setBackgroundColor(Color.rgb(206,205,205));

            Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
            text.setTypeface(typeface);
            text.setTextColor(Color.WHITE);
            text.setTextSize(13);
            text.setPadding(10,10,10,10);
            text.setText("Some Fields Are Empty!");
            toast.setView(text);
            toast.show();

        }else {
            txtErr.setText("");

            player = new Player(name, userName, password, age, phone, "no", location, "user");

            //Adding the player into firebase
            dbManager.addPlayerToFirebase(dbMemberNameForPlayer, player);

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP,0,50);

            TextView text = new TextView(CreateAccountActivity.this);
            text.setBackgroundColor(Color.rgb(206,205,205));
            Typeface typeface = Typeface.create("sans-serif-smallcaps",Typeface.NORMAL);
            text.setTypeface(typeface);
            text.setTextColor(Color.WHITE);
            text.setTextSize(13);
            text.setPadding(10,10,10,10);
            text.setText("Player "+name+" Added Successfully");
            toast.setView(text);
            toast.show();

            emptyAllTextFields();

            intent = new Intent(CreateAccountActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    // Check any empty inputs
    private boolean fieldsAreEmpty(String name, String userName, String password, String age,
                                   String phone, String location) {

        if(name.equalsIgnoreCase("") || userName.equalsIgnoreCase("") ||
           password.equalsIgnoreCase("") || age.equalsIgnoreCase("") ||
           phone.equalsIgnoreCase("") || location.equalsIgnoreCase("")){
            return true;
        }else {
            return false;
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

    public void emptyAllTextFields(){
        nameE.setText("");
        userNameE.setText("");
        passwordE.setText("");
        ageE.setText("");
        phoneE.setText("");
        locationE.setText("");
    }

}
