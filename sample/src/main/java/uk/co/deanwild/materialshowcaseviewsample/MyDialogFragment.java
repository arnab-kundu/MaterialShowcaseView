package uk.co.deanwild.materialshowcaseviewsample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

import static android.content.Context.MODE_PRIVATE;

public class MyDialogFragment extends DialogFragment {


    Button button1,button2,button3,button4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_sequence_example, container);
        button1 = view.findViewById(R.id.btn_one);
        button2 = view.findViewById(R.id.btn_two);
        button3 = view.findViewById(R.id.btn_three);
        button4 = view.findViewById(R.id.btn_reset);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(300); // half second between each showcase view
        config.setItemCount(4);
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("ARC", MODE_PRIVATE);
        config.setShowEveryTime(sharedPreferences.getBoolean("wantTutorial", true));
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, "12345");

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                //Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });
        sequence.setConfig(config);
        sequence.addSequenceItem(button1, "Aman", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.addSequenceItem(button2, "Arnab", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.addSequenceItem(button3, "Kumar", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.addSequenceItem(button4, "Reset", "Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s) + Associate one or more equipment with the PIN location to identify equipment location(s)");
        sequence.start();

    }
}
