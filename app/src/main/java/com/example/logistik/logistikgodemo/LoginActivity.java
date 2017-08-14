package com.example.logistik.logistikgodemo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    EditText editUsuario;
    EditText editContrasena;
    String strUsuario;
    String strContrasena;
//    public void onMenuClick(View view){
//        Context currentContext = this;
//        Intent activity_login = new Intent(currentContext, MenuActivity.class);
//        startActivity(activity_login);
//        finish();
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = (EditText) findViewById(R.id.editUsuario);
        editContrasena = (EditText) findViewById(R.id.editContrasena);

    }

    public void onMenuClick(View view) throws ExecutionException, InterruptedException, JSONException {

        //API PRODUCCION
        String strURL = "https://api-debug.logistikgo.com/api/Usuarios/ValidarUsuario";
        //API DEBUG VISUAL STUDIO
//        String strURL = "http://10.0.2.2:63510/api/Usuarios/ValidarUsuario";
        strUsuario =  editUsuario.getText().toString();
        strContrasena = editContrasena.getText().toString();

        JSONObject jdata=new JSONObject();
        JSONObject jParams=new JSONObject();

        try {
            jdata.put("strURL",strURL);

            jParams.put("strUsuario",strUsuario);
            jParams.put("strContrasena",strContrasena);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //VERIFICA SI HAY CONEXIÓN DE INTERNET
//        if(isConnected()){
//            tvIsConnected.setBackgroundColor(0xFF00CC00);
//            tvIsConnected.setText("You are connected");

        //REALIZA LA PETICION
        JSONObject jResult = GetResponse(jdata,jParams);
        String NombreUsuario = jResult.getString("NombreUsuario");

        if (NombreUsuario != ""){


            Toast.makeText(this, "Bienvenido " + NombreUsuario , Toast.LENGTH_SHORT).show();

            Context currentContext = this;
            Intent activity_login = new Intent(currentContext, MenuActivity.class);
            activity_login.putExtra("NameUsuario",NombreUsuario);
            activity_login.putExtra("IDViajeProceso",jResult.getString("IDViajeProceso"));
            activity_login.putExtra("StatusProceso",jResult.getString("StatusProceso"));

//            Intent InformacionTab = new Intent(currentContext, ViajeCursoActivity.class);
//            InformacionTab.putExtra("IDViajeProceso",jstrResult.getString("IDViajeProceso"));
//            InformacionTab.putExtra("StatusProceso",jstrResult.getString("StatusProceso"));
//            startActivity(InformacionTab);

            startActivity(activity_login);
            finish();

        }
        else{
            Toast.makeText(this, "Usuario incorrecto" , Toast.LENGTH_SHORT).show();
        }



//        }
    }
    public JSONObject GetResponse(JSONObject jdata,JSONObject jParams) throws ExecutionException, InterruptedException, JSONException {
        JSONObject resJson = null;

        //Instantiate new instance of our class
        HttpGetRequest getRequest = new HttpGetRequest();

        resJson = getRequest.execute(jdata,jParams).get();

        return resJson;
    }

    public static JSONObject GetHttpResponse(String strURL, JSONObject jData, String strRequest_method, int read_timeout, int connection_timeout)  {
        String strRes = null;
        String inputLine;
        JSONObject jRes = null;
        JSONObject _jMeta = null;
        JSONObject _jData = null;
        JSONObject _jError = null;

        try {
            URL urlCurrent = new URL(strURL);
            HttpURLConnection connection =(HttpURLConnection)urlCurrent.openConnection();

            connection.setRequestMethod(strRequest_method);
            connection.setReadTimeout(read_timeout);
            connection.setConnectTimeout(connection_timeout);

            //POST
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //ENCABEZADOS DE LA PETICIÓN
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //Connect to our url
            connection.connect();

            OutputStream os = connection.getOutputStream();
            os.write(jData.toString().getBytes("UTF-8"));
            os.close();

            int HttpResult = connection.getResponseCode();

            //VERIFICAR SI LA CONEXION SE REALIZO DE FORMA CORRECTA = 200
            if(HttpResult == HttpURLConnection.HTTP_OK){

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());

                StringBuilder stringBuilder = new StringBuilder();
                String strResponseMessage = connection.getResponseMessage();
                JsonReader jsonReader = new JsonReader(streamReader);

                //LEER JSON MANUAL
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder _stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                String _strRes = stringBuilder.toString();
                JSONObject obj = new JSONObject(_strRes);
                JSONObject paramMeta = obj.getJSONObject("jMeta");
                String __strResponse = paramMeta.getString("ResponseCode");

                String strResponse = paramMeta.getString("Response");

                if(strResponse.equals("OK")){
                    jRes = obj.getJSONObject("jData");
                }
                else{
                    jRes = obj.getJSONObject("jDataError");
                }
            }
            else{
                String strResponse = connection.getResponseMessage();
                InputStreamReader streamError = new InputStreamReader(connection.getErrorStream());
                JsonReader jsonReader = new JsonReader(streamError);

                //LEER JSON
                jsonReader.beginObject(); // Start processing the JSON object
                while (jsonReader.hasNext()) { // Loop through all keys
                    String key = jsonReader.nextName(); // Fetch the next key
                    if (key.equals("Message")) { // VERIFICA EL NOMBRE DEL CAMPO
                        strRes = jsonReader.nextString();
                        break; // Break out of the loop
                    } else {
                        jsonReader.skipValue(); // Skip values of other keys
                    }
                }
                jsonReader.close();

                Log.d("ERROR",strResponse);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jRes;
    }

    // VERIFICAR SI EXISTE CONEXIÓN A INTERNET
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    //CLASS ASYNC REQUEST
    public static class HttpGetRequest extends AsyncTask<JSONObject, Void, JSONObject> {

        //VARIABLES DE CONFIGURACION DE LA CONEXION
        public static final String REQUEST_METHOD = "POST";
        public static final int READ_TIMEOUT = 150000;
        public static final int CONNECTION_TIMEOUT = 150000;

        @Override
        protected void onPreExecute() {
        }


        @Override
        protected JSONObject doInBackground(JSONObject... jObject) {
            String stringUrl = null;
            JSONObject resJson = null;

            try {
                stringUrl = jObject[0].getString("strURL");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String result = null;
            String inputLine;

            try {

                resJson = GetHttpResponse(stringUrl,jObject[1],REQUEST_METHOD,READ_TIMEOUT,CONNECTION_TIMEOUT);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resJson;
        }

    }
}
