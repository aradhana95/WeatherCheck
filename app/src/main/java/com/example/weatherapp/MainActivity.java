package com.example.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity<API> extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;

    //http://api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=5fbdde63e278c55d85c0086a92e647ae
    String baseURL = "http://api.openweathermap.org/data/2.5/weather?q=";


    String API = "&APPID=5fbdde63e278c55d85c0086a92e647ae";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        city = (EditText) findViewById(R.id.getCity);
        result = (TextView) findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String myURL = baseURL + city.getText().toString() + API;

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {

                            @Override

                            public void onResponse(JSONObject jsonObject) {

                                Log.i("jSON:", "JSON:" + jsonObject);

                                try {
                                    String info = jsonObject.getString("weather");

                                    Log.i("INFO", "INFO" + info);

                                    JSONArray ar = new JSONArray(info);

                                    for (int i = 0; i < ar.length(); i++) {

                                        JSONObject parObj = ar.getJSONObject(i);

                                        String myWeather = parObj.getString("main");
                                        result.setText(myWeather);
                                        Log.i("ID", "ID" + parObj.getString("id"));
                                        Log.i("MAIN", "MAIN" + parObj.getString("main"));


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }





                   /* try {
                        String  coor = jsonObject.getString("coord");
                        Log.i("Coor","Coor" + coor);
                        JSONObject co = new JSONObject(coor);

                        String lon =co.getString("lon");
                        String lat =co.getString("lat");

                        Log.i("LON" ,"LON:"+lon);
                        Log.i("LAT" ,"LAT:"+lon);



                    }catch (JSONException e){
                        e.printStackTrace();
                    }*/

                            }

                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Log.i("Error", "somthing went wrong" + volleyError);
                            }
                        }


                );

                MySingleton.getInstance(MainActivity.this).addToRequestQue(jsonObjectRequest);

            }
        });

    }
}



