package pl.recordit.examples.techlif.fragmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import pl.recordit.examples.techlif.fragmentapp.ui.exercise.ExerciseFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.form.FormFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.main.MainFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.map.MapFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.progress.ProgressFragment;
import pl.recordit.examples.techlif.fragmentapp.ui.sensors.SensorFragment;

public class MainActivity extends AppCompatActivity {
    private Button mainButton;
    private Button formButton;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case R.id.action_progress:
               switchToFragment("progress");
               return true;
           case R.id.action_list:
               return true;
           case R.id.action_camera:
               return true;
           case R.id.action_exercise:
               switchToFragment("sensor");
               return true;
           case R.id.action_thread:
               switchToFragment("main");
               return true;
           case R.id.action_form:
               switchToFragment("form");
               return true;
           case R.id.action_sensor:
               switchToFragment("sensor");
       }
       return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        MainFragment main = MainFragment.newInstance();
        ProgressFragment progress = ProgressFragment.newInstance();
        ExerciseFragment exercise = new ExerciseFragment();
        SensorFragment sensor = new SensorFragment();
        MapFragment map = new MapFragment();
        FormFragment form = FormFragment.getInstance();
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, form, "form")
                    .add(R.id.container, main, "main")
                    .add(R.id.container, progress, "progress")
                    .add(R.id.container, exercise, "exercise")
                    .add(R.id.container, sensor, "sensor")
                    .add(R.id.container, map, "map")
                    .hide(map)
                    .hide(main)
                    .hide(progress)
                    .hide(exercise)
                    .hide(sensor)
                    .commitNow();
        }
    }

    private void switchToFragment(String tag){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Optional<Fragment> showed = fragments
                .stream()
                .filter(f -> Objects.equals(f.getTag(), tag))
                .findAny();
        List<Fragment> hided = fragments.stream().filter(f -> !Objects.equals(f.getTag(), tag))
                .collect(Collectors.toList());
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        showed.ifPresent(transaction::show);
        for (Fragment fragment : hided) {
            transaction.hide(fragment);
        }
        transaction.commitNow();
    }
}