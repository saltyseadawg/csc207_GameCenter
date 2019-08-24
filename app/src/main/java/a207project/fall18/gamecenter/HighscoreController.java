package a207project.fall18.gamecenter;


import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class HighscoreController{
    /**
     * The score being analyzed.
     */
    private int score;
    /**
     * The game that the score is from.
     */
    private String game;
    /**
     * An instance of ScoreModel.
     */
    private ScoreModel scoreModel;
    /**
     * The name of the game.
     */
    private String gameName;

    HighscoreController(String currentGame, int newScore){
        score = newScore;
        game = currentGame;
        scoreModel = new ScoreModel(game);

        // Get game name in suitable format
        switch (game){
            case "Sliding Tiles":
                gameName = "slidingTiles";
                break;
            case "Snake":
                gameName = "snake";
                break;
            case "2048":
                gameName = "2048";
                break;
        }


        loadScores();
    }

    /**
     * Saving the score to the user's scoreboard.
     */
    void saveToUserScores(){
        int minValue = score;
        int minIndex = 0;
        for (int i = 0; i < 5; i++){
            if (scoreModel.userScores[i] <= minValue){
                minValue = scoreModel.userScores[i];
                minIndex = i;
            }
        }
        if (minValue <= score){
            scoreModel.databaseReference.child("users").child(scoreModel.currentUser.getUid()).child(String.format("%sScores", gameName)).child(String.format("score%d", minIndex)).setValue(score);
        }
    }

    /**
     * Saving the score to the all time scoreboard.
     */
    void saveToTopScores(){
        int minValue = score;
        int minIndex = 0;
        for (int i = 0; i < 5; i++){
            if (scoreModel.bestScores[i] <= minValue){
                minValue = scoreModel.bestScores[i];
                minIndex = i;
            }
        }
        if (minValue <= score){
            scoreModel.databaseReference.child("games").child(gameName).child("topScores").child(String.format("score%d", minIndex)).setValue(score);
            scoreModel.databaseReference.child("games").child(gameName).child("topPlayers").child(String.format("player%d", minIndex)).setValue(scoreModel.username);
        }
    }

    /**
     * Loading scores from the database.
     */
    private void loadScores() {
        scoreModel.databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Iterate through database and pull scores into lists
                int userScore;
                int bestScore;
                for (int i = 0; i < 5; i++) {
                    try {
                        userScore = dataSnapshot.child("users").child(scoreModel.currentUser.getUid()).child(String.format("%sScores", game)).child(String.format("score%d", i)).getValue(Integer.class);
                        scoreModel.userScores[i] = userScore;

                    } catch (Exception e) {
                        scoreModel.userScores[i] = 0;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    try {
                        bestScore = dataSnapshot.child("games").child(game).child("topScores").child(String.format("score%d", i)).getValue(Integer.class);
                        scoreModel.bestScores[i] = bestScore;

                    } catch (Exception e) {
                        scoreModel.bestScores[i] = 0;
                    }
                }
                saveToTopScores();
                saveToUserScores();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

}
