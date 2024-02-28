package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.view.LayoutInflaterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<JSONObject> {
    int listLayout;
    ArrayList<JSONObject> usersList;
    Context context;

    public ListViewAdapter(Context context, int listLayout, int field, ArrayList<JSONObject> usersList)
    {
        super(context, listLayout, usersList);
        this.context = context;
        this.listLayout = listLayout;
        this.usersList = usersList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View listViewItem = inflater.inflate(listLayout,null,false);
        TextView name = listViewItem.findViewById(R.id.textViewName);
        TextView email = listViewItem.findViewById(R.id.textViewEmail);
        try {
            name.setText(usersList.get(position).getString("name"));
            email.setText(usersList.get(position).getString("email"));
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return listViewItem;
    }
}
