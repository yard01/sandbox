package com.github.yard01.sandbox.crib.service_crib.wallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.example.service_crib.R;

import androidx.fragment.app.FragmentManager;

public class WallpaperTabContentCreator {

    public static void createContent(FragmentManager fragmentManager, final View view) {
        Button startWallPapeer = view.findViewById(R.id.service_crib_wallpaper_btn_start);
        startWallPapeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(view.getContext(), ServiceCribWallpaper.class));
                view.getContext().startActivity(intent);

            }
        });
    }
}
