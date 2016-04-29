package com.example.alumno.clase05;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by alumno on 28/04/2016.
 */
public class miHilo extends Thread {

    Activity miActivity;
    private Handler h;
    private Boolean stroimg;
    private HttpManager httpManage;
    //public miHilo (Activity miActivity){
    public miHilo (String url, Boolean bool, Handler h){
        this.h = h;
        this.stroimg = bool;
        httpManage = new HttpManager(url);

    }

    @Override
    public void run() {
        //super.run();
        //TextView miTV = (TextView) miActivity.findViewById(R.id.miTV);
        //miTV.setText("Chau mundo");
        Message mensaje = new Message();
        try {
            if(stroimg) {
                mensaje.arg1 = MainActivity.TEXTO;
                mensaje.obj = httpManage.getStrDataByGET();
            }
            else{
                mensaje.arg1 = MainActivity.IMAGEN;
                mensaje.obj = httpManage.getBytesDataByGET();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        h.sendMessage(mensaje);

    }
}
