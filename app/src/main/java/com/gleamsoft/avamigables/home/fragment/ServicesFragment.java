package com.gleamsoft.avamigables.home.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gleamsoft.avamigables.R;
import com.gleamsoft.avamigables.home.Util.Constants;
import com.gleamsoft.avamigables.home.Util.LocationAddress;
import com.gleamsoft.avamigables.home.Util.geoLocation;
import com.gleamsoft.avamigables.home.activity.DetectedActivitiesIntentService;
import com.gleamsoft.avamigables.home.adapter.PautaAdapter;
import com.gleamsoft.avamigables.home.adapter.metroPlusAdapter;
import com.gleamsoft.avamigables.home.model.Info;
import com.gleamsoft.avamigables.home.model.Pautas;
import com.gleamsoft.avamigables.home.model.metroPlus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks,
                GoogleApiClient.OnConnectionFailedListener,
                LocationListener, ResultCallback<Status> {

private static final String TAG = ServicesFragment.class.getSimpleName();

private static final String LOCATION_KEY = "location-key";
private static final String ACTIVITY_KEY = "activity-key";

// Location API
private GoogleApiClient mGoogleApiClient;
private LocationRequest mLocationRequest;
private LocationSettingsRequest mLocationSettingsRequest;
private Location mLastLocation;

// Activity Recognition API
private ActivityDetectionBroadcastReceiver mBroadcastReceiver;
@DrawableRes
private int mImageResource = R.drawable.ic_question;

// UI


// Códigos de petición
public static final int REQUEST_LOCATION = 1;
public static final int REQUEST_CHECK_SETTINGS = 20;


private RecyclerView mRecyclerView;

private PautaAdapter pautaAdapter;
private List<Info> profesionalList;
TextView textViewChildName;
private TextView mLatitude;
private  String address = "";
//metroplus

private RecyclerView mRecyclerViewm;

private metroPlusAdapter pautaAdapterm;
private List<metroPlus> profesionalListm;
private RecyclerView.LayoutManager mLayoutManager;

public ServicesFragment() {
    // Required empty public constructor
}


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_services, container, false);
    
    // Referencias UI
    
    textViewChildName = (TextView) view.findViewById(R.id.textViewChild);
    mLatitude = (TextView) view.findViewById(R.id.tv_latitude);
    profesionalList = new ArrayList<Info>();
    
    mRecyclerView = (RecyclerView) view.findViewById(R.id.products_recycler_view);
    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
    mRecyclerView.setLayoutManager(layoutManager);
    pautaAdapter = new PautaAdapter(profesionalList, getActivity());
    mRecyclerView.setAdapter(pautaAdapter);
    
    // pautaAdapter.setClickListener(getContext());
    
    //metro plus
    
    profesionalListm = new ArrayList<metroPlus>();
    mRecyclerViewm = (RecyclerView) view.findViewById(R.id.products_recycler_tra);
    mLayoutManager = new LinearLayoutManager(getContext());
    mRecyclerViewm.setLayoutManager(mLayoutManager);
    pautaAdapterm = new metroPlusAdapter(profesionalListm, getActivity());
    mRecyclerViewm.setAdapter(pautaAdapterm);
    
    // Establecer punto de entrada para la API de ubicación
    buildGoogleApiClient();
    
    // Crear configuración de peticiones
    createLocationRequest();
    
    // Crear opciones de peticiones
    buildLocationSettingsRequest();
    
    // Verificar ajustes de ubicación actuales
    checkLocationSettings();
    
    mBroadcastReceiver = new ActivityDetectionBroadcastReceiver();
    
    updateValuesFromBundle(savedInstanceState);

    getdata();
    getmetroplus();
    return view;
}


@Override
public void onPause() {
    super.onPause();
    if (mGoogleApiClient.isConnected()) {
        stopLocationUpdates();
        stopActivityUpdates();
    }
    
    LocalBroadcastManager.getInstance(getActivity())
            .unregisterReceiver(mBroadcastReceiver);
}

@Override
public void onResume() {
    super.onResume();
    if (mGoogleApiClient.isConnected()) {
        startLocationUpdates();
        startActivityUpdates();
    }
    
    IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
    LocalBroadcastManager.getInstance(getActivity())
            .registerReceiver(mBroadcastReceiver, intentFilter);
}


@Override
public void onSaveInstanceState(Bundle outState) {
    // Protegemos la ubicación actual antes del cambio de configuración
    outState.putParcelable(LOCATION_KEY, mLastLocation);
    outState.putInt(ACTIVITY_KEY, mImageResource);
    super.onSaveInstanceState(outState);
}

@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
        case REQUEST_CHECK_SETTINGS:
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.d(TAG, "El usuario permitió el cambio de ajustes de ubicación.");
                    processLastLocation();
                    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    Log.d(TAG, "El usuario no permitió el cambio de ajustes de ubicación");
                    break;
            }
            break;
    }
}

