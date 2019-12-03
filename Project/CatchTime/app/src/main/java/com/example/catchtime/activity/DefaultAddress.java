package com.example.catchtime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.catchtime.R;
import com.example.catchtime.entity.Location;
import com.example.catchtime.fragment.MyAdapterLocation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DefaultAddress extends AppCompatActivity {

    private List<Location> locations = new ArrayList<>();
    private MyAdapterLocation myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.defaultaddressselection);

        Location location = new Location();
        location.setLocThing("学院");
        locations.add(location);
        Location location1 = new Location();
        location1.setLocThing("宿舍");
        locations.add(location1);


        myAdapter = new MyAdapterLocation(getApplicationContext(),locations,R.layout.item_location);
        ListView listView = findViewById(R.id.def_lv);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AddDefaultAddress.class);
                intent.putExtra("name",locations.get(position).getLocThing());
                startActivity(intent);
            }
        });
    }
}
