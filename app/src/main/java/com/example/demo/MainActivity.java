package com.example.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.plugin.library.permissions.OnPermissionsListener;
import com.plugin.library.permissions.PermissionsManager;
import com.plugin.library.permissions.PermissionsSettings;
import com.plugin.library.view.titlebar.TitleBar;

import java.util.List;

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

                PermissionsManager.getInstance(MainActivity.this).request(new PermissionsSettings.Builder()
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).build(), new OnPermissionsListener() {
                    @Override
                    public void onGranted() {
                        Toast.makeText(MainActivity.this,"onGranted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,TestActivity.class));
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        Toast.makeText(MainActivity.this,"onDenied",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
