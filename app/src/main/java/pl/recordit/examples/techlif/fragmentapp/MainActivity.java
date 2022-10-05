package pl.recordit.examples.techlif.fragmentapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Button;

import pl.recordit.examples.techlif.fragmentapp.ui.form.FormFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.main.MainFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.progress.ProgressFragment;

public class MainActivity extends AppCompatActivity {
    private Button mainButton;
    private Button formButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainButton = findViewById(R.id.executorButton);
        formButton = findViewById(R.id.form);
        MainFragment main = MainFragment.newInstance();
        ProgressFragment progress = ProgressFragment.newInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, FormFragment.getInstance(),"form")
                    .add(R.id.container, main, "main")
                    .add(R.id.container, progress, "progress")
                    .hide(main)
                    .hide(progress)
                    .commitNow();
        }
        mainButton.setOnClickListener(v ->{
            Fragment m = getSupportFragmentManager()
                    .findFragmentByTag("main");
            Fragment f = getSupportFragmentManager()
                    .findFragmentByTag("form");
            Fragment p = getSupportFragmentManager()
                    .findFragmentByTag("progress");
            if (f != null && m != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(f)
                        .hide(p)
                        .show(m)
                        .commitNow();
            }
        });
        formButton.setOnClickListener(v ->{
            Fragment m = getSupportFragmentManager()
                    .findFragmentByTag("main");
            Fragment f = getSupportFragmentManager()
                    .findFragmentByTag("form");
            Fragment p = getSupportFragmentManager()
                    .findFragmentByTag("progress");
            if (f != null && m != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(m)
                        .hide(f)
                        .show(p)
                        .commitNow();
            }
        });

    }
}