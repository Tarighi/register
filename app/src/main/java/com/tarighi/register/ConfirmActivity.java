package com.tarighi.register;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserInfo currentUser= UserController.INSTANCE.getCurrentUser();

        if (currentUser!=null) {

            setContentView(R.layout.activity_confirm);
            ((TextView) findViewById(R.id.txtName)).setText(currentUser.FirstName);
            ((TextView) findViewById(R.id.txtFamliy)).setText(currentUser.Family);
            ((TextView) findViewById(R.id.txtAge)).setText(Integer.toString(currentUser.Age));
            ((TextView) findViewById(R.id.txtEmail)).setText(currentUser.Email);
            ((TextView) findViewById(R.id.txtMobile)).setText(Long.toString( currentUser.Mobile));
            if( currentUser.AVATAR!=null && currentUser.AVATAR.length()>0) {
                Uri selectedImage = Uri.parse(currentUser.AVATAR);
                RoundedImageView imageView = (RoundedImageView) findViewById(R.id.imgAvatar);
                imageView.setImageURI(selectedImage);
                imageView.setTag(selectedImage.toString());
            }
            ((TextView) findViewById(R.id.txtCity)).setText(currentUser.City);

            Button btnSubmit = findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else{
            finish();
        }
    }


}
