package shakti.volleymvprecyclerview.library.volley;

import android.content.Context;
import android.content.pm.PackageManager;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import shakti.volleymvprecyclerview.library.utils.Utils;


/**
 * Created by Sakthivel on 15-12-2019.
 */
public class ServiceRequest {

    private Context context;
    private ServiceListener mServiceListener;
    private StringRequest stringRequest;
    private String TAG = "";
    private String appVersion = "";

    public interface ServiceListener {
        void onCompleteListener(String response);

        void onErrorListener();
    }

    public ServiceRequest(Context mContext) {
        this.context = mContext;
        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /* Method to set tag to cancel request*/
    public void setTagToCancelRequest(String tag) {
        TAG = tag;
    }

    /* Method to cancel request*/
    public void cancelRequests(String tag) {
        AppController.getInstance().cancelPendingRequests(tag);
        if (stringRequest != null) {
            stringRequest.cancel();
        }
    }

    public void makeServiceRequest(final String url, int method, final HashMap<String, String> param, ServiceListener listener) {

        Utils.print("url", url);
        Utils.print("param", param.toString());

        this.mServiceListener = listener;
        stringRequest = new StringRequest(method, url, response -> {

            Utils.log("response", response);
            try {
                mServiceListener.onCompleteListener(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, error -> {
            try {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //Toast.makeText(context, "Network connection is slow.Please try again.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    //Toast.makeText(context, "AuthFailureError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    // Toast.makeText(context, "ServerError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //Toast.makeText(context, "NetworkError", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    //Toast.makeText(context, "ParseError", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
            }
            mServiceListener.onErrorListener();
        }) {
            @Override
            protected Map<String, String> getParams() {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                // since we don't know which of the two underlying network vehicles
                // will Volley use, we have to handle and store session cookies manually

                return super.parseNetworkResponse(response);
            }
        };

        //to avoid repeat request Multiple Time
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(90000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(stringRequest, TAG);
    }


}
