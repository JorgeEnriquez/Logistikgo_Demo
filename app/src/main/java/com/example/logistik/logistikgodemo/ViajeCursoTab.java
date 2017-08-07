package com.example.logistik.logistikgodemo;

/**
 * Created by Jorge Enríquez and uriel ;) on 06/08/2017.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViajeCursoTab extends Fragment {
    Button button;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_viaje_curso, container, false);
        button = (Button)rootView.findViewById(R.id.btn_viaje_curso);
        textView = (TextView)rootView.findViewById(R.id.text_viaje_curso);
        button.setText("Llegada Origen");
        textView.setText("Llegada Origen");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getText() == "Llegada Origen")
                {
                    button.setText("Descarga");
                    textView.setText("Descarga");
                }
                else {
                    button.setText("Llegada Origen");
                    textView.setText("Llegada Origen");
                }
            }
        });
        return rootView;
    }
}
