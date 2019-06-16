package com.example.user.test;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {

    private final int PERMISO_GPS = 1;

    private boolean tienePermiso = false;

    private GoogleMap mMap;

    private LocationManager locationManager;
    private int LOCATION_REFRESH_TIME = 10000;
    private int LOCATION_REFRESH_DISTANCE = 10;
    //ImageView image;

    private Marker myMarker;
    private Marker myMarker_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //image = findViewById(R.id.imagen_preview);
        /**Obtendo la versión de android del Dispositivo*/
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        /**Valido que la versión antual sea igual a superior a M*/
        if(currentapiVersion >= Build.VERSION_CODES.M) {
            validarUSOUbicacion();
        } else {
            tienePermiso = true;
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**Agregamos esta Etiqueta para evitar que se muestre error en la codificación
     * que os metodos checkSelfPermission, shouldShowRequestPermissionRationale,
     * requestPermissions. Fueron agregados a partir de Api 23 (6.0)*/
    @TargetApi(Build.VERSION_CODES.M)
    private void validarUSOUbicacion() {
        /**Obtenemos el estado actual del Permiso*/
        final int iGPS = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        /**Comprobamos si el usuario OTORGO el Permiso*/
        if (iGPS != PackageManager.PERMISSION_GRANTED) {
            /**Validamos si debemos mostrar el UI de petición del Permiso.*/
            final boolean pGPS = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION);
            if (pGPS) {
                /**Este método muestra true si la app solicita el permiso anteriormente y el usuario rechaza la solicitud.*/
            } else {
                /**Si el usuario rechaza la solicitud de permiso en el pasado y selecciona la opción Don't ask again (No
                 * volver a preguntar) en el diálogo de solicitud de permiso del sistema, el método muestra false. También
                 * muestra false si una política de dispositivo prohíbe que la app tenga ese permiso.
                 */
            }
            /**Solicitamos el Permiso necesario*/
            String[] permiso = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
            /**PERMISO_GPS: es una constante global declarada en la parte superior*/
            requestPermissions(permiso, PERMISO_GPS);
        } else {
            /**Si entra en esta sección es porque ya tiene el Permiso*/
            tienePermiso = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            /**Recuerda la Constante Global asignada a la petición
             * será la que nos permita reconocer la respuesta.*/
            case PERMISO_GPS:
                /**Validamos si el Respuesta no es vacia y si se OTORGO el permiso*/
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /**Asignamos valor a la variable global*/
                    tienePermiso = true;
                } else {
                    /**Mostramos un mensaje al usuario*/
                    Toast.makeText(getApplicationContext(),"No podrá hacer uso de la su GPS",Toast.LENGTH_LONG).show();
                    /**Asignamos valor a la variable global*/
                    tienePermiso = false;
                }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        /**Llamado cuando la Localización cambia */
        LatLng ubicacion = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(ubicacion));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void obtenerUbicacion () {
        try {
            /**Instancia al Sistema, solicitando el servicio de Localización*/
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            /**El método requestLocationUpdate, donde le pasamos el Proveedor (GPS en este caso)
             * El tiempo en milisegundos cuando debe actualizar, La distancia en metros cuando
             * debe cambiar y la interfaz LocationListener this ya esta clase la implementa. */
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, this);
        }catch (SecurityException e) {
            Toast.makeText(getBaseContext(), "Error mostrando la unicacion", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        /**Ya está listo el mapa podemos usar */
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        /**Declaramos una Ubicación*/
        LatLng ubicacion = new LatLng(-12.122294, -77.028323);
        /**Agregamos un marcador en el Mapa pasandole la unicación*/
        myMarker = mMap.addMarker(new MarkerOptions().position(ubicacion).title("Chan Chan").snippet("Chan Chan, la ciudad de barro más grande del mundo").icon(BitmapDescriptorFactory.fromResource(R.drawable.point)));
        /**Mueve la Camará para que se posicione en una Ubicación, además detenerminamos un Zoom*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15));


        try{
            /**Habilitadmos que se muestre nuestra el ícono de Ubicación
             * en la Parte superior del Mapa*/
            mMap.setMyLocationEnabled(true);
            /**Llamamos al método para obtener la ubicación actual*/
            obtenerUbicacion();

        }catch (SecurityException e) {
            Toast.makeText(getBaseContext(), "Error activando Ubicación", Toast.LENGTH_LONG).show();
        }
    }




    static final int REQUEST_IMAGE_CAPTURE = 1;

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Toast.makeText(this,"Tomamos la foto " + imageBitmap.getWidth() + " X " + imageBitmap.getHeight(),Toast.LENGTH_LONG).show();
            //image.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.equals(myMarker)){
            Toast.makeText(this,myMarker.getTitle(),Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this,DetailActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
