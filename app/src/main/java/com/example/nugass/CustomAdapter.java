package com.example.nugass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nugass.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList taskDate, taskName, taskReward;

    CustomAdapter(Context context, ArrayList taskDate, ArrayList taskName, ArrayList taskReward) {
        this.context = context;
        this.taskDate = taskDate;
        this.taskName = taskName;
        this.taskReward = taskReward;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.task_date.setText(String.valueOf(taskDate.get(position)));
        holder.task_name.setText(String.valueOf(taskName.get(position)));
        String coinsGained = "+" + String.valueOf(taskReward.get(position)) + " ";
        holder.task_reward.setText(coinsGained);
    }

    @Override
    public int getItemCount() {
        return taskName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView task_date, task_name, task_reward;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_date = itemView.findViewById(R.id.text_task_date);
            task_name = itemView.findViewById(R.id.text_task_name);
            task_reward = itemView.findViewById(R.id.text_coins_gained);
        }
    }
}
