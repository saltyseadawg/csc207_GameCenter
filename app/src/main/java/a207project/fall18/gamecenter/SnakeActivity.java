package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class SnakeActivity extends AppCompatActivity implements View.OnClickListener {
    private SnakePanelView snakePanelView;
    private int snakeSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);

        snakeSpeed = getIntent().getIntExtra("speed", 3);
        snakePanelView = findViewById(R.id.snake_view);
        snakePanelView.snakeViewModel = new SnakeViewModel(snakeSpeed);

        findViewById(R.id.start_btn).setOnClickListener(this);
        findViewById(R.id.left_btn).setOnClickListener(this);
        findViewById(R.id.right_btn).setOnClickListener(this);
        findViewById(R.id.top_btn).setOnClickListener(this);
        findViewById(R.id.bottom_btn).setOnClickListener(this);
        findViewById(R.id.done).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
    }

    /**
     * Initiates an action based on the button clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                // turn the button invisible and restarts the game
                v.setVisibility(View.INVISIBLE);
                reStartGame();
            case R.id.left_btn:
                // turn the snake left
                snakePanelView.snakeViewModel.changeSnakeDirection(GameType.LEFT);
                break;
            case R.id.right_btn:
                // turn the snake right
                snakePanelView.snakeViewModel.changeSnakeDirection(GameType.RIGHT);
                break;
            case R.id.top_btn:
                // turn the snake to the top
                snakePanelView.snakeViewModel.changeSnakeDirection(GameType.TOP);
                break;
            case R.id.bottom_btn:
                // turn the snake to the bottom
                snakePanelView.snakeViewModel.changeSnakeDirection(GameType.BOTTOM);
                break;
            case R.id.done:
                //go to the score display page
                switchWin();
                break;
            case R.id.back:
                // generate an alert
                showBackMessageDialog();
                break;
        }
    }

    /**
     * Monitor the running game
     */
    private class GameMainThread extends Thread {

        /**
         * running the game
         */
        @Override
        public void run() {
            while (!snakePanelView.snakeViewModel.getEndGame()) {
                snakePanelView.snakeViewModel.moveSnake();
                snakePanelView.snakeViewModel.checkCollision();
                snakePanelView.snakeViewModel.refreshGridSquare();
                snakePanelView.snakeViewModel.handleSnakeTail();
                snakePanelView.postInvalidate();//redraw the interface
                handleSpeed();
            }
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    showMessageDialog();
                }
            });
        }

        /**
         * handle the speed of the snake
         */
        private void handleSpeed() {
            try {
                sleep(1000 / snakeSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Activate the WinActivity
     */
    protected void switchWin(){
        Intent tmp = new Intent(SnakeActivity.this, WinActivity.class);
        tmp.putExtra("Score", snakePanelView.snakeViewModel.snakeGameModel.calculateScore());
        startActivity(tmp);
    }

    /**
     * Activates the SnakeMenu
     */
    protected void switchBack() {
        Intent tmp = new Intent(SnakeActivity.this, SnakeMenu.class);
        startActivity(tmp);
    }

    /**
     * Show the message dialog
     */
    private void showMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game over!");
        builder.setMessage("Play again?");
        builder.setPositiveButton("Restart", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
                reStartGame();
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Shows a message alert after the back button is clicked
     */
    private void showBackMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Go back?");
        builder.setMessage("Are you sure you want to go back? If you're in the middle of a game " +
                "your score will not be saved.");
        builder.setPositiveButton("Go back", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                snakePanelView.snakeViewModel.setEndGame(true);
                switchBack();
            }
        });
        builder.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Restarts the snake game
     */
    void reStartGame(){
        snakePanelView.snakeViewModel.reStartGame();
        GameMainThread thread = new GameMainThread();
        thread.start();
    }
    /**
     * Lets user play Snake using arrow keys.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            snakePanelView.snakeViewModel.changeSnakeDirection(GameType.BOTTOM);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            snakePanelView.snakeViewModel.changeSnakeDirection(GameType.TOP);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            snakePanelView.snakeViewModel.changeSnakeDirection(GameType.LEFT);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            snakePanelView.snakeViewModel.changeSnakeDirection(GameType.RIGHT);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
