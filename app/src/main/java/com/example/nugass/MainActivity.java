package com.example.nugass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String playerCoins;
    private String textUserName, textUserCoins, textUserCharacter;
    private DatabaseHelper db = new DatabaseHelper(MainActivity.this);
    private Bundle args = new Bundle();
    private EditText inputUserName;

    ArrayList<String> userId, userName, userCoins, userCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userId = new ArrayList<>();
        userName = new ArrayList<>();
        userCoins = new ArrayList<>();
        userCharacter = new ArrayList<>();

        playerCoins = "";

        // ActionBar actionBar = getActionBar();
        // actionBar.setDisplayShowHomeEnabled(false);
        // LayoutInflater inflater = LayoutInflater.from(this);

//        sendDataToFragments();

        reloadUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        playerCoins = textUserCoins;

        TextView tv = new TextView(this);
        tv.setText(playerCoins);
        tv.setPadding(0,15,30,0);
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_coins,0);
        menu.add(0, 0, 1, "Cat").setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);


        return true;
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            break;
                        case R.id.nav_store:
                            selectedFragment = new StoreFragment();
                            break;
                    }

                    selectedFragment.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    public void storeUserDataInArrays() {
        Cursor cursor = db.readUserData();
        if (cursor.getCount() == 0) {
            userId.add("1");
            userName.add("USER");
            userCoins.add("0");
            userCharacter.add("character5_128");

            registerUser();
        } else {
            Toast.makeText(this, "Existing User", Toast.LENGTH_SHORT).show();
            while (cursor.moveToNext()) {
                userId.add(cursor.getString(0));
                userName.add(cursor.getString(1));
                userCoins.add(cursor.getString(2));
                userCharacter.add(cursor.getString(3));
            }
        }
    }

    public void reloadUI() {
        storeUserDataInArrays();
        textUserName = String.valueOf(userName.get(0));
        textUserCharacter = String.valueOf(userCharacter.get(0));
        textUserCoins = String.valueOf(userCoins.get(0)) + " ";
        int textUserCoinsInt = Integer.valueOf(userCoins.get(0));

        args.putString("textUserCharacter", textUserCharacter);
        args.putString("textUserName", textUserName);
        args.putInt("textUserCoins", Integer.valueOf(textUserCoinsInt));

        Fragment homeFragment = new HomeFragment();
        homeFragment.setArguments(args);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(navListener);
        bottomNav.setSelectedItemId(R.id.nav_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                homeFragment).commit();
    }

    public void registerUser() {
        showDialog();
    }

    public void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater linf = LayoutInflater.from(this);
        View inflator = linf.inflate(R.layout.dialog_register, null);
        inputUserName = inflator.findViewById(R.id.input_user_name);

        alertDialogBuilder
                .setView(inflator)
                .setCancelable(false)
                .setPositiveButton("Berikutnya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String usrName = inputUserName.getText().toString();
                        String exName = "Fauzan";
                        db.addUserData(usrName, 1000, "character1_128");
                        recreate();
                    }
                })
                .setNegativeButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.finish();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}