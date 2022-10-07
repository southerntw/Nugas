package com.example.nugass;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class StoreFragment extends Fragment {

    private ImageView userCharacter;
    private ArrayList<String> userCharacterId;
    private int userCoins;
    private Button c1, c2, c3, c4, c5;

    private DatabaseHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_store, container, false);

        userCharacter = v.findViewById(R.id.sprites_character);
        userCharacterId = new ArrayList<>();

        db = new DatabaseHelper(getContext());

        refreshUserData();

        c1 = v.findViewById(R.id.buy_c1);
        c2 = v.findViewById(R.id.buy_c2);
        c3 = v.findViewById(R.id.buy_c3);
        c4 = v.findViewById(R.id.buy_c4);
        c5 = v.findViewById(R.id.buy_c5);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCharacter("character1_128");
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCharacter("character2_128");
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCharacter("character3_128");
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCharacter("character4_128");
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyCharacter("character5_128");
            }
        });

        return v;
    }

    public void refreshUserData() {
        Bundle args = this.getArguments();
        if (args != null) {
            userCoins = args.getInt("textUserCoins");
            String textUserCharacter = args.getString("textUserCharacter");
            String temp_character = textUserCharacter;
            int character = getResources().getIdentifier(temp_character, "drawable", "com.example.nugass");
            userCharacter.setImageResource(character);
        }
    }

    public void buyCharacter(String characterID) {
        int coins = userCoins;
        if (coins < 100) {
            Toast.makeText(getActivity(), "Insufficient Coins", Toast.LENGTH_SHORT);
        } else {
            coins -= 100;
            db.updateCharacter(characterID);
            db.updateCoins(coins);
            Toast.makeText(getActivity(), "Successfully bought the thing", Toast.LENGTH_SHORT);
            getActivity().recreate();
        }
    }
}
