package com.example.logistik.logistikgodemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ViajeSubirEvidenciasTab extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    ImageView imageViewCartaPorte, imageViewRemision, imageViewEvidencia;
    ImageButton buttonCartaPorte, buttonRemision, buttonEvidencia;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viaje_subirevidencias, container, false);

        imageViewCartaPorte = (ImageView) view.findViewById(R.id.imageViewCartaPorte);
        imageViewRemision = (ImageView) view.findViewById(R.id.imageViewRemision);
        imageViewEvidencia = (ImageView) view.findViewById(R.id.imageViewEvidencia);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_images);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);

        imageViewCartaPorte.setImageDrawable(roundedBitmapDrawable);
        imageViewRemision.setImageDrawable(roundedBitmapDrawable);
        imageViewEvidencia.setImageDrawable(roundedBitmapDrawable);

        buttonCartaPorte = (ImageButton) view.findViewById(R.id.buttonCartaPorte);
        buttonRemision = (ImageButton) view.findViewById(R.id.buttonRemision);
        buttonEvidencia = (ImageButton) view.findViewById(R.id.buttonEvidencia);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
