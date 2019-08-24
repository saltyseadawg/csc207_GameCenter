package a207project.fall18.gamecenter;
//onCreate taken from https://www.mytrendin.com/android-viewpager/

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GameCenterActivity extends AppCompatActivity {
    ViewPager viewPager;
    SwipeAdapter swipeAdapter;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    /**
     * List of menu screens for games
     */
    Class[] gameMenus = {StartingActivity.class, SnakeMenu.class, Main2048Activity.class};
    final static String[] GAME_LIST = {"Sliding Tiles", "Snake", "2048"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        swipeAdapter = new SwipeAdapter(this);
        viewPager.setAdapter(swipeAdapter);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        TextView textView = findViewById(R.id.textViewWelcome);
        textView.setText("Welcome, " + currentUser.getEmail());
        addLaunchAppButton();
        addScoreButton();
        addBackButton();

    }

    /**
     * Activate the LaunchApp Button
     */
    private void addLaunchAppButton() {
        Button launchApp = findViewById(R.id.activatePlayButton);
        launchApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(v.getContext(), gameMenus[viewPager.getCurrentItem()]);
                startActivity(tmp);
            }
        });
    }

    /**
     * Activate the Score button
     */
    private void addScoreButton() {
        Button scoreButton = findViewById(R.id.ScoreButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(v.getContext(), ScoreActivity.class);
                tmp.putExtra("game", viewPager.getCurrentItem());
                startActivity(tmp);
            }
        });
    }

    /**
     * Activate the Back button
     */
    private void addBackButton() {
        Button scoreButton = findViewById(R.id.backButton);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tmp = new Intent(v.getContext(), LoginActivity.class);
                startActivity(tmp);
            }
        });
    }
}
