package a207project.fall18.gamecenter;
//https://www.mytrendin.com/android-viewpager/

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SwipeAdapter extends PagerAdapter {
    /**
     * List of game logos
     */
    private int[] images ={R.drawable.slidingtiles_logo, R.drawable.snake_logo, R.drawable.logo_2048};
    private Context context;
    private LayoutInflater layoutInflater;

    /**
     * A new SwipeAdapter
     * @param context
     */
    SwipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.swipe,container,false);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_view);
        TextView textView = (TextView)view.findViewById(R.id.text_game);
        imageView.setImageResource(images[position]);
        textView.setText(GameCenterActivity.GAME_LIST[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}