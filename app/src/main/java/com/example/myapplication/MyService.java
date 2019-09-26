package com.example.myapplication;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

public class MyService extends Service implements SensorEventListener {
    SensorManager sm;
    Sensor ls;
    AudioManager am;
    Sensor ps;                  // --->(1)
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    /*press ctrl+o and create the onCreate Method
     *create a sensor manager,sensor light,audiomanager,sensor prox (see (1))
     *initilize sensormanager,light sensor and proximity sensor (see (2))
     * check if device contains light sensor
     * write else
     * implement sensor (see the class definition) (implement the methods)
     * check for light density and proximity value and put phone to silent or outdoor
     * create a new sensor event listener (listener3) for the proximity sensor (see (3))
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Service is started",Toast.LENGTH_SHORT).show();
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        ls =sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        ps=sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);      //--->(2)

        if(ls==null)
        {
            Toast.makeText(this,"Light Sensor is absent",Toast.LENGTH_SHORT).show();
        }
        else
        {
            am=(AudioManager)getSystemService(AUDIO_SERVICE); //am is an object of type audio manager
            sm.registerListener(this,ls,sm.SENSOR_DELAY_FASTEST);
            sm.registerListener(listener3,ps,sm.SENSOR_DELAY_FASTEST);
            Notification notification = new Notification.Builder(this)
                    .setContentText("Service running")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("Service")
                    .build();
            NotificationManagerCompat nc = NotificationManagerCompat.from(this);
            nc.notify(1,notification);
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float optical_density[] =event.values;
        if(optical_density[0]<1.0f)  //if light is less than one then set phone to silent
        {
            if (am.getRingerMode()!=AudioManager.RINGER_MODE_SILENT)
                am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
        else  //else set phone to outdoor
        {
            if(am.getRingerMode()!=AudioManager.RINGER_MODE_NORMAL)
                am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    SensorEventListener listener3 = new SensorEventListener() {    //---> (3)
        @Override
        public void onSensorChanged(SensorEvent event) {
            Toast.makeText(MyService.this,""+event.values[0],Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}

