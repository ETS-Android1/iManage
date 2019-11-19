package com.example.nahimana.imanage.helpers;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestHandler {
    private static Context mCtx;
    private RequestQueue mRequestQueue;
    private static RequestHandler requestInstance;

    private RequestHandler (Context context){
        mCtx = context;
    }
    public static synchronized   RequestHandler getInstance(Context context){
        if(requestInstance == null){
            requestInstance = new RequestHandler(context);
        }
        return requestInstance;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
