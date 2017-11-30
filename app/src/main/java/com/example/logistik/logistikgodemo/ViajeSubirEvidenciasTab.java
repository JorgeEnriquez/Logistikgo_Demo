package com.example.logistik.logistikgodemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.logistik.logistikgodemo.Config.ConexionAPIs;
import com.example.logistik.logistikgodemo.Http.HttpClient;

import java.io.ByteArrayOutputStream;

public class ViajeSubirEvidenciasTab extends Fragment {
    String IDViaje;
    String Titulo;
    String TipoArchivo = "EVIDENCIAS";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri filePath;
    private ImageView mImageView;
    private String urlCamera = "http://10.0.2.2:63518/api/Viaje/SaveEvidenciaDigital";
    private String urlDescription = "http://10.0.2.2:63518/api/Viaje/SaveComentarioEv_Digital";
    String RutaAPI;

    ImageView imageViewCartaPorte, imageViewRemision, imageViewEvidencia;
    ImageButton buttonCartaPorte, buttonRemision, buttonEvidencia;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viaje_subirevidencias, container, false);

        RutaAPI = ConexionAPIs.RutaApi;

        Bundle bundle = getActivity().getIntent().getExtras();
        IDViaje = bundle.getString("IDViajeProceso");

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

        buttonCartaPorte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Titulo = "CARTA PORTE";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
        buttonRemision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Titulo = "REMISION";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });
        buttonEvidencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Titulo = "EVIDENCIA";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView nImage = (ImageView) getActivity().findViewById(R.id.imageView);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {

            filePath = data.getData();
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            try {
                sendPhoto(imageBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);


        switch (Titulo) {
            case "CARTA PORTE":
                imageViewCartaPorte.setImageBitmap(bitmap);
                return;
            case "REMISION":
                imageViewRemision.setImageBitmap(bitmap);
                return;
            case "EVIDENCIA":
                imageViewEvidencia.setImageBitmap(bitmap);
                return;
        }
    }
    public class UploadTask extends AsyncTask<Bitmap, Void, Void> {

        @Override
        protected Void doInBackground(Bitmap... bitmaps) {
            String url = RutaAPI +  "api/Viaje/SaveEvidenciaDigital";
            String BOUNDARY = "Binario";

            if (bitmaps[0] == null)
                return null;
            //  Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();            //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            //  InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream
            Bitmap  bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.bienvenido_operador);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);
            //byte[] b = bitmapToByteArray(bitmap);




            HttpClient client = new HttpClient(url);

            try {
                client.connectForMultipart();

                client.addFormPart("Titulo", Titulo);
                client.addFormPart("TipoArchivo", TipoArchivo);
                client.addFormPart("IDViaje", IDViaje);
                client.addFilePart("file", ".png", baos.toByteArray());
                String response = null;

                client.finishMultipart();

                response = client.getResponse();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //  Toast.makeText(MainActivity.this, R.string.uploaded, Toast.LENGTH_LONG).show();
        }

    }
}
