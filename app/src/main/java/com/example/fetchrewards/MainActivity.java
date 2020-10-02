package com.example.fetchrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    ListView lv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("List ID");
        HashMap<Integer, ArrayList<String>> resultList = null;
        try {
            resultList = new Service().execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        lv1 = findViewById(R.id.lv1);

        assert resultList != null;
        Set<Integer> as= resultList.keySet();
        final ArrayList<Integer> al = new ArrayList<>(as);
        Collections.sort(al);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1,al);
        lv1.setAdapter(ad);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                Integer s = al.get(position);
                intent.putExtra("LISTID",s);
                startActivity(intent);
            }
        });

    }
}