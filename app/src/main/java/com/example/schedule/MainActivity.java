package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<RecyclerViewItem> mList;
    private RecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstInit();

        for(int i=0;i<5;i++){
            //addItem("iconName", "MAC" + i, "loaction");
            addItem("iconName", "MAC" + i, "loaction");
        }

        mRecyclerViewAdapter = new RecyclerViewAdapter(mList);

        mRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "onItemClick position : " + pos, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerViewAdapter.setOnLongItemClickListener(new RecyclerViewAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                Toast.makeText(getApplicationContext(), "onLingItemClick position : " + pos, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); 세로
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)); //가로
    }

    public void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mList = new ArrayList<>();
    }

    public void addItem(String imgName, String mainText, String subText){
        RecyclerViewItem item = new RecyclerViewItem();

        item.setImgName(imgName);
        item.setMainText(mainText);
        item.setSubText(subText);

        mList.add(item);
    }
}