package com.example.duanmau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanmau.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QLPhieuMuonFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerwiewQLPhieumuon);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatAdd);
        return view;
    }
}
