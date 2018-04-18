package com.haieros.path;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.haieros.path.path.PathText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final PathText kang = findViewById(R.id.kang);
        Button start = findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kang.start();
            }
        });
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kang.clear();
            }
        });

    }
}
