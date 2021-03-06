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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.logistik.logistikgodemo.Config.ConexionAPIs;
import com.example.logistik.logistikgodemo.Http.HttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ViajeSubirEvidenciasTab extends Fragment {
    String IDViaje, Titulo;
    String TipoArchivo = "EVIDENCIAS";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri filePath;
    private ImageView mImageView;
    private String urlCamera = "http://10.0.2.2:63513/api/Viaje/SaveEvidenciaDigital";
    private String urlDescription = "http://10.0.2.2:63513/api/Viaje/SaveComentarioEv_Digital";
    public View view;
    //Linea 42
    String RutaAPI, strCartaPorte, strRemision, strEvidencia, strFormat;
    ImageView imageViewCartaPorte, imageViewRemision, imageViewEvidencia;
    ImageButton imageButtonCartaPorte, imageButtonRemision, imageButtonEvidencia, buttonCartaPorte, buttonRemision, buttonEvidencia, buttonGlobal;
    EditText edittextCartaPorte, editTextRemision, editTextEvidencia;
    Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_viaje_subirevidencias, container, false);
        RutaAPI = ConexionAPIs.RutaApi;

        Bundle bundle = getActivity().getIntent().getExtras();
        IDViaje = bundle.getString("IDViajeProceso");

        imageViewCartaPorte = (ImageView) view.findViewById(R.id.imageViewCartaPorte);
        imageViewRemision = (ImageView) view.findViewById(R.id.imageViewRemision);
        imageViewEvidencia = (ImageView) view.findViewById(R.id.imageViewEvidencia);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_images);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);

        imageViewCartaPorte.setImageDrawable(roundedBitmapDrawable);
        imageViewRemision.setImageDrawable(roundedBitmapDrawable);
        imageViewEvidencia.setImageDrawable(roundedBitmapDrawable);

        imageButtonCartaPorte = (ImageButton) view.findViewById(R.id.buttonCartaPorte);
        imageButtonRemision = (ImageButton) view.findViewById(R.id.buttonRemision);
        imageButtonEvidencia = (ImageButton) view.findViewById(R.id.buttonEvidencia);

        edittextCartaPorte = (EditText) view.findViewById(R.id.editTextCartaPorte);
        editTextRemision = (EditText) view.findViewById(R.id.editTextRemision);
        editTextEvidencia = (EditText) view.findViewById(R.id.editTextEvidencia);

        buttonCartaPorte = (ImageButton) view.findViewById(R.id.buttonSendCartaPorte);
        buttonRemision = (ImageButton) view.findViewById(R.id.buttonSendRemision);
        buttonEvidencia = (ImageButton) view.findViewById(R.id.buttonSendEvidencia);

        disableEditText(edittextCartaPorte, buttonCartaPorte);
        disableEditText(editTextRemision, buttonRemision);
        disableEditText(editTextEvidencia, buttonEvidencia);

        imageButtonCartaPorte.setOnClickListener(onClickListener);
        imageButtonRemision.setOnClickListener(onClickListener);
        imageButtonEvidencia.setOnClickListener(onClickListener);

        buttonCartaPorte.setOnClickListener(onClickListener);
        buttonRemision.setOnClickListener(onClickListener);
        buttonEvidencia.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        Intent takePictureIntent;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonSendCartaPorte:
                    Titulo = "CARTA PORTE";
                    strCartaPorte = edittextCartaPorte.getText().toString();
                    saveDescription(strCartaPorte, buttonCartaPorte);
                    return;
                case R.id.buttonSendRemision:
                    Titulo = "REMISION";
                    strRemision = editTextRemision.getText().toString();
                    saveDescription(strRemision, buttonRemision);
                    return;
                case R.id.buttonSendEvidencia:
                    Titulo = "EVIDENCIA";
                    strEvidencia = editTextEvidencia.getText().toString();
                    saveDescription(strEvidencia, buttonEvidencia);
                    return;
                case R.id.buttonCartaPorte:
                    Titulo = "CARTA PORTE";
                    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                    return;
                case R.id.buttonRemision:
                    Titulo = "REMISION";
                    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                    return;
                case R.id.buttonEvidencia:
                    Titulo = "EVIDENCIA";
                    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }
                    return;
            }
        }
    };

    private void disableEditText(EditText editText, ImageButton imageButton) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setFocusableInTouchMode(false);

        imageButton.setEnabled(false);
        imageButton.setFocusable(false);
        imageButton.setBackgroundResource(R.drawable.button_disabled_send);
    }

    private void enableEditText(EditText editText, ImageButton imageButton) {
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setCursorVisible(true);
        editText.setFocusableInTouchMode(true);

        imageButton.setEnabled(true);
        imageButton.setFocusable(true);
        imageButton.setBackgroundResource(R.drawable.button_send);
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

    private void  saveDescription(String strData, ImageButton imageButton){
        strFormat = "Description";
        new UploadDescription().execute(strData, imageButton);
        //Toast.makeText(getActivity(), dato, Toast.LENGTH_SHORT).show();
    }

    private void sendPhoto(Bitmap bitmap) throws Exception {
        new UploadTask().execute(bitmap);

        strFormat = "Image";

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmapDrawable.setCircular(true);

        switch (Titulo) {
            case "CARTA PORTE":
                imageViewCartaPorte.setImageDrawable(roundedBitmapDrawable);
                enableEditText(edittextCartaPorte, buttonCartaPorte);
                return;
            case "REMISION":
                imageViewRemision.setImageDrawable(roundedBitmapDrawable);
                enableEditText(editTextRemision, buttonRemision);
                return;
            case "EVIDENCIA":
                imageViewEvidencia.setImageDrawable(roundedBitmapDrawable);
                enableEditText(editTextEvidencia, buttonEvidencia);
                return;
        }
    }
    public class UploadTask extends AsyncTask<Bitmap, Void, JSONObject> {
        JSONObject jRes = new JSONObject();

        @Override
        protected JSONObject doInBackground(Bitmap... bitmaps) {
            String url = RutaAPI +  "api/Viaje/SaveEvidenciaDigital";
            String BOUNDARY = "Binario";

            if (bitmaps[0] == null)
                return null;
            Bitmap bitmap = bitmaps[0];
            ByteArrayOutputStream stream = new ByteArrayOutputStream();            //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
            //  InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream
            //Bitmap  bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.bienvenido_operador);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //byte[] b = bitmapToByteArray(bitmap);

            HttpClient client = new HttpClient(url);

            try {
                client.connectForMultipart(strFormat);

                client.addFormPart("Titulo", Titulo);
                client.addFormPart("TipoArchivo", TipoArchivo);
                client.addFormPart("IDViaje", IDViaje);
                client.addFilePart("file", ".png", baos.toByteArray());
                JSONObject response = null;
                client.finishMultipart();
                response = client.getResponse();
                JSONObject jData = response.getJSONObject("jMeta");

                URL _url = new URL(jData.getString("RutaAchivo"));
                URLConnection con = _url.openConnection();
                con.connect();
                InputStream is = con.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                Bitmap ImageServer = BitmapFactory.decodeStream(bis);
                bis.close();

                jRes.put("ImageServer", ImageServer);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return jRes;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //  Toast.makeText(MainActivity.this, R.string.uploaded, Toast.LENGTH_LONG).show();
        }

    }

    public class UploadDescription extends AsyncTask<Object, Object, JSONObject> {
        JSONObject jsonObjectRetunr = new JSONObject();
        @Override
        protected JSONObject doInBackground(Object... strings){
            String url = urlDescription; //RutaAPI + "api/Viaje/SaveComentarioEv_Digital";
            HttpClient client = new HttpClient(url);
            JSONObject jsonObject = new JSONObject();

            try {
                client.connectForMultipart(strFormat);

                jsonObject.put("strTitulo", Titulo);
                jsonObject.put("TipoArchivo", TipoArchivo);
                jsonObject.put("strIDViaje", IDViaje);
                jsonObject.put("strObservacion", strings[0]);
                client.addParamJson(jsonObject);
                JSONObject response = null;

                client.finishMultipart();

                response = client.getResponse();
                jsonObjectRetunr.put("Button", strings[1]);
//condicional
            }catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectRetunr;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            // TODO Auto-generated method stub
            super.onPostExecute(jsonObject);
            try {
                buttonGlobal = (ImageButton) jsonObject.get("Button");
                buttonGlobal.setBackgroundResource(R.drawable.button_success_send);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
