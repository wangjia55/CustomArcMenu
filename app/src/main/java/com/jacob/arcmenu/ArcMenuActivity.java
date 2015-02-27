package com.jacob.arcmenu;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;


public class ArcMenuActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);
        ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arcmenu);
        arcMenu.setOnMenuClickListener(new ArcMenu.OnMenuClickListener() {
            @Override
            public void onclick(View v, int position) {
                Toast.makeText(ArcMenuActivity.this, v.getTag() + ":" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
