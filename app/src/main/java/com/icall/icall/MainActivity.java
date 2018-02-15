package com.icall.icall;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.icall.icall.util.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        setNavigationItemSelectedListener(navigation);

        startActivity("com.icall.icall.LOGIN");
    }

    private void setNavigationItemSelectedListener(BottomNavigationView navigation){
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.home);
                        return true;
                    case R.id.navigation_notice:
                        mTextMessage.setText(R.string.notice);
                        return true;
                    case R.id.navigation_release:
                        mTextMessage.setText(R.string.release);
                        return true;
                    case R.id.navigation_other:
                        mTextMessage.setText(R.string.other);
                        return true;
                    case R.id.navigation_config:
                        mTextMessage.setText(R.string.config);
                        return true;
                }
                return false;
            }
        });
    }

    private void startActivity(String action){
        try {
            Intent intent = new Intent();
            intent.setAction(action);
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
