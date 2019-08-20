package uk.co.deanwild.materialshowcaseviewsample;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    Switch wantTutorial;
    SharedPreferences.Editor editor;
    boolean wantToSeeTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn_simple_example);
        button.setOnClickListener(this);
        button = findViewById(R.id.btn_custom_example);
        button.setOnClickListener(this);
        button = findViewById(R.id.btn_sequence_example);
        button.setOnClickListener(this);
        button = findViewById(R.id.btn_tooltip_example);
        button.setOnClickListener(this);
        button = findViewById(R.id.btn_reset_all);
        button.setOnClickListener(this);
        wantTutorial=findViewById(R.id.wantTutorial);

        sharedPreferences=getSharedPreferences("ARC",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        wantToSeeTutorial=sharedPreferences.getBoolean("wantTutorial",false);
        if(wantToSeeTutorial)
            wantTutorial.setChecked(true);

        wantTutorial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("wantTutorial", true);
                else
                    editor.putBoolean("wantTutorial", false);
                editor.commit();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        wantToSeeTutorial=sharedPreferences.getBoolean("wantTutorial",false);
        Toast.makeText(this, String.valueOf(wantToSeeTutorial), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.btn_simple_example:
                intent = new Intent(this, SimpleSingleExample.class);
                break;

            case R.id.btn_custom_example:
                intent = new Intent(this, CustomExample.class);
                break;

            case R.id.btn_sequence_example:
                intent = new Intent(this, SequenceExample.class);
                break;

            case R.id.btn_tooltip_example:
                intent = new Intent(this, TooltipExample.class);
                break;

            case R.id.btn_reset_all:
                MaterialShowcaseView.resetAll(this);
                Toast.makeText(this, "All Showcases reset", Toast.LENGTH_SHORT).show();
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }


}
