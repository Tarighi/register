package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Toast.makeText(TestActivity.this, UserController.INSTANCE.getCurrentUser().GetFullName(), Toast.LENGTH_SHORT).show();
    }
}
