//package com.example.catchtime.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.example.catchtime.R;
//import com.example.catchtime.entity.Locations;
//import com.example.catchtime.fragment.MyAdapterLocation;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class DefaultAddress extends AppCompatActivity {
//
//    private List<Locations> locations = new ArrayList<>();
//    private MyAdapterLocation myAdapter;
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.defaultaddressselection);
//
//        Locations location = new Locations();
//        location.setLocThing("学院");
//        locations.add(location);
//        Locations location1 = new Locations();
//        location1.setLocThing("宿舍");
//        locations.add(location1);
//
//
//        myAdapter = new MyAdapterLocation(getApplicationContext(),locations,R.layout.item_location);
//        ListView listView = findViewById(R.id.def_lv);
//        listView.setAdapter(myAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.setClass(getApplicationContext(), AddDefaultAddress.class);
//                intent.putExtra("name",locations.get(position).getLocThing());
//                startActivity(intent);
//            }
//        });
//    }
//}
