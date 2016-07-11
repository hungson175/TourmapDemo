package mobile.dtd.vn.tourmapdemo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng CENTER = new LatLng(11.942722, 108.436938);
    private ArrayList<PlaceItem> listPlaces;

    public static final String TAG = "SONPH.TOURMAP.DEMO";
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String username;
    private String photoUrl;
    private DatabaseReference firebaseDbRef;
    private List<PlaceItem> places;
    private HashMap<String,PlaceItem> mapMarkerId2Place = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initUser();
        firebaseDbRef = FirebaseDatabase.getInstance().getReference();
    }

    private void populateMap(DataSnapshot dataSnapshot, String s) {

    }

    private void initUser() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "onConnectionFailed:" + connectionResult);
                        Toast.makeText(MapsActivity.this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if ( firebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            username = firebaseUser.getDisplayName();
            if ( firebaseUser.getPhotoUrl() != null) {
                photoUrl = firebaseUser.getPhotoUrl().toString();
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        this.googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        firebaseDbRef.child("places").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPlaces = new ArrayList<>();
                for(DataSnapshot cateS : dataSnapshot.getChildren()) {
                    for(DataSnapshot ps : cateS.getChildren()) {
                        PlaceItem p = ps.getValue(PlaceItem.class);
                        listPlaces.add(p);
                    }
                }
                setMapMarkers(listPlaces);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = Helpers.inflate(getApplicationContext(),R.layout.info_window);
                PlaceItem place = mapMarkerId2Place.get(marker.getId());
                String imgURL = place.getThumbURL();
                ImageView ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
                Picasso.with(getApplicationContext()).load(imgURL).placeholder(R.drawable.flat_camera_icon).into(ivThumb);
//                ivThumb.setImageUrl(imgURL,VolleySingleton.getInstance(getApplicationContext()).getImageLoader());
                TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
                tvTitle.setText(place.getName());
                RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
                ratingBar.setRating((float) place.getRating());
                return v;
            }
        });
    }

    private void setMapMarkers(List<PlaceItem> places) {
        googleMap.clear();
        mapMarkerId2Place.clear();
        for (PlaceItem place : places) {
            LatLng pos = new LatLng(place.getLat(),place.getLongt());
            MarkerOptions options = new MarkerOptions()
                    .position(pos)
                    .title(place.getName());
            Marker marker = this.googleMap.addMarker(options);
            mapMarkerId2Place.put(marker.getId(),place);
        }
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(CENTER));
    }
}
