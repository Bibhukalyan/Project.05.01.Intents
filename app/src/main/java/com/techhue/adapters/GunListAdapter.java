package com.techhue.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techhue.intents.MyOtherActivity;
import com.techhue.intents.R;
import com.techhue.intents.SelectHorseActivity;
import com.techhue.models.GunModel;
import com.techhue.models.HorseModel;

import java.util.ArrayList;

public class GunListAdapter extends RecyclerView.Adapter<GunListAdapter.ViewHolder> {

    private ArrayList<GunModel> gunModels;
    private LayoutInflater inflater;
    private Activity mcontext;

    public GunListAdapter(Context context, ArrayList<GunModel> gunModels) {
        inflater = LayoutInflater.from(context);
        this.gunModels = gunModels;
        this.mcontext = (Activity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_horse_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (gunModels != null && gunModels.get(position) != null){
            holder.ivHorse.setImageResource(gunModels.get(position).getGunImage());
            holder.tvHorseName.setText(gunModels.get(position).getGunName());
            holder.containerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mcontext, "Clicked", Toast.LENGTH_SHORT).show();
                    mcontext.setResult(Activity.RESULT_OK,new Intent().putExtra(MyOtherActivity.GUN_SELECTED,gunModels.get(position)));
                    mcontext.finish();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return gunModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivHorse;
        TextView tvHorseName;
        View containerLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            containerLayout = itemView;//itemView.findViewById(R.id.container_layout);
            ivHorse = itemView.findViewById(R.id.ivHorse);
            tvHorseName = itemView.findViewById(R.id.tvHorseName);
        }


    }



}
