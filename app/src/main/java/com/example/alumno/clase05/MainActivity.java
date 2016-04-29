package com.example.alumno.clase05;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    //crear variables estáticas para comunicar los valores
    public static final int TEXTO = 1;
    public static final int IMAGEN = 2;
    private miHilo hilo;
    //private TextView txt;
    private Handler h = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //txt = (TextView) this.findViewById(R.id.miTV);

        //1 string de URL y un booleano en TRUE
        hilo = new miHilo("http://192.168.2.130:8080/android/clase6.xml", true, h);
        hilo.start();

        hilo = new miHilo("http://192.168.2.130:8080/android/koala.png", false, h);
        hilo.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        hilo.interrupt();
    }

    @Override
    public boolean handleMessage(Message msg) {

        if (msg.arg1 == this.TEXTO)
        {
            TextView txt;
            txt = (TextView) this.findViewById(R.id.miTV);
            txt.setText(msg.obj.toString());
        }
        else if (msg.arg1 == this.IMAGEN)
        {
            ImageView iv;
            iv = (ImageView) this.findViewById(R.id.miIV);
            byte[] arraybytes = (byte[]) msg.obj;
            Bitmap bmp = BitmapFactory.decodeByteArray(arraybytes, 0, arraybytes.length);
            iv.setImageBitmap(bmp);
        }
        return false;
    }
}
