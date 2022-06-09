package com.example.okuwchy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder>{

    private Context context;
    private List<MarkModel> markModelList;

    public MarkAdapter(Context context, List<MarkModel> markModelList) {
        this.context = context;
        this.markModelList = markModelList;
    }

    @NonNull
    @Override
    public MarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mark,parent,false);
        MarkAdapter.ViewHolder viewHolder = new MarkAdapter.ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MarkAdapter.ViewHolder holder, int position) {

        holder.mark_item.setText(markModelList.get(position).getMark());
        holder.mark_sub.setText(markModelList.get(position).getSubject());
        holder.mark_day_i.setText(markModelList.get(position).getMarkday());
        holder.bellik.setText(markModelList.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return markModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mark_sub, mark_day_i, mark_item, bellik;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            mark_sub = itemView.findViewById(R.id.mark_sub_item);
            mark_day_i = itemView.findViewById(R.id.mark_day_item);
            mark_item = itemView.findViewById(R.id.mark_view);
            bellik = itemView.findViewById(R.id.mark_bellik);
        }
    }
}
