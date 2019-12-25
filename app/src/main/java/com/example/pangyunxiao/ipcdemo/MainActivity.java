package com.example.pangyunxiao.ipcdemo;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pangyunxiao.ipcdemo.R;
import com.xiaowei.aidlclient.utils.MdmAidlHelper;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {
  private Button btnGetuserinfo;
  private Button btnToken;
  private Button btnQuit;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    MdmAidlHelper.getInstance().init(this.getApplication());
    btnToken = (Button) findViewById(R.id.btn_token);
    btnGetuserinfo = (Button) findViewById(R.id.btn_getuserinfo);
    btnQuit = (Button) findViewById(R.id.btn_quit);
    btnGetuserinfo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Toast.makeText(MainActivity.this, MdmAidlHelper.getInstance().getUserInfo().getData(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    btnToken.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {


        try {
          final String data = MdmAidlHelper.getInstance().getToken(true).getData();
          Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    btnQuit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        try {
          Toast.makeText(MainActivity.this, MdmAidlHelper.getInstance().quitLogin().getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
}
