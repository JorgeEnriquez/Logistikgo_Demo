package com.example.logistik.logistikgodemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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

        //extraemos el drawable en un bitmap
        Drawable originalDrawable = getResources().getDrawable(R.drawable.no_images);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);

        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        imageViewCartaPorte = (ImageView) view.findViewById(R.id.imageViewCartaPorte);
        imageViewRemision = (ImageView) view.findViewById(R.id.imageViewRemision);
        imageViewEvidencia = (ImageView) view.findViewById(R.id.imageViewEvidencia);

        imageViewCartaPorte.setImageDrawable(roundedDrawable);
        imageViewRemision.setImageDrawable(roundedDrawable);
        imageViewEvidencia.setImageDrawable(roundedDrawable);

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
