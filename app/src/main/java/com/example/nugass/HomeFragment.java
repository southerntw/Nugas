package com.example.nugass;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {
    private EditText taskName, taskWork, taskBreak;
    private TextView userName;
    private ImageView userCharacter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        taskName = v.findViewById(R.id.input_task);
        taskWork = v.findViewById(R.id.input_work);
        taskBreak = v.findViewById(R.id.input_break);

        userName = v.findViewById(R.id.text_name);
        userCharacter = v.findViewById(R.id.sprites_character);

        refreshUserData();

        Button btn = (Button) v.findViewById(R.id.button_start);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String valueTaskName = taskName.getText().toString();
                String valueTaskWork = taskWork.getText().toString();
                String valueTaskBreak = taskBreak.getText().toString();

                Intent i = new Intent(getActivity(), TimerActivity.class);
                i.putExtra("taskName", valueTaskName);
                i.putExtra("taskWork", valueTaskWork);
                i.putExtra("taskBreak", valueTaskBreak);
                startActivity(i);
            }
        });

        return v;
    }

    public void refreshUserData() {
        Bundle args = this.getArguments();
        if (args != null) {
            String textUserName = args.getString("textUserName");
            String textUserCharacter = args.getString("textUserCharacter");
            userName.setText(textUserName);

            String temp_character = textUserCharacter;
            int character = getResources().getIdentifier(temp_character, "drawable", "com.example.nugass");
            userCharacter.setImageResource(character);
        }
    }
}
