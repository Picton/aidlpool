package com.xiaowei.aidlclient;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

 import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaowei.aidlclient.utils.MdmAidlHelper;

public class MainActivity extends AppCompatActivity {
  private Button btnGetuserinfo;
  private Button btnToken;
  private Button btnQuit;




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    MdmAidlHelper.getInstance().init();
    btnToken =  findViewById(R.id.btn_token);
    btnGetuserinfo =  findViewById(R.id.btn_getuserinfo);
    btnQuit =  findViewById(R.id.btn_quit);
    btnGetuserinfo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Toast.makeText(MainActivity.this, MdmAidlHelper.getInstance().getUserInfo().getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }); btnToken.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        try {
          Toast.makeText(MainActivity.this, MdmAidlHelper.getInstance().getToken(true).getMessage(), Toast.LENGTH_SHORT).show();
        } catch ( Exception e) {
          e.printStackTrace();
        }


      }
    }); btnQuit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        try {
          Toast.makeText(MainActivity.this, MdmAidlHelper.getInstance().quitLogin().getMessage(), Toast.LENGTH_SHORT).show();
        } catch ( Exception e) {
          e.printStackTrace();
        }


      }
    });
  }
}
