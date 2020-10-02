package com.example.fetchrewards;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity2 extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Bundle s = getIntent().getExtras();
        assert s != null;
        Integer key = s.getInt("LISTID");
        setTitle("Names in ListID "+key.toString());
        ArrayList<String> al = Service.resultList.get(key);
        ListView lv2 = findViewById(R.id.lv2);

        assert al != null;
        Collections.sort (al, new Comparator<String>() {
            public int compare(String o1, String o2)
            {
                String sub1 = o1.substring (5);
                String sub2 = o2.substring (5);
                return Integer.valueOf(sub1).compareTo(Integer.valueOf(sub2));
            }
        });

        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1,al);
        lv2.setAdapter(ad);
    }
}