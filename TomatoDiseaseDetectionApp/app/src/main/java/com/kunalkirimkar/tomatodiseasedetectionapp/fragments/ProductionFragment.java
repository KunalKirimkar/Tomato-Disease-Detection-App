package com.kunalkirimkar.tomatodiseasedetectionapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.kunalkirimkar.tomatodiseasedetectionapp.R;
import com.kunalkirimkar.tomatodiseasedetectionapp.adapter.ProductionListAdapter;
import com.kunalkirimkar.tomatodiseasedetectionapp.dto.ProductionDTO;

import java.util.ArrayList;

public class ProductionFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar skillProgress;
    private ProductionListAdapter productionListAdapter;
    private ArrayList<ProductionDTO> list;
    private ImageButton options;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_production, container, false);
        recyclerView = view.findViewById(R.id.productionList);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        list = new ArrayList<>();
        productionListAdapter = new ProductionListAdapter(getContext(), list);
//        productionListAdapter.setOnItemClickListener(new ProductionListAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int id) {
//                ProductionDTO productionDTO = list.get(id);
//                String productionItemId = productionDTO.getId();
//
//                Intent intent = new Intent(requireActivity(), ProductionInfoActivity.class);
//                intent.putExtra("productionItemId", productionItemId);
//                startActivity(intent);
//            }
//        });
        recyclerView.setAdapter(productionListAdapter);

        return view;
    }
}