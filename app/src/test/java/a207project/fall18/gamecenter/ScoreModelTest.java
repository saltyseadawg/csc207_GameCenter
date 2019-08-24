package a207project.fall18.gamecenter;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ScoreModelTest {
    /**
     * An instance of ScoreModel.
     */
    private ScoreModel scoreModel;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Mock
    FirebaseApp firebaseApp;


    @Before // setup()
    public void before() {
        firebaseAuth.createUserWithEmailAndPassword("testaccount@gmail.com", "abc1234");
        scoreModel = new ScoreModel("Snake");
        scoreModel.databaseReference.child("users").child(scoreModel.currentUser.getUid()).child("snakeScores").child("score4").setValue(12);
        scoreModel.databaseReference.child("users").child(scoreModel.currentUser.getUid()).child("snakeScores").child("score3").setValue(4);
        scoreModel.databaseReference.child("users").child(scoreModel.currentUser.getUid()).child("snakeScores").child("score2").setValue(3);
    }
    @Test
    public void loadScores(){
        int expectedScores[] = {0, 0, 3, 4, 12};
        scoreModel.loadScores();
        assertArrayEquals(scoreModel.userScores, expectedScores);
    }
    @After //tearDown()
    public void after(){
        scoreModel.databaseReference.child("users").child(scoreModel.currentUser.getUid()).removeValue();
    }
}
