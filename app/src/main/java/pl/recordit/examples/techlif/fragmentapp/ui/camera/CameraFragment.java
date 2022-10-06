package pl.recordit.examples.techlif.fragmentapp.ui.camera;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.recordit.examples.techlif.fragmentapp.R;

public class CameraFragment extends Fragment {
    ImageCapture imageCapture;
    VideoCapture<Recorder> videoCapture;
    Recording recording;
    Button imageCaptureButton;
    Button videoCaptureButton;
    final static List<String> REQUIRED_PERMISSIONS = buildRequiredPermissions();
    final ExecutorService executor = Executors.newSingleThreadExecutor();
    final static int REQUEST_CODE_PERMISSION = 10;
    PreviewView view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view = view.findViewById(R.id.preview);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (imageCapture == null) {
            imageCapture = new ImageCapture.Builder().build();
        }
        if (allPermissionsGranted()) {
            startCamera();
        } else {
            ActivityCompat.requestPermissions(this.getActivity(), REQUIRED_PERMISSIONS.toArray(new String[]{}), REQUEST_CODE_PERMISSION);
        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(getContext());
        future.addListener(() -> {
                    try {
                        ProcessCameraProvider provider = future.get();
                        Preview preview = new Preview.Builder().build();
                        preview.setSurfaceProvider(view.getSurfaceProvider());
                        CameraSelector selector = CameraSelector.DEFAULT_FRONT_CAMERA;
                        provider.unbindAll();
                        provider.bindToLifecycle(this, selector, preview, imageCapture);

                    } catch (ExecutionException | InterruptedException e) {
                        Log.e("APP", "Bindiing failed: " + e.getMessage());
                    }

                },
                ContextCompat.getMainExecutor(getContext()));
    }

    private static List<String> buildRequiredPermissions() {
        List<String> permissions = new ArrayList<>(List.of(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
        ));
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        return permissions;
    }

    private boolean allPermissionsGranted() {
        return REQUIRED_PERMISSIONS
                .stream()
                .allMatch(p -> PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this.getActivity().getBaseContext(), p));
    }

}