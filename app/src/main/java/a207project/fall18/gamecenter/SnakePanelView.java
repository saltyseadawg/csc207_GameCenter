package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zhangman on 2018/1/8 14:43.
 * Email: zhangman523@126.com
 */

/**
 * The View of the panel for Snake.
 */
public class SnakePanelView extends View {

    /**
     * An instance of SnakeViewModel.
     */
    a207project.fall18.gamecenter.SnakeViewModel snakeViewModel;

    /**
     * The Paint object to manage the colour and style of the grid.
     */
    private Paint mGridPaint = new Paint();

    /**
     * The Paint object to manage the colour and style of
     */
    private Paint mStrokePaint = new Paint();

    /**
     * The dp value converted from 15 px
     */
    private int mRectSize = dp2px(getContext(), 15);

    /**
     * Grid position variables.
     */
    private int mStartX, mStartY;

    /**
     * A constructor for SnakePanelView
     * @param context an instance of the Context
     */
    public SnakePanelView(Context context) {
        this(context, null);
        setOnTouchListener(new SnakeMovement(this));
    }

    /**
     * An overloaded constructor
     * @param context an instance of the Context
     * @param attrs an Attribute set
     */
    SnakePanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        setOnTouchListener(new SnakeMovement(this));
    }

    /**
     * An overloaded constructor
     * @param context an instance of the Context
     * @param attrs an Attribute set
     * @param defStyleAttr the style Attribute
     */
    SnakePanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(new SnakeMovement(this));
    }

    /**
     * Changes grid position variables when the view size changes.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mStartX = w / 2 - snakeViewModel.getGridSize() * mRectSize / 2;
        mStartY = dp2px(getContext(), 40);
    }

    /**
     * Determines the measured dimensions of the view.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = mStartY * 2 +   (snakeViewModel.getGridSize() + 5) * mRectSize;
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec), height);
    }

    /**
     * Draws the grid.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        mGridPaint.reset();
        mGridPaint.setAntiAlias(true);
        mGridPaint.setStyle(Paint.Style.FILL);
        mGridPaint.setAntiAlias(true);

        mStrokePaint.reset();
        mStrokePaint.setColor(Color.BLACK);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);

        for (int i = 0; i < snakeViewModel.getGridSize(); i++) {
            for (int j = 0; j < snakeViewModel.getGridSize(); j++) {
                int left = mStartX + i * mRectSize;
                int top = mStartY + j * mRectSize;
                int right = left + mRectSize;
                int bottom = top + mRectSize;
                //draws the grid by drawing the edges of a square
                canvas.drawRect(left, top, right, bottom, mStrokePaint);
                mGridPaint.setColor(snakeViewModel.gridSquare.get(i).get(j).getColor());
                //fills in the square of the grid
                canvas.drawRect(left, top, right, bottom, mGridPaint);
            }
        }
    }
    /**
     * Returns dp converted from px
     * @param context the context
     * @param dpVal the value of the dp
     * @return px converted from dp
     */
    static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }
}
