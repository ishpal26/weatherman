package ishpallutfan.weatherman;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Lutfan on 24/1/2018.
 */

public class CustomGifView extends View {

    private InputStream gifInputStream;
    private Movie gifMovie;
    private int movieWidth, movieHeight;
    private long movieDuration;
    private long mMovieStart;

    public CustomGifView(Context context) {
        super(context);
        init(context);
    }

    public CustomGifView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomGifView(Context context, AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setFocusable(true);
        gifInputStream = context.getResources()
                .openRawResource(R.raw.giphy);

        gifMovie = Movie.decodeStream(gifInputStream);

        movieWidth = gifMovie.width();
        movieHeight = gifMovie.height();
        movieDuration = gifMovie.duration();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,
                             int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Set the dimensions of this view.
        this.setMeasuredDimension(getScreenWidth(), getScreenHeight());

        //setMeasuredDimension(getScreenWidth(), getScreenHeight()); //setMeasuredDimension(movieWidth, movieHeight);
    }

    public int getMovieWidth(){
        return movieWidth;
    }

    public int getMovieHeight(){
        return movieHeight;
    }

    public long getMovieDuration(){
        return movieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {   // first time
            mMovieStart = now;
        }

        if (gifMovie != null) {

            int dur = gifMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }

            int relTime = (int)((now - mMovieStart) % dur);

            gifMovie.setTime(relTime);

            gifMovie.draw(canvas, 0, 0);
            invalidate();

        }

    }

    public int getScreenWidth() {
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        return width;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}