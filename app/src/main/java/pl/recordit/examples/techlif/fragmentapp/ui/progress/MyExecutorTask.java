package pl.recordit.examples.techlif.fragmentapp.ui.progress;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

public class MyExecutorTask implements Runnable{
    private final ProgressBar progressBar;
    private final Handler handler;

    public MyExecutorTask(ProgressBar progressBar, Handler handler) {
        this.progressBar = progressBar;
        this.handler = handler;
    }

    @Override
    public void run() {
        for(int i = 0; i < 500; i++){
            if (Thread.currentThread().isInterrupted()){
                Log.i("APP", "Task canceled");
                return;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (i % 5 == 0) {
                publishProgress(i / 5);
            }
        }
        Log.i("APP", "Task succeed");
    }

    private void publishProgress(int i){
        handler.post(() -> progressBar.setProgress(i));
    }
}
