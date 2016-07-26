package ishpallutfan.weatherman;

/**
 * Created by Ishpal on 3/6/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;

public class SplashScreen extends Activity {

    String tentative = "test";
    Thread timerThread;
    TextView t2, yT, nT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        t2 = (TextView) findViewById(R.id.textView2);
        yT = (TextView) findViewById(R.id.yesText);
        nT = (TextView) findViewById(R.id.noText);

        /*Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "Fonts/Roboto-ThinItalic.ttf");
        t2.setTypeface(myCustomFont);
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "Fonts/Roboto-ThinItalic.ttf");
        yT.setTypeface(myCustomFont1);
        Typeface myCustomFont2 = Typeface.createFromAsset(getAssets(), "Fonts/Roboto-ThinItalic.ttf");
        nT.setTypeface(myCustomFont2);*/

        //This portion makes the fonts nice
        /*t =  (TextView) findViewById(R.id.header);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "Fonts/Roboto-Thin.ttf");
        t.setTypeface(myCustomFont);*/

        timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);

                }catch(InterruptedException e){
                    e.printStackTrace();

                } finally {

                    runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(this,"hi", Toast.LENGTH_SHORT).show();
                        t2.setTextColor(ColorStateList.valueOf(Color.parseColor("#E0F7FA")));
                        yT.setTextColor(ColorStateList.valueOf(Color.parseColor("#E0F7FA")));
                        nT.setTextColor(ColorStateList.valueOf(Color.parseColor("#E0F7FA")));
                        //Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        //startActivity(intent);

                        TextView texVar= (TextView) findViewById(R.id.yesText);
                        TextView texVar2= (TextView) findViewById(R.id.noText);

                        texVar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // to pass the age to the next activity
                                boolean isRaining = true;
                                Intent intentBundle1 = new Intent(v.getContext(), MapsActivity.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putBoolean("isRaining", isRaining);
                                intentBundle1.putExtras(bundle1);
                                startActivity(intentBundle1);

                            }
                        });

                        texVar2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean isRaining = false;

                                // to pass the age to the next activity
                                Intent intentBundle2 = new Intent(v.getContext(), MapsActivity.class);
                                Bundle bundle2 = new Bundle();
                                bundle2.putBoolean("isRaining", isRaining);
                                intentBundle2.putExtras(bundle2);
                                startActivity(intentBundle2);
                            }
                        });
                      }
                    });

                }
            }
        };
        timerThread.start();
        //timerThread.interrupt();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
