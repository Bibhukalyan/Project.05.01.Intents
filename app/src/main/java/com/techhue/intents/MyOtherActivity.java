package com.techhue.intents;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.techhue.adapters.GunListAdapter;
import com.techhue.models.GunModel;

import java.util.ArrayList;

public class MyOtherActivity extends Activity {

    /**
     * Listing 5-14: Finding the launch Intent in an Activity
     */
    ArrayList<GunModel> gunModels = new ArrayList<>();
    public static final String GUN_SELECTED = "GUN_SELECTED";

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);

        gunModels.add(new GunModel(R.drawable.gun1,"Gun1"));
        gunModels.add(new GunModel(R.drawable.gun2,"Gun2"));
        gunModels.add(new GunModel(R.drawable.gun3,"Gun3"));
        gunModels.add(new GunModel(R.drawable.gun4,"Gun4"));
        gunModels.add(new GunModel(R.drawable.gun5,"Gun5"));
        gunModels.add(new GunModel(R.drawable.gun6,"Gun6"));
        gunModels.add(new GunModel(R.drawable.gun7,"Gun7"));

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
    }

    @Override
    public void onStart() {
        super.onStart();

        setContentView(R.layout.selector_layout);
        final RecyclerView listView = (RecyclerView) findViewById(R.id.listView1);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listView.setAdapter(new GunListAdapter(this,gunModels));

        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*long selected_id = listView.getSelectedItemId();

                Intent result = new Intent(Intent.ACTION_PICK, Uri.parse(Long.toString(selected_id)));
                setResult(RESULT_OK, result);
                finish();*/
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*setResult(RESULT_CANCELED);
                finish();*/
            }
        });
    }
}