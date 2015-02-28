package com.jacob.arcmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import static android.view.View.OnClickListener;


public class MainActivity extends FragmentActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_arc_menu).setOnClickListener(this);
        findViewById(R.id.button_custom_menu).setOnClickListener(this);
        findViewById(R.id.button_le_menu).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_arc_menu:
                intent = new Intent(MainActivity.this, ArcMenuActivity.class);
                break;
            case R.id.button_custom_menu:
                intent = new Intent(MainActivity.this, SimpleArcMenuActivity.class);
                break;
            case R.id.button_le_menu:
                intent = new Intent(MainActivity.this, LedongliMenuActivity.class);
                break;
        }
        startActivity(intent);
    }
}
