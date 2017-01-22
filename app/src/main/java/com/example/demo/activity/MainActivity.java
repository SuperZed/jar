package com.example.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.demo.R;
import com.plugin.library.view.titlebar.TitleBar;

public class MainActivity extends AppCompatActivity {

    TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleBar= (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setTitle("标题");
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ExpandableListViewActivity.class));
//                PermissionsManager.getInstance(MainActivity.this).request(new PermissionsSettings.Builder()
//                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).build(), new OnPermissionsListener() {
//                    @Override
//                    public void onGranted() {
//                        Toast.makeText(MainActivity.this,"onGranted",Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(MainActivity.this,TestActivity.class));
//                    }
//
//                    @Override
//                    public void onDenied(List<String> permissions) {
//                        Toast.makeText(MainActivity.this,"onDenied",Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }
}
