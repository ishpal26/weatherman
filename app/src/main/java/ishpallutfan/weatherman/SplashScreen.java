package ishpallutfan.weatherman;

/**
 * Created by Ishpal on 3/6/2016.
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;

public class SplashScreen extends Activity {

    String tentative = "test";
    Thread timerThread;
    TextView t2, yT, nT, tv;
    ImageView logo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        t2 = (TextView) findViewById(R.id.textView2);
        yT = (TextView) findViewById(R.id.yesText);
        nT = (TextView) findViewById(R.id.noText);
        tv = (TextView) findViewById(R.id.textView);
        logo = (ImageView) findViewById(R.id.imageView2);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-ThinItalic.ttf");
        t2.setTypeface(myCustomFont);
        Typeface myCustomFont1 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-ThinItalic.ttf");
        yT.setTypeface(myCustomFont1);
        Typeface myCustomFont3 = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        nT.setTypeface(myCustomFont3);

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


                        ObjectAnimator translateLogo = ObjectAnimator.ofFloat(logo, "translationY", -80);
                        /*ObjectAnimator translatet2 = ObjectAnimator.ofFloat(t2, "translationY", -100);
                        ObjectAnimator translateyT = ObjectAnimator.ofFloat(yT, "translationY", -100);
                        ObjectAnimator translatenT = ObjectAnimator.ofFloat(nT, "translationY", -100);*/

                        Animation fadeIn = new AlphaAnimation(0, 1);
                        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
                        fadeIn.setDuration(1000);
                        //fadeIn.setStartOffset(1000);

                        t2.setVisibility(View.VISIBLE);
                        yT.setVisibility(View.VISIBLE);
                        nT.setVisibility(View.VISIBLE);
                        tv.setVisibility(View.VISIBLE);

                        /*translatet2.start();
                        translateyT.start();
                        translatenT.start();*/
                        translateLogo.start();
                        t2.startAnimation(fadeIn);
                        yT.startAnimation(fadeIn);
                        nT.startAnimation(fadeIn);
                        tv.startAnimation(fadeIn);

                        /*t2.setTextColor(ColorStateList.valueOf(Color.parseColor("#E0F7FA")));
                        yT.setTextColor(ColorStateList.valueOf(Color.parseColor("#E0F7FA")));
                        nT.setTextColor(ColorStateList.valueOf(Color.parseColor("#E0F7FA")));*/

                        //Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        //startActivity(intent);

                        TextView texVar= (TextView) findViewById(R.id.yesText);
                        TextView texVar2= (TextView) findViewById(R.id.noText);

                        texVar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // to pass the age to the next activity
                                yT.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFCC80")));
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

                                nT.setTextColor(ColorStateList.valueOf(Color.parseColor("#FFCC80")));
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


        isLocationEnabled();
        if(!isLocationEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
            builder.setTitle("Unable to connect to Location Services ")
                    .setMessage("Turn on Location Services?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            });

            AlertDialog alert = builder.create();
            alert.show();
        }


    }


    protected boolean isLocationEnabled() {
        String le = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(le);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}
