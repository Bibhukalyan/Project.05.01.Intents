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

import com.techhue.adapters.HorseListAdapter;
import com.techhue.models.HorseModel;

import java.util.ArrayList;

public class SelectHorseActivity extends Activity
{

    public static final String SELECTED_HORSE = "SELECTED_HORSE";
    ArrayList<HorseModel> horseModels = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_layout);
        horseModels.clear();
        horseModels.add(new HorseModel(R.drawable.horse1,"Horse1"));
        horseModels.add(new HorseModel(R.drawable.horse2,"Horse2"));
        horseModels.add(new HorseModel(R.drawable.horse3,"Horse3"));
        horseModels.add(new HorseModel(R.drawable.horse4,"Horse4"));
        horseModels.add(new HorseModel(R.drawable.horse5,"Horse5"));
        horseModels.add(new HorseModel(R.drawable.horse6,"Horse6"));
        horseModels.add(new HorseModel(R.drawable.horse7,"Horse7"));

        final RecyclerView listView = (RecyclerView) findViewById(R.id.listView1);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listView.setAdapter(new HorseListAdapter(this,horseModels));

        /**
         * Listing 5-5: Returning a result from a sub-Activity
         */
        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*long selected_horse_id = listView.getSelectedItemId();

                Uri selectedHorse = Uri.parse("content://horses/" +
                        selected_horse_id);
                Intent result = new Intent(Intent.ACTION_PICK, selectedHorse);

                setResult(RESULT_OK, result);
                finish();*/
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        /*cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });*/
    }
}