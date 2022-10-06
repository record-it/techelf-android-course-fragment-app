package pl.recordit.examples.techlif.fragmentapp.ui.map;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import pl.recordit.examples.techlif.fragmentapp.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private SupportMapFragment mapFragment;
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        Log.i("APP", "Map fragment " + mapFragment);
        this.mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng warsaw = new LatLng(52, 21);
        Marker myMarker = map.addMarker(
                new MarkerOptions()
                        .position(warsaw)
                        .title("Warsaw City")
                        .anchor(0,0)
                        .alpha(0.5f)
                        .snippet("Stolica Polski")
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))

        );
        map.moveCamera(CameraUpdateFactory.newLatLng(warsaw));
    }
}