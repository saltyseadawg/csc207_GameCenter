package a207project.fall18.gamecenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class SnakeMenu extends AppCompatActivity {
    SeekBar seekBarSpeed;
    TextView textViewSpeed;
    String textSpeed;

    final int SPEED_INCREMENT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake_menu);

        seekBarSpeed = findViewById(R.id.seekBarDifficulty);
        textViewSpeed = findViewById(R.id.textViewSpeed);

        addStartButtonListener();
        addBackButtonListener();

        seekBarSpeed.setMax(5);
        seekBarSpeed.setMin(1);
        textSpeed = String.valueOf(seekBarSpeed.getProgress());
        textViewSpeed.setText("Speed: " + textSpeed);

        // speed change slider
        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSpeed = String.valueOf(i);
                textViewSpeed.setText("Speed: " + textSpeed);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar){}
        });
    }

    /**
     * Add the start button
     */
    private void addStartButtonListener(){
        Button btnStart = findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent tmp = new Intent(v.getContext(), SnakeActivity.class);
                tmp.putExtra("speed", Integer.parseInt(textSpeed) * SPEED_INCREMENT);
                startActivity(tmp);
            }

        });
    }

    /**
     * Activate the back button to the game center
     */
    private void addBackButtonListener(){
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent tmp = new Intent(v.getContext(), GameCenterActivity.class);
                startActivity(tmp);
            }

        });
    }
}

