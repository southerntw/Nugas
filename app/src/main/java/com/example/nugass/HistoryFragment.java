package com.example.nugass;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    private DatabaseHelper db;
    ArrayList<String> taskDate, taskName, taskReward;

    private ImageView userCharacter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = v.findViewById(R.id.recycler_view);
        db = new DatabaseHelper(getActivity());
        taskDate = new ArrayList<>();
        taskName = new ArrayList<>();
        taskReward = new ArrayList<>();

        userCharacter = v.findViewById(R.id.sprites_character);

        storeHistoryDataInArrays();

        customAdapter = new CustomAdapter(getActivity(), taskDate, taskName, taskReward);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshUserData();

        return v;
    }

    public void storeHistoryDataInArrays() {
        Cursor cursor = db.readHistoryData();
        if (cursor.getCount() == 0) {
        } else {
            while (cursor.moveToNext()) {
                taskName.add(cursor.getString(1));
                taskReward.add(cursor.getString(2));
                taskDate.add(cursor.getString(3));
            }
        }
    }

    public void refreshUserData() {
        Bundle args = this.getArguments();
        if (args != null) {
            String textUserCharacter = args.getString("textUserCharacter");
            String temp_character = textUserCharacter;
            int character = getResources().getIdentifier(temp_character, "drawable", "com.example.nugass");
            userCharacter.setImageResource(character);
        }
    }
}
