package vn.edu.poly.demogps;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvLocation = findViewById(R.id.tvLocation);
        FloatingActionButton fab = findViewById(R.id.fab);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isInternetConnected();

                if (isGPSEnable()) {
                    getLocation();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("GPS is not enable!!!");
                    builder.show();
                }
            }
        });
    }

    public void getLocation() {

        LocationManager locationManager =
                (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                tvLocation.setText(location.getLongitude() + "," + location.getLatitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 999);
            return;
        }

        // truy cap cache cua gps
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            TextView tvLastKnownLocation = findViewById(R.id.tvLastKnownLocation);
            tvLastKnownLocation.setText(location.getLongitude() + "," + location.getLatitude());
        }


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1, 1, locationListener);

    }


    public boolean isGPSEnable() {

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return isGPSEnable;
    }


    public void isInternetConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifi = networkInfo.isConnected();

        networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isMobile = networkInfo.isConnected();

        if (isWifi) Toast.makeText
                (this, "Wifi is connected!!!", Toast.LENGTH_LONG).show();

        else if (isMobile) Toast.makeText
                (this, "3G is connected!!!", Toast.LENGTH_LONG).show();

        else Toast.makeText
                    (this, "Nothings!!!!", Toast.LENGTH_LONG).show();

    }

}
