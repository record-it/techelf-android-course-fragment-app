package pl.recordit.examples.techlif.fragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;

import pl.recordit.examples.techlif.fragmentapp.ui.form.FormFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {
    private Button mainButton;
    private Button formButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = findViewById(R.id.main);
        formButton = findViewById(R.id.form);
        MainFragment main = MainFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, FormFragment.getInstance(),"form")
                    .add(R.id.container, main, "main")
                    .hide(main)
                    .commitNow();
        }
        mainButton.setOnClickListener(v ->{
            Fragment m = getSupportFragmentManager()
                    .findFragmentByTag("main");
            Fragment f = getSupportFragmentManager()
                    .findFragmentByTag("form");
            if (f != null && m != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(f)
                        .show(m)
                        .commitNow();
            }
        });


    }
}