public void onRequestPermissionsResult(int requestCode,
                                       String[] permissions,
                                       int[] grantResults) {
    if (requestCode == REQUEST_LOCATION) {
        if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            
            startLocationUpdates();
            
        } else {
            Toast.makeText(getActivity(), "Permisos no otorgados", Toast.LENGTH_LONG).show();
        }
    }
}

private synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                               .addConnectionCallbacks(this)
                               .addOnConnectionFailedListener(this)
                               .addApi(LocationServices.API)
                               .addApi(ActivityRecognition.API)
                               .enableAutoManage(getActivity(), this)
                               .build();
}

private void createLocationRequest() {
    mLocationRequest = new LocationRequest()
                               .setInterval(Constants.UPDATE_INTERVAL)
                               .setFastestInterval(Constants.UPDATE_FASTEST_INTERVAL)
                               .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
}

private void buildLocationSettingsRequest() {
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    builder.addLocationRequest(mLocationRequest)
            .setAlwaysShow(true);
    mLocationSettingsRequest = builder.build();
}

private void checkLocationSettings() {
    PendingResult<LocationSettingsResult> result =
            LocationServices.SettingsApi.checkLocationSettings(
                    mGoogleApiClient, mLocationSettingsRequest
            );
    
    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
        @Override
        public void onResult(@NonNull LocationSettingsResult result) {
            Status status = result.getStatus();
            
            switch (status.getStatusCode()) {
                case LocationSettingsStatusCodes.SUCCESS:
                    Log.d(TAG, "Los ajustes de ubicación satisfacen la configuración.");
                    startLocationUpdates();
                    break;
                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                    try {
                        Log.d(TAG, "Los ajustes de ubicación no satisfacen la configuración. " +
                                           "Se mostrará un diálogo de ayuda.");
                        status.startResolutionForResult(
                                getActivity(),
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException e) {
                        Log.d(TAG, "El Intent del diálogo no funcionó.");
                        // Sin operaciones
                    }
                    break;
                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                    Log.d(TAG, "Los ajustes de ubicación no son apropiados.");
                    break;
                
            }
        }
    });
}

private void updateValuesFromBundle(Bundle savedInstanceState) {
    if (savedInstanceState != null) {
        if (savedInstanceState.containsKey(LOCATION_KEY)) {
            mLastLocation = savedInstanceState.getParcelable(LOCATION_KEY);
            
            updateLocationUI();
        }
        
        if (savedInstanceState.containsKey(ACTIVITY_KEY)) {
            mImageResource = savedInstanceState.getInt(ACTIVITY_KEY);
            
            updateRecognitionUI();
        }
        
        
    }
}

private void updateLocationUI() {
    
    LatLng cord = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    LocationAddress locationAddress = new LocationAddress();
    locationAddress.getAddressFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(),
            getActivity(), new GeocoderHandler());
    //getData(cord);
    
    //getLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),200);
}

private void updateRecognitionUI() {
    //mDectectedActivityIcon.setImageResource(mImageResource);
}

private void stopActivityUpdates() {
    ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(
            mGoogleApiClient,
            getActivityDetectionPendingIntent()
    ).setResultCallback(this);
}

private void stopLocationUpdates() {
    LocationServices.FusedLocationApi
            .removeLocationUpdates(mGoogleApiClient, this);
}

private PendingIntent getActivityDetectionPendingIntent() {
    Intent intent = new Intent(getActivity(), DetectedActivitiesIntentService.class);
    return PendingIntent.getService(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
}

private void getLastLocation() {
    if (isLocationPermissionGranted()) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    } else {
        manageDeniedPermission();
    }
}

private void processLastLocation() {
    getLastLocation();
    if (mLastLocation != null) {
        updateLocationUI();
    }
}

private void startActivityUpdates() {
    ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(
            mGoogleApiClient,
            Constants.ACTIVITY_RECOGNITION_INTERVAL,
            getActivityDetectionPendingIntent()
    ).setResultCallback(this);
}

private void startLocationUpdates() {
    if (isLocationPermissionGranted()) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    } else {
        manageDeniedPermission();
    }
}

private void manageDeniedPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION)) {
        // Aquí muestras confirmación explicativa al usuario
        // por si rechazó los permisos anteriormente
    } else {
        ActivityCompat.requestPermissions(
                getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION);
    }
}

private boolean isLocationPermissionGranted() {
    int permission = ActivityCompat.checkSelfPermission(
            getActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION);
    return permission == PackageManager.PERMISSION_GRANTED;
}

@Override
public void onConnected(@Nullable Bundle bundle) {
    
    // Obtenemos la última ubicación al ser la primera vez
    processLastLocation();
    // Iniciamos las actualizaciones de ubicación
    startLocationUpdates();
    // Y también las de reconocimiento de actividad
    startActivityUpdates();
}

@Override
public void onConnectionSuspended(int i) {
    Log.d(TAG, "Conexión suspendida");
    mGoogleApiClient.connect();
}

@Override
public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    Toast.makeText(
            getActivity(),
            "Error de conexión con el código:" + connectionResult.getErrorCode(),
            Toast.LENGTH_LONG)
            .show();
    
}

