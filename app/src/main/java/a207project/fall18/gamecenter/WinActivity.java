package a207project.fall18.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        addreturnButtonListener();

        TextView resultLabel = findViewById(R.id.resultLabel);

        int score = getIntent().getIntExtra("Score", 0);

        resultLabel.setText(score + "");

    }

    /**
     * Activate the back button to the main menu for the game
     */
    private void addreturnButtonListener(){
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity();
            }
        });
    }

    /**
     * Going back to the main menu for the game
     */
    private void switchActivity(){
        Intent tmp = new Intent(WinActivity.this, GameCenterActivity.class);
        startActivity(tmp);
    }

}
