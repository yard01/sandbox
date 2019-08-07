package com.github.yard01.sandbox.crib.ui_crib.controls.page1;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.ui_crib.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class ControlsPage1Creator {

    public static void createContent(final View view) {
        ChipGroup chipGroup = view.findViewById( R.id.chipGroup);
        //final Context context = view.getContext();
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                Chip chip = chipGroup.findViewById(i);
                if (chip != null)
                    Toast.makeText(view.getContext(), "Chip is " + chip.getText(), Toast.LENGTH_SHORT).show();


            }
        });

        Chip chip = view.findViewById(R.id.chip);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Close is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
