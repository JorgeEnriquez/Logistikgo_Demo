package com.example.logistik.logistikgodemo;

/**
 * Created by Jorge Enríquez y por uriel ;) on 06/08/2017.
 */
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class InformacionTab extends Fragment {
    View view;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacion, container, false);
        textView = (TextView)view.findViewById(R.id.txtCantidad);
//        textView.setText("0001");

        Bundle bundle = getActivity().getIntent().getExtras();

        if (bundle != null)
            textView.setText(bundle.getString("DATO"));
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
