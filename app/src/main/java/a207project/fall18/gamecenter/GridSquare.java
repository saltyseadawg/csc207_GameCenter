package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

import android.graphics.Color;


public class GridSquare {

    /**
     * An int representing the type of GridSquare.
     */
    private int mType;

    GridSquare(int type) {
        mType = type;
    }

    /**
     * Returns the colour of the GridSquare
     *
     * @return the colour of the GridSquare
     */
    int getColor() {
        switch (mType) {
            case GameType.GRID:
                return Color.WHITE;
            case GameType.FOOD:
                return Color.BLUE;
            case GameType.SNAKE:
                return Color.parseColor("#FF4081");
        }
        return Color.WHITE;
    }

    /**
     * Set the type of this GridSquare to an integer type that corresponds to a type.
     */
    void setType(int type) {
        mType = type;
    }
}
