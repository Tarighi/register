package com.tarighi.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.transition.Visibility;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Hawk.init(MainActivity.this).build();

        SetView();

        Button btnEdit =findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent,Constant.RegisterRequestCode);
            }
        });

        Button btnShow =findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,ConfirmActivity.class);
                startActivityForResult(intent,Constant.ConfirmRequestCode);
            }
        });
    }

    private void SetView()
    {
        TextView wellcome =findViewById(R.id.wellcome);
        Button btnEdit =findViewById(R.id.btnEdit);
        Button btnShow =findViewById(R.id.btnShow);
        ImageView imgAvatar =findViewById(R.id.imgAvatar);
        if(Hawk.contains(Constant.Name)){

            String name=Hawk.get(Constant.Name);

            wellcome.setText(getString(R.string.wellcome_dear)+" "+ name);
            btnShow.setVisibility(View.VISIBLE);
            imgAvatar.setVisibility(View.VISIBLE);
            btnEdit.setText(R.string.edit_profile);
        }
        else{
            wellcome.setText(R.string.wellcome);
            btnShow.setVisibility(View.GONE);
            imgAvatar.setVisibility(View.GONE);
            btnEdit.setText(R.string.register);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.RegisterRequestCode){
            if(resultCode== Activity.RESULT_OK){
                SetView();
                if(Hawk.contains(Constant.Name)) {
                    Toast.makeText(this, getString(R.string.successfulConfirm), Toast.LENGTH_LONG).show();
                }
            }

        }
        else if(requestCode==Constant.ConfirmRequestCode){

        }

    }
}
