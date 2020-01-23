package ng.riby.androidtest;

import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.security.Permission;
import java.text.DecimalFormat;

import ng.riby.androidtest.database.AppExecutors;
import ng.riby.androidtest.database.Appdatabase;
import ng.riby.androidtest.database.TaskEntry;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class StartStopActivity extends AppCompatActivity {

    Button start;
    Button stop;

    private Appdatabase mDb;
    private FusedLocationProviderClient client;



    double startLong;
    double startLatt;
    double endLong;
    double endLatt;
    double distance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_stop);
        askPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        start = findViewById(R.id.startB);
        stop = findViewById(R.id.stopB);

        stop.setVisibility(View.INVISIBLE);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(StartStopActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    return;
                }

                start.setVisibility(View.INVISIBLE);
                stop.setVisibility(View.VISIBLE);
                client.getLastLocation().addOnSuccessListener(StartStopActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            //TextView textView = findViewById(R.id.textView);
                            startLong = location.getLongitude();
                            startLatt = location.getLatitude();


                        }
                    }
                });
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                client.getLastLocation().addOnSuccessListener(StartStopActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null){
                            endLong = location.getLongitude();
                            endLatt = location.getLatitude();
                        }

                         distance = calculateDistance(startLatt,startLong,endLatt,endLong);


                        final TaskEntry taskEntry = new TaskEntry(startLong,startLatt,endLong,endLatt,distance);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.taskDao().insertTask(taskEntry);
                                finish();
                            }
                        });


                    }
                });
                finish();
            }
        });


        mDb = Appdatabase.getInstance(getApplicationContext());


    }

    private double calculateDistance(double slat, double slon, double elat, double elon) {
        Location loc1 = new Location("");
        loc1.setLatitude(slat);
        loc1.setLongitude(slon);

        Location loc2 = new Location("");
        loc2.setLatitude(elat);
        loc2.setLongitude(elon);

        double distanceInMeters = loc1.distanceTo(loc2);

        return distanceInMeters;
    }


    private void askPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }
}
