package a207project.fall18.gamecenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ScoreActivity extends AppCompatActivity {
    /**
     * the player's username
     */
    String userName;
    /**
     * data being displayed on the score board per user
     */
    TextView userScore1, userScore2, userScore3, userScore4, userScore5, userScore6,userScore7,
            userScore8, userScore9, userScore10;
    List<TextView> userScores;
    /**
     * data being displayed on the score board per game
     */
    TextView allScore1, allScore2, allScore3, allScore4, allScore5, allScore6, allScore7, allScore8,
            allScore9, allScore10;
    List<TextView> allScores;
    /**
     * instance of the scoremodel
     */
    ScoreModel scoreModel;
    /**
     * set size of scoreboard
     */
    int size = 5;
    int[] users = new int[5];
    int[] bests = new int[5];


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Get current scoremodel
        scoreModel = new ScoreModel(getIntent().getIntExtra("game", 0));

        // Get current user scores and best scores
        loadScores();
    }

    /**
     * Retrieves scores from database and puts them into lists for
     * user and best game scores respectively
     */
    private void loadScores() {
        scoreModel.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String game = "";
                switch (scoreModel.currentGame){
                    case "Sliding Tiles":
                        game = "slidingTiles";
                        break;
                    case "Snake":
                        game = "snake";
                        break;
                    case "2048":
                        game = "2048";
                        break;
                }

                // Iterate through database and pull scores into lists
                Integer userScore, bestScore;
                for (int i = 0; i < 5; i++) {
                    try {
                        userScore = dataSnapshot.child("users").child(scoreModel.currentUser
                                .getUid()).child(String.format("%sScores", game))
                                .child(String.format("score%d", i)).getValue(Integer.class);
                        users[i] = userScore;

                    } catch (Exception e) {
                        users[i] = 0;

                    }
                }
                for (int i = 0; i < 5; i++) {
                    try {
                        bestScore = dataSnapshot.child("games").child(game).child("topScores")
                                .child(String.format("score%d", i)).getValue(Integer.class);
                        bests[i] = bestScore;

                    } catch (Exception e) {
                        bests[i] = 0;
                    }
                }
                createScore();
                updateScores();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    /**
     * Sets Textviews for user and best scores after retrieving them by id
     */
    public void createScore(){
        // Scoreboard strings - user
        userScore1 = findViewById(R.id.userScore1);
        userScore2 = findViewById(R.id.userScore2);
        userScore3 = findViewById(R.id.userScore3);
        userScore4 = findViewById(R.id.userScore4);
        userScore5 = findViewById(R.id.userScore5);
        userScore6 = findViewById(R.id.userScore6);
        userScore7 = findViewById(R.id.userScore7);
        userScore8 = findViewById(R.id.userScore8);
        userScore9 = findViewById(R.id.userScore9);
        userScore10 = findViewById(R.id.userScore10);

        userScores = new ArrayList<>(Arrays.asList(userScore1, userScore2, userScore3, userScore4,
                userScore5, userScore6,userScore7, userScore8, userScore9, userScore10));

        // Scoreboard strings - all
        allScore1 = findViewById(R.id.allScore1);
        allScore2 = findViewById(R.id.allScore2);
        allScore3 = findViewById(R.id.allScore3);
        allScore4 = findViewById(R.id.allScore4);
        allScore5 = findViewById(R.id.allScore5);
        allScore6 = findViewById(R.id.allScore6);
        allScore7 = findViewById(R.id.allScore7);
        allScore8 = findViewById(R.id.allScore8);
        allScore9 = findViewById(R.id.allScore9);
        allScore10 = findViewById(R.id.allScore10);

        allScores = new ArrayList<>(Arrays.asList(allScore1, allScore2, allScore3, allScore4,
                allScore5, allScore6, allScore7, allScore8, allScore9, allScore10));

    }


    /**
     * Updates the strings that are displayed on the scoreboard, both user and best scores
     */
    public void updateScores(){
        // Sort current user and best scores
        Arrays.sort(users);
        Arrays.sort(bests);

        // Set values to scoreboard
        for (int i = 0; i < size; i++){
            (userScores.get(i)).setText(String.valueOf(users[(size - 1) - i]));
            (allScores.get(i)).setText(String.valueOf(bests[(size -1) - i]));
        }


    }

}
