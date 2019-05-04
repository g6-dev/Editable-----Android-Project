package android.g6.cricspot;

import android.content.Intent;
import android.g6.cricspot.CricClasses.DatabaseManager;
import android.g6.cricspot.CricClasses.TwoRowListAdapter;
import android.g6.cricspot.CricObjects.NameAndLocation;
import android.g6.cricspot.CricObjects.Player;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FindPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt;
    ListView listView;
    List<Player> listOfAllPlayers = new ArrayList<>();
    List<NameAndLocation> playersNameLocationList = new ArrayList<>();
    TwoRowListAdapter listAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_player);

        txt = findViewById(R.id.txtInFindPlayersPage);
        listView = findViewById(R.id.listViewerInFindPlayerPage);

        listOfAllPlayers.clear();
        listOfAllPlayers = DatabaseManager.getPlayersList();

        Button scroll = (Button)findViewById(R.id.back);
        scroll.setOnClickListener(this);

        playersNameLocationList.clear();

        //Get the players who doesn't have a team yet!!!
        for (Player player: listOfAllPlayers){ // Run whole players
            if(player.getTeam().equalsIgnoreCase("no")) { // players with no team ! :D
                playersNameLocationList.add(new NameAndLocation(player.getUserName(), player.getLocation()));
                System.out.println(">>>>> player: " + player);
            }
        }

        listAdapter = new TwoRowListAdapter(FindPlayerActivity.this, R.layout.listview_2row_activity,
                playersNameLocationList);
        listView.setAdapter(listAdapter);

        /* ListViewer onClick Listener */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView) view.findViewById(R.id.row1)).getText().toString();

                intent = new Intent(FindPlayerActivity.this, PlayerDetailsActivity.class);
                intent.putExtra("tester", selectedItem);
                startActivity(intent);
                //Toast.makeText(UserWithoutTeamActivity.this, "Yet in Maintenance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //back page
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, UserWithTeamActivity.class);
                startActivity(intent);
                break;
        }
    }
}
