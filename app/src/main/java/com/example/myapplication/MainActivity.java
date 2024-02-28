package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        private static final String JSON_URL = "http://m1.maxfad.ru/api/users.json";

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);

        loadJSONFromURL(JSON_URL);
    }


    private void loadJSONFromURL(String URL)
    {
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(ListView.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(listView.INVISIBLE);
                        try{
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("users");
                            ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
                            ListAdapter listAdapter = new ListViewAdapter(getApplicationContext(), R.layout.row, R.id.textViewName, listItems);
                            listView.setAdapter(listAdapter);
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
        new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray)
    {
        ArrayList<JSONObject> arrayList = new ArrayList<JSONObject>();
        try{
            if (jsonArray != null)
            {
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    arrayList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return arrayList;
    }
}