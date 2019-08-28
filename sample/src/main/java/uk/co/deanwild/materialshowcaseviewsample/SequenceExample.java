package uk.co.deanwild.materialshowcaseviewsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.Toast;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;


public class SequenceExample extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonOne;
    private Button mButtonTwo;
    private Button mButtonThree;

    private Button mButtonReset;

    private static final String SHOWCASE_ID = "sequence example";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_example);
        mButtonOne = findViewById(R.id.btn_one);
        mButtonOne.setOnClickListener(this);

        mButtonTwo = findViewById(R.id.btn_two);
        mButtonTwo.setOnClickListener(this);

        mButtonThree = findViewById(R.id.btn_three);
        mButtonThree.setOnClickListener(this);

        mButtonReset = findViewById(R.id.btn_reset);
        mButtonReset.setOnClickListener(this);

    /*   if(hasSoftKeys(this)){
           Log.d("msg","yes");
       }else
           Log.d("msg","no");*/

        presentShowcaseSequence(); // one second delay
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "LANDSCAPE", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //Toast.makeText(this, "PORTRAIT", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_one || v.getId() == R.id.btn_two || v.getId() == R.id.btn_three) {

            presentShowcaseSequence();

        } else if (v.getId() == R.id.btn_reset) {

            MaterialShowcaseView.resetSingleUse(this, SHOWCASE_ID);
            Toast.makeText(this, "Showcase reset", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean hasSoftKeys(Context context) {
        boolean hasSoftwareKeys = true;
        //c = context; use getContext(); in fragments, and in activities you can
        //directly access the windowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display d = this.getWindowManager().getDefaultDisplay();
            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            d.getRealMetrics(realDisplayMetrics);

            int realHeight = realDisplayMetrics.heightPixels;
            int realWidth = realDisplayMetrics.widthPixels;

            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);

            int displayHeight = displayMetrics.heightPixels;
            int displayWidth = displayMetrics.widthPixels;

            hasSoftwareKeys = (realWidth - displayWidth) > 0 ||
                    (realHeight - displayHeight) > 0;
            Log.e("hassoftkey", String.valueOf(realWidth - displayWidth));
        } else {
            boolean hasMenuKey = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            }
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            hasSoftwareKeys = !hasMenuKey && !hasBackKey;
        }
        return hasSoftwareKeys;
    }

    private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(300); // half second between each showcase view
        config.setItemCount(4);
        SharedPreferences sharedPreferences = getSharedPreferences("ARC", MODE_PRIVATE);
        config.setShowEveryTime(sharedPreferences.getBoolean("wantTutorial", false));
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                //Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });
        sequence.setConfig(config);
        sequence.addSequenceItem(mButtonOne, "Aman", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.addSequenceItem(mButtonTwo, "Arnab", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.addSequenceItem(mButtonThree, "Kumar", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.addSequenceItem(mButtonReset, "Reset", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.start();
    }

}
