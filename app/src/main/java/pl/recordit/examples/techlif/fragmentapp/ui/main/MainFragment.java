package pl.recordit.examples.techlif.fragmentapp.ui.main;

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
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import pl.recordit.examples.techlif.fragmentapp.R;

public class MainFragment extends Fragment {
    private Button runButton;
    private Button runExecutorButton;
    private EditText messages;

    private Runnable task = () -> {
        Log.i("APP", "Thread beginig");
        try {
            //Thread.sleep(1000);
            long sum = 0;
            Log.i("APP", "ThreadId " + Thread.currentThread().getId());
            for(int i = 0; i < 1000000; i++){
                if (Thread.currentThread().isInterrupted()){
                    Log.i("APP", "Thread interrupted");
                    break;
                }

                sum += i;
            }
            Log.i("APP", "Sum " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("APP", "After delay");
    };
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Utwórz zadanie wykonywane cyklicznie co 1 sekundę logujące komunikat
        scheduler.scheduleAtFixedRate(() -> handler.post(() -> messages.setText(messages.getText().toString() + "A\n")), 0, 1, TimeUnit.SECONDS);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        runButton = view.findViewById(R.id.run_button);
        runExecutorButton = view.findViewById(R.id.executor_run);
        messages = view.findViewById(R.id.messages);
        runExecutorButton.setOnClickListener(v -> {
            executorService.execute(task);
            Future<?> future = executorService.submit(task);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.cancel(true);
        });

        runButton.setOnClickListener(v -> {
            Thread thread = new Thread(task);
            thread.start();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.interrupt();
            Log.i("APP", "Thread started");
            thread.start();
        });
    }
}