@Override
public void onLocationChanged(Location location) {
    Log.d(TAG, String.format("Nueva ubicación: (%s, %s)",
            location.getLatitude(), location.getLongitude()));
    mLastLocation = location;
    updateLocationUI();
}


@Override
public void onResult(@NonNull Status status) {
    if (status.isSuccess()) {
        Log.d(TAG, "Detección de actividad iniciada");
        
    } else {
        Log.e(TAG, "Error al iniciar/remover la detección de actividad: "
                           + status.getStatusMessage());
    }
}

public class ActivityDetectionBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int type = intent.getIntExtra(Constants.ACTIVITY_KEY, -1);
        
        mImageResource = Constants.getActivityIcon(type);
        updateRecognitionUI();
    }
    
}

private class GeocoderHandler extends Handler {
    @Override
    public void handleMessage(Message message) {
        String locationAddress;
        switch (message.what) {
            case 1:
                Bundle bundle = message.getData();
                locationAddress = bundle.getString("address");
                break;
            default:
                locationAddress = null;
        }
    
        mLatitude.setText(locationAddress);
        address = locationAddress;
         
    }
}

private void getData(LatLng cord) {
    final ParseGeoPoint point = new ParseGeoPoint(cord.latitude, cord.longitude);
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Punto");
    query.orderByAscending("location");
    query.setLimit(2);
    query.whereWithinKilometers("location", point, 1);
    query.findInBackground(new FindCallback<ParseObject>() {
        @Override
        public void done(List<ParseObject> objects, ParseException e) {
            if (e == null) {
                int count = objects.size();
                Log.d("salio datos", objects.size() + "");
                if (count > 0) {
                    profesionalList.clear();
                    
                    for (ParseObject post : objects) {
                        ParseGeoPoint geoPoint = post.getParseGeoPoint("location");
                        LatLng cord = new LatLng(point.getLatitude(), point.getLongitude());
                        LatLng cord2 = new LatLng(geoPoint.getLatitude(), geoPoint.getLongitude());
                        double distance = SphericalUtil.computeDistanceBetween(cord, cord2);
                        get(post, distance);
                        
                    }
    
                } else {
                    textViewChildName.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
    
                }
            } else {
                Log.d("Error!", e.getMessage());
            }
            
        }
        
    });
    
}

private void get(ParseObject punto, final double distance) {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Pauta");
    query.whereEqualTo("punto", punto);
    query.include("punto");
    query.findInBackground(new FindCallback<ParseObject>() {
        public void done(List<ParseObject> scoreList, ParseException e) {
            
            if (e == null) {
                int contador = 1;
                for (ParseObject comment : scoreList) {
                    if (contador < 2) {
                        ParseObject post = comment.getParseObject("punto");
                        // This does not require a network access.
                        ParseFile thumbnail = comment.getParseFile("foto");
                        Pautas pauta = new Pautas("", post.getObjectId(), comment.getObjectId(),
                                                         post.getString("nombre"), thumbnail,
                                                         post.getString("direccion"),
                                                         comment.getString("titulo"),
                                                         comment.getString("mensaje"),
                                                         comment.getBoolean("estado"),
                                                         geoLocation.formatNumber(distance));
    
                       // profesionalList.add(pauta);
    
                    }
                    contador++;
                    
                    
                }
                pautaAdapter.notifyDataSetChanged();
            } else {
                Log.d("score", "Error: " + e.getMessage());
            }
        }
    });
    
}
private void getdata(){
    Info info = new Info(1,"Producido",0);
    profesionalList.add(info);
    info = new Info(1,"Acumulado",200);
    profesionalList.add(info);
    pautaAdapter.notifyDataSetChanged();
}

private void getmetroplus() {

    String[] array = getActivity().getResources().getStringArray(R.array.bus);
    String randomStr = array[new Random().nextInt(array.length)];

    metroPlus metro = new metroPlus("1",
            "em-"+String.valueOf(1),
            1
            , "No has exagerado mucho con  la motocicleta",
            "Tienes derecho a 500 gramos",
            false,
            1,
            2,
            "Agradecemos que contribuyas con un mejor uso del transporte Público");
    profesionalListm.add(metro);
    metro = new metroPlus("1",
            "em-"+String.valueOf(1),
            1
            , "Has hecho uso del transporte Público",
            "Tienes derecho a 500 gramos",
            false,
            1,
            2,
            "Agradecemos que contribuyas con un mejor uso del transporte Público");
    profesionalListm.add(metro);
    metro = new metroPlus("1",
            "em-"+String.valueOf(1),
            1
            , "Has hecho uso de la bicicleta",
            "Tienes derecho a 500 gramos",
            false,
            1,
            2,
            randomStr);
    profesionalListm.add(metro);
    metro = new metroPlus("1",
            "em-"+String.valueOf(1),
            1
            , "Has dejado el carro en casa",
            "Tienes derecho a 1000 gramos",
            false,
            1,
            2,
            "Agradecemos que contribuyas con un mejor uso del transporte");
    profesionalListm.add(metro);



        pautaAdapterm.notifyDataSetChanged();


    

}
}

 
