package a207project.fall18.gamecenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ScoreModel {
    /**
     * String of the name for the current game.
     */
    String currentGame;

    /**
     * The current user.
     */
    FirebaseUser currentUser;

    /**
     * The current user's username.
     */
    String username;

    /**
     * Reference to the FireBase database.
     */
    DatabaseReference databaseReference;

    /**
     * A list of the user's high scores for the current game.
     */
    int[] userScores = new int[5];

    /**
     * A list of the high scores for the current game.
     */
    int[] bestScores = new int[5];


    /**
     * The score model for the current game
     * @param game an integer that represents one of the three games
     */
    ScoreModel(int game) {
        currentGame = GameCenterActivity.GAME_LIST[game];
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        username = currentUser.getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * The score model for the current game
     * @param game a string that represents one of the three games
     */
    ScoreModel(String game) {
        currentGame = game;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        username = currentUser.getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
