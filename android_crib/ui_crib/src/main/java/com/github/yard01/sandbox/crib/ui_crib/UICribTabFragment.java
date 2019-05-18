package com.github.yard01.sandbox.crib.ui_crib;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

public class UICribTabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static UICribTabFragment newInstance(String param1, String param2) {
        UICribTabFragment fragment = new UICribTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}
