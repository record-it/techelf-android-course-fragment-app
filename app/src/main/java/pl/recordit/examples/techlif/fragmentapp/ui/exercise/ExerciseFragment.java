package pl.recordit.examples.techlif.fragmentapp.ui.exercise;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import pl.recordit.examples.techlif.fragmentapp.R;
//TODO Stwórz fragment z polem EditText multiline i przyciskiem Download
//TODO który pobierz z jakieś URI dokument JSON np. https://jsonplaceholder.typicode.com/posts/1
//TODO należy dodać permission INTERNET
public class ExerciseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    public void dowload() throws IOException {
        URL uri = new URL("https://jsonplaceholder.typicode.com/posts/1");
        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
        connection.setRequestProperty("User-agent", "android app");
        if (connection.getResponseCode() == 200){
            List<String> strings = new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().collect(Collectors.toList());
        }
    }
}