package com.tarighi.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

public class MainActivity extends AppCompatActivity {

    UserInfo currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//
        Button btnViewList =findViewById(R.id.btnViewList);
        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,RecyclerActivity.class);
                startActivityForResult(intent,Constant.RecyclerRequestCode);
            }
        });


    }

    private void SetView()
    {
        try {
            TextView wellcome = findViewById(R.id.wellcome);
            Button btnEdit = findViewById(R.id.btnEdit);
            Button btnShow = findViewById(R.id.btnShow);
            RoundedImageView imgAvatar = findViewById(R.id.imgAvatar);
            currentUser = UserController.INSTANCE.getCurrentUser();
            if (currentUser != null) {

                wellcome.setText(getString(R.string.wellcome_dear) + " " + currentUser.GetFullName());
                btnShow.setVisibility(View.VISIBLE);
                imgAvatar.setVisibility(View.VISIBLE);
                btnEdit.setText(R.string.edit_profile);

                if (currentUser.AVATAR != null && currentUser.AVATAR.length() > 0) {
                    Uri selectedImage = Uri.parse(currentUser.AVATAR);
                    RoundedImageView imageView = findViewById(R.id.imgAvatar);
                    imageView.setImageURI(selectedImage);
                    imageView.setTag(selectedImage.toString());
                }
            } else {
                finish();
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constant.RegisterRequestCode){
            if(resultCode== Activity.RESULT_OK){
                SetView();
                Toast.makeText(this, getString(R.string.successfulConfirm), Toast.LENGTH_LONG).show();
            }

        }
        else if(requestCode==Constant.ConfirmRequestCode){

        }

    }
}
