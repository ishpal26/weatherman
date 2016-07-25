package ishpallutfan.weatherman;

/**
 * Created by Ishpal on 3/6/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreen extends Activity {

    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //This portion makes the fonts nice
        /*t =  (TextView) findViewById(R.id.header);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Thin.ttf");
        t.setTypeface(myCustomFont);*/

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
