package com.techhue.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techhue.intents.R;
import com.techhue.intents.SelectHorseActivity;
import com.techhue.models.HorseModel;

import java.util.ArrayList;

public class HorseListAdapter extends RecyclerView.Adapter<HorseListAdapter.ViewHolder> {

    private ArrayList<HorseModel> horseList;
    private LayoutInflater inflater;
    private Activity mcontext;

    public HorseListAdapter(Context context, ArrayList<HorseModel> horseList) {
        inflater = LayoutInflater.from(context);
        this.horseList = horseList;
        this.mcontext = (Activity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_horse_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (horseList != null && horseList.get(position) != null){
            holder.ivHorse.setImageResource(horseList.get(position).getHorseImage());
            holder.tvHorseName.setText(horseList.get(position).getHorseName());
            holder.containerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mcontext, "Clicked", Toast.LENGTH_SHORT).show();
                    mcontext.setResult(Activity.RESULT_OK,new Intent().putExtra(SelectHorseActivity.SELECTED_HORSE,horseList.get(position)));
                    mcontext.finish();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return horseList.size();
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
