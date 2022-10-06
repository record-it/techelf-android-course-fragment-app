package pl.recordit.examples.techlif.fragmentapp.ui.sensors;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.recordit.examples.techlif.fragmentapp.R;
//TODO Zdefiniuj własną klasę implementującą interfejs SensorEventListener
//TODO W metodzie onResume zarejestruj obiekt słuchacza zdefiniowanej wyżej klasy
//TODO w metodzie onPause usuń słuchacza metodą unregisterListener wskazują obiekt utworzony w punkcie powyżej
public class SensorFragment extends Fragment {
    public static SensorFragment newInstance() {
        return new SensorFragment();
    }
    SensorManager manager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor tempSens = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if (tempSens == null){
            Log.i("APP", "Temperature sensor is not present!");
            return;
        }
        Log.i("APP", "Temperature sensor is present!");
        boolean result = manager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.i("APP", "Value changed " + sensorEvent.values[0]);
                Log.i("APP", "Accuracy " + sensorEvent.accuracy);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                switch (i){
                    case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                        Log.i("APP", "Accuracy changed to HIGH");
                        break;
                    case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                        Log.i("APP", "Accuracy changed to MEDIUM");
                        break;
                    case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                        Log.i("APP", "Accuracy changed to LOW");
                        break;
                    case SensorManager.SENSOR_STATUS_UNRELIABLE:
                        Log.i("APP", "Accuracy changed to UNRELIABLE");
                        break;
                }
            }
        }, tempSens, 100_000);
        Log.i("APP", "Registering result " + result);
    }

}