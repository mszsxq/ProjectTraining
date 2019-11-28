package com.example.catchtime.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.catchtime.AddLocation;
import com.example.catchtime.ModifyPage;
import com.example.catchtime.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

public class ChangeLocal extends SwipeBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changelocal);
        ListView listView= (ListView) findViewById(R.id.lvcontent);
        int flag=getIntent().getIntExtra("resource",0);
        String st=getIntent().getStringExtra("poi");
        String str=getIntent().getStringExtra("poi").substring(1,st.length()-1);
        List<String> arrs=new ArrayList<>();
        arrs= Arrays.asList(str.split("name"));
        listView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,arrs));
        final List<String> finalArrs = arrs;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (flag==0){
                    intent=new Intent(view.getContext(), AddLocation.class);
                }else {
                    intent=new Intent(view.getContext(), ModifyPage.class);
                }
                intent.putExtra("local1", finalArrs.get(position));
                startActivity(intent);
                finish();
            }
        });
    }
}
