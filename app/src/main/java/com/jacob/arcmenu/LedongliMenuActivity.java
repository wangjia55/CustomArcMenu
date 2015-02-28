package com.jacob.arcmenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LedongliMenuActivity extends Activity  {

    private LeMenu ledongliMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledongli_menu);

        ledongliMenu = (LeMenu) findViewById(R.id.ledongli_menu);
        ledongliMenu.setOnMenuClickListener(new LeMenu.OnLeMenuClickListener() {
            @Override
            public void onLeMenuClick(View view, int position) {
                Toast.makeText(LedongliMenuActivity.this, ""+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
