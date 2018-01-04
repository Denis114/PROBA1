package com.example.denis.laba8_and;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Viborka extends AsyncTask<String, Void, String> {

    public String Task;
    public static Avto avto;


    public Viborka() {
        avto = new Avto();
    }

    public String getTask(){
        return Task;
    }
    public void setTask(String task){
        this.Task = task;
    }



    @Override
    protected String doInBackground(String... params) {

        InputStream inputStream = null;
        String result= null;
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(params[0]);

        try {

            HttpResponse response = client.execute(httpGet);
            inputStream = response.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
                Log.i("App", "Data received:" +result);
            }
            else
                result = "Failed to fetch data";

            return result;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String dataFetched) {
        //parse the JSON data and then display
        parseJSON(dataFetched);
        if(Task=="GetAuto") {
            Otchet.et1.setText(String.valueOf(avto.getNomer()));
            Otchet.et2.setText(String.valueOf(avto.getMarka()));
            Otchet.et3.setText(String.valueOf(avto.getModel()));
            Otchet.et4.setText(String.valueOf(avto.getVladelec()));
            Otchet.et5.setText(String.valueOf(avto.getVIN_nomer()));
            Otchet.et6.setText(String.valueOf(avto.getGod_vipyska()));
            Otchet.et7.setText(String.valueOf(avto.getMoshnost()));
            Otchet.et8.setText(String.valueOf(avto.getBitay()));
            Log.i("App", "данные установлены");

        }

    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private void parseJSON(String data){

        try{
            JSONArray jsonArray = new JSONArray(data);

            int jsonArrLength = jsonArray.length();

            if(Task=="GetAuto"){
                for(int i=0; i < jsonArrLength; i++) {
                    JSONObject jsonChildNode = jsonArray.getJSONObject(0);

                    String ID = jsonChildNode.getString("ID");
                    String nomer = jsonChildNode.getString("nomer");
                    String marka = jsonChildNode.getString("marka");
                    String model = jsonChildNode.getString("model");
                    String vladelec = jsonChildNode.getString("vladelec");
                    String VIN_nomer = jsonChildNode.getString("VIN_nomer");
                    String god_vipyska = jsonChildNode.getString("god_vipyska");
                    String moshnost = jsonChildNode.getString("moshnost");
                    String bitay = jsonChildNode.getString("bitay");

                    Log.i("App", "данне приняты");

                    avto.setID(Integer.valueOf(ID));
                    avto.setNomer(nomer);
                    avto.setMarka(marka);
                    avto.setModel(model);
                    avto.setVladelec(vladelec);
                    avto.setVIN_nomer(VIN_nomer);
                    avto.setGod_vipyska(Integer.valueOf(god_vipyska));
                    avto.setMoshnost(moshnost);
                    avto.setBitay(bitay);


                    Log.i("App", "avto id=" + avto.getID());
                }

            }

        }catch(Exception e){
            Log.i("App", "Error parsing data " +e.getMessage());
        }
    }
}