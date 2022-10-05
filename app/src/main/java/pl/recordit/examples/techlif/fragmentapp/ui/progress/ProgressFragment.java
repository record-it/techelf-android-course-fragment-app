package pl.recordit.examples.techlif.fragmentapp.ui.progress;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import pl.recordit.examples.techlif.fragmentapp.R;

public class ProgressFragment extends Fragment {
    Button startButton;
    Button cancelButton;
    ProgressBar progressBar;
    public static ProgressFragment newInstance() {
        return new ProgressFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startButton = view.findViewById(R.id.buttonStart);
        progressBar = view.findViewById(R.id.progressBar);
        cancelButton = view.findViewById(R.id.cancelButton);
        MyAsyncTask task = new MyAsyncTask(progressBar);
        startButton.setOnClickListener(v -> {
            task.execute();
            startButton.setEnabled(false);
        });
        cancelButton.setOnClickListener(v -> {
            task.cancel();
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}