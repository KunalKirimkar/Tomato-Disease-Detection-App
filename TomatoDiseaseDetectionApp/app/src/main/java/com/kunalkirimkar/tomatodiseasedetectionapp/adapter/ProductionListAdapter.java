package com.kunalkirimkar.tomatodiseasedetectionapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kunalkirimkar.tomatodiseasedetectionapp.R;
import com.kunalkirimkar.tomatodiseasedetectionapp.dto.ProductionDTO;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductionListAdapter extends RecyclerView.Adapter<ProductionListAdapter.ProductionViewHolder> {

    private Context context;
    private ArrayList<ProductionDTO> list;
    private OnItemClickListener listener;

    public ProductionListAdapter(Context context, ArrayList<ProductionDTO> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ProductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.production_items, parent, false);
        return new ProductionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ProductionDTO skill = list.get(position);
        holder.productionTV.setText(skill.getName());

        Glide.with(context)
                .load(skill.getSkillImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.productionImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ProductionViewHolder extends RecyclerView.ViewHolder {

        TextView productionTV;
        ImageView productionImage;

        public ProductionViewHolder(@NotNull View producionView) {
            super(producionView);
            productionTV = producionView.findViewById(R.id.productionText);
            productionImage = producionView.findViewById(R.id.productionImage);
        }
    }
}
