package pl.recordit.examples.techlif.fragmentapp.ui.progress;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pl.recordit.examples.techlif.fragmentapp.R;

public class ProgressFragment extends Fragment {
    Button startButton;
    Button cancelButton;
    ProgressBar progressBar;
    Handler handler = new Handler(Looper.getMainLooper());
    ExecutorService executorService = Executors.newSingleThreadExecutor();

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
        final Future<?>[] future = new Future<?>[1];
        startButton.setOnClickListener(v -> {
            //task.execute();
            future[0] = executorService.submit(new MyExecutorTask(progressBar, handler));
            startButton.setEnabled(false);
        });
        cancelButton.setOnClickListener(v -> {
            //task.cancel();
            if (future[0] != null){
                Log.i("APP", "ExecutorTask is cancel");
                future[0].cancel(true);
            }
        });

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}