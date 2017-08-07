package com.example.logistik.logistikgodemo;

/**
 * Created by Jorge Enríquez y por uriel ;) on 06/08/2017.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InformacionTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_informacion, container, false);
        return rootView;
    }
}
