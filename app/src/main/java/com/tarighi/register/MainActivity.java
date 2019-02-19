package com.tarighi.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.tarighi.register.Helpers.OnAPIResponseEventListener;
import com.tarighi.register.Helpers.TimingController;
import com.tarighi.register.Helpers.Timings;

public class MainActivity extends AppCompatActivity {

    UserInfo currentUser;
    private DrawerLayout mDrawerLayout;
    TimingController timingController;
    boolean backPressed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetView();
        mDrawerLayout = findViewById(R.id.drawer_layout);
       ImageView imgDrawerSwitch = findViewById(R.id.imgDrawerSwitch);

        imgDrawerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout myDrawer=findViewById(R.id.myDrawer);
                if(mDrawerLayout.isDrawerOpen(myDrawer)){
                    mDrawerLayout.closeDrawers();
                }
                else {
                    mDrawerLayout.openDrawer(myDrawer);
                }

            }
        });



        Button btnEdit =findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto(RegisterActivity.class,Constant.RegisterRequestCode);
            }
        });

        Button btnShow =findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto(ConfirmActivity.class,Constant.ConfirmRequestCode);
            }
        });
//
        Button btnViewList =findViewById(R.id.btnViewList);
        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goto(RecyclerActivity.class,Constant.RecyclerRequestCode);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(backPressed)
        {
            super.onBackPressed();
        }

        else {
            backPressed=true;
            Toast.makeText(MainActivity.this, "Press Back again to SignOut", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        backPressed = false;
                    }
            },2500);
        }

    }

    private void Goto(Class<?> cls, int requestCode)
    {
        Intent intent =new Intent(MainActivity.this,cls);
        startActivityForResult(intent,requestCode);
        mDrawerLayout.closeDrawers();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuEdit:
                Goto(RegisterActivity.class,Constant.RegisterRequestCode);
                break;
            case R.id.mnuShow:
                Goto(ConfirmActivity.class,Constant.ConfirmRequestCode);
                break;
            case R.id.mnuViewList:
                Goto(RecyclerActivity.class,Constant.RecyclerRequestCode);
                break;
        }
        return  true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        timingController=new TimingController();

        timingController.callTimingsAPI(currentUser.City);
        timingController.setOnAPIResponseEventListener(new OnAPIResponseEventListener() {
            @Override
            public void onAPIResponse(final Timings data) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setTimings(data);
                    }
                });

            }
        });
    }

    private  void setTimings(Timings data)
    {
        LinearLayout linearLayout4=findViewById(R.id.linearLayout4);
        linearLayout4.setVisibility(View.VISIBLE);
        TextView txtCity =findViewById(R.id.txtCity);

        TextView txtFajr =findViewById(R.id.txtFajr);
        TextView txtSunrise =findViewById(R.id.txtSunrise);
        TextView txtDhuhr =findViewById(R.id.txtDhuhr);
        TextView txtAsr =findViewById(R.id.txtAsr);
        TextView txtSunset =findViewById(R.id.txtSunset);
        TextView txtMaghrib =findViewById(R.id.txtMaghrib);
        TextView txtIsha =findViewById(R.id.txtIsha);
        TextView txtImsak =findViewById(R.id.txtImsak);
        TextView txtMidnight =findViewById(R.id.txtMidnight);

        txtCity.setText("Timings In "+data.City);
        txtFajr.setText("Fajr : "+data.Fajr);
        txtSunrise.setText("Sunrise : "+data.Sunrise);
        txtDhuhr.setText("Dhuhr : "+data.Dhuhr);
        txtAsr.setText("Asr : "+data.Asr);
        txtSunset.setText("Sunset : "+data.Sunset);
        txtMaghrib.setText("Maghrib : "+data.Maghrib);
        txtIsha.setText("Isha : "+data.Isha);
        txtImsak.setText("Imsak : "+data.Imsak);
        txtMidnight.setText("Midnight : "+data.Midnight);
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
