package com.github.javierugarte;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

/**
 * Copyright 2016 Javier González
 * All right reserved.
 */
public class ContributionsRequest {

    private static final String URL = "https://github.com/users/%s/contributions";

    private final Context mContext;

    public ContributionsRequest(Context context) {
        this.mContext = context;
    }

    public void launchRequest(String username, final OnContributionsRequestListener listener) {

        String url = String.format(URL, username);

        StringRequest strReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                ContributionsProvider provider = new ContributionsProvider();
                List<ContributionsDay> contributions = provider.getContributions(response);
                listener.onResponse(contributions);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                listener.onError(volleyError);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(strReq);
    }

    public interface OnContributionsRequestListener {

        void onResponse(List<ContributionsDay> contributionsDay);

        void onError(VolleyError error);

    }

}
