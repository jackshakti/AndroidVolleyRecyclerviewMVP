package shakti.volleymvprecyclerview.library.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shakti.volleymvprecyclerview.library.utils.Utils;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class MultipartRequest {

    private Context context;
    private MultipartListener mServiceListener;
    private VolleyMultipartRequest multipartRequest;

    public interface MultipartListener {
        void onCompleteListener(String response);

        void onErrorListener();
    }

    public MultipartRequest(Context mContext) {
        this.context = mContext;
    }

    public void cancelRequest() {
        if (multipartRequest != null) {
            multipartRequest.cancel();
        }
    }

    public void makeMultipartRequest(final String url, int method, final HashMap<String, String> param, final String fileName, final Bitmap bitmap, MultipartListener listener) {

        Utils.print("url", url);
        Utils.print("param", param.toString());
        Utils.print("fileName", fileName);
        Utils.print("bitmap", bitmap.toString());

        this.mServiceListener = listener;
        multipartRequest = new VolleyMultipartRequest(method, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Utils.log("response", resultResponse);

                mServiceListener.onCompleteListener(resultResponse);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        errorMessage = "Request timeout";
                    } else if (error.getClass().equals(NoConnectionError.class)) {
                        errorMessage = "Failed to connect server";
                    }
                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                        String status = response.getString("status");
                        String message = response.getString("message");

                        if (networkResponse.statusCode == 404) {
                            errorMessage = "Resource not found";
                        } else if (networkResponse.statusCode == 401) {
                            errorMessage = message + " Please login again";
                        } else if (networkResponse.statusCode == 400) {
                            errorMessage = message + " Check your inputs";
                        } else if (networkResponse.statusCode == 500) {
                            errorMessage = message + " Something is getting wrong";
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.i("Error", errorMessage);
                error.printStackTrace();

                mServiceListener.onErrorListener();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return param;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put(fileName, new DataPart(AppHelper.setFileName() + ".jpg", AppHelper.getFileDataFromDrawable(bitmap), "image/jpeg"));

                return params;
            }

        };


        //to avoid repeat request Multiple Time
        DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        multipartRequest.setRetryPolicy(retryPolicy);
        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        multipartRequest.setShouldCache(false);
        AppController.getInstance().addToRequestQueue(multipartRequest);

    }

}