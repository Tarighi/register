package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void registerMe(View v) {

        String Name=((EditText)findViewById(R.id.edtName)).getText().toString();
        String Famliy=((EditText)findViewById(R.id.edtFamliy)).getText().toString();
        String Age=((EditText)findViewById(R.id.edtAge)).getText().toString();
        String Email=((EditText)findViewById(R.id.edtEmail)).getText().toString();
        String Mobile=((EditText)findViewById(R.id.edtMobile)).getText().toString();

       Intent intent =new Intent(MainActivity.this,ConfirmActivity.class);
        intent.putExtra("Name",Name);
        intent.putExtra("Famliy",Famliy);
        intent.putExtra("Age",Age);
        intent.putExtra("Email",Email);
        intent.putExtra("Mobile",Mobile);
       startActivity(intent);
    }
}
