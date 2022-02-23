package com.sbitbd.ibrahimK_gc.ui.all_student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sbitbd.ibrahimK_gc.Adapter.class_adapter;
import com.sbitbd.ibrahimK_gc.R;
import com.sbitbd.ibrahimK_gc.ui.home.HomeViewModel;

public class all_student extends Fragment {

    private View root;
    private RecyclerView recyclerView;
//    private class_model class_model;
    private class_adapter class_adapter;
    private HomeViewModel homeViewModel = new HomeViewModel();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_student, container, false);
        initview();
        return root;
    }
    private void initview(){
        try {
            recyclerView = root.findViewById(R.id.all_class_rec);
            GridLayoutManager manager = new GridLayoutManager(root.getContext().getApplicationContext(), 2);
            recyclerView.setLayoutManager(manager);
            class_adapter = new class_adapter(root.getContext().getApplicationContext(),3);
            homeViewModel.get_class(root.getContext().getApplicationContext(),class_adapter);

            recyclerView.setAdapter(class_adapter);
        }catch (Exception e){
        }
    }
}