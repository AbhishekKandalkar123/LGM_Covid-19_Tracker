package com.akan.lgmcovid_19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Model> list;
    private CovAdapter adapter;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.IdRecyclerView);
        search = findViewById(R.id.searching);
        list = new ArrayList<>();

        adapter = new CovAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        getStateData();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterState(editable.toString());
            }
        });
    }

    private void getStateData() {

        String url= "https://data.covid19india.org/state_district_wise.json";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Iterator<String> keys = response.keys();
                    keys.next();
                    while(keys.hasNext())
                    {
                        String stateName = keys.next();
                        JSONObject jsonObject = response.getJSONObject(stateName);
                        JSONObject districtdata = jsonObject.getJSONObject("districtData");
                        Iterator<String> districts = districtdata.keys();
                        while(districts.hasNext()) {
                            String districtName = districts.next();
                            JSONObject district = districtdata.getJSONObject(districtName);
                            long active = district.getLong("active");
                            long decreased = district.getLong("deceased");
                            long recovered = district.getLong("recovered");

                            list.add(new Model(stateName,districtName,decreased,recovered,active));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
                catch (JSONException e) {
                    Toast.makeText(MainActivity.this,"Fail to extract json data!!",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        }){

        };
        requestQueue.add(jsonObjectRequest);
    }

    private void filterState(String state) {
        ArrayList<Model> filteredlist= new ArrayList<>();
        for(Model item: list)
        {
            if(item.getSname().toLowerCase().contains(state.toLowerCase())){
                filteredlist.add(item);
            }
        }
        if(filteredlist.isEmpty())
        {
            Toast.makeText(this,"No such state found", Toast.LENGTH_SHORT).show();

        }
        else
        {
            adapter.filterList(filteredlist);
        }
    }


}
