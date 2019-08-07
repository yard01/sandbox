package com.github.yard01.sandbox.crib.integration_crib.content_provider;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.integration_crib.R;

import androidx.fragment.app.FragmentManager;

public class ContentProviderTabContentCreator {

    public static void createContent(FragmentManager fragmentManager, final View rootView) {
        //SimpleBookDBContentProvider provider = new SimpleBookDBContentProvider();
        //provider.
        final ContentProviderClient cntxt = new ContentProviderClient(rootView.getContext());
        ListView lvBook = (ListView) rootView.findViewById(R.id.integration_contentprovider_listview);
        lvBook.setAdapter(cntxt.getAdapter());
        Button button = rootView.findViewById(R.id.integration_crib_content_provider_button_error);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cntxt.onClickError(rootView);
            }
        });
    }
}
