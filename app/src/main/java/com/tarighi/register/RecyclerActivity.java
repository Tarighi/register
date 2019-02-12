package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        RecyclerView recycler=findViewById(R.id.recycler);
        recycler.setAdapter(new RvAdapter(UserController.INSTANCE.getUsers()));
        recycler.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }
}
