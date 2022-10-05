package pl.recordit.examples.techlif.fragmentapp.ui.progress;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

public class MyAsyncTask extends AsyncTask<Void, Integer, String> {
    private final ProgressBar progressBar;
    private volatile boolean isCanceled;
    public MyAsyncTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void cancel() {
        isCanceled = true;
    }

    @Override
    protected String doInBackground(Void... voids) {
        for(int i = 0; i < 500; i++){
            if (isCanceled){
                Log.i("APP", "Task canceled");
                return "Canceled";
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i % 5 == 0) {
                publishProgress(i / 5);
            }
        }
        return "Success";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0], true);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("APP", "End of task with " + s);
    }
}
