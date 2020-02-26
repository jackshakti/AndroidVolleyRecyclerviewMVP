package shakti.volleymvprecyclerview.app.model;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import shakti.volleymvprecyclerview.app.presenter.IRecyclerviewPresenter;
import shakti.volleymvprecyclerview.iconstant.IConstant;
import shakti.volleymvprecyclerview.library.utils.NetworkUtil;
import shakti.volleymvprecyclerview.library.utils.Utils;
import shakti.volleymvprecyclerview.library.volley.ServiceRequest;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class RecyclerviewModel implements IRecyclerviewModel {

    /**
     * Recyclerview Presenter interface instance in order to access the override methods
     */
    private IRecyclerviewPresenter iRecyclerviewPresenter;

    private Context mContext;
    private HashMap param;
    private ServiceRequest mRequest;

    public RecyclerviewModel(IRecyclerviewPresenter mIRecyclerviewPresenter) {
        iRecyclerviewPresenter = mIRecyclerviewPresenter;
    }

    @Override
    public void validateRecyclerviewRequest(Context context, HashMap<String, String> mParams) {
        this.mContext = context;
        this.param = mParams;

        if (NetworkUtil.checkNetConnection(mContext)) {
            RecyclerviewRequest(IConstant.BaseUrl);
        } else {
            Utils.noInternetAlert(context);
        }
    }

    private void RecyclerviewRequest(final String Url) {
        Utils.showProgressDialog(mContext);

        mRequest = new ServiceRequest(mContext);
        mRequest.makeServiceRequest(Url, Request.Method.GET, param, new ServiceRequest.ServiceListener() {
            @Override
            public void onCompleteListener(String response) {

                System.out.println("-----------RecyclerviewRequest response---------------"+response);

                String sStatus = "", sMessage = "";
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.length() > 0) {
                        sStatus = object.getString("status");
                        sMessage = object.getString("msg");

                        if (sStatus.equals("1")) {
                            iRecyclerviewPresenter.onRecyclerviewResponseSuccess(sMessage, response);
                        } else {
                            iRecyclerviewPresenter.onRecyclerviewResponseFailed(sMessage);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Utils.dismissProgressDialog();
                }
                Utils.dismissProgressDialog();
            }

            @Override
            public void onErrorListener() {
                Utils.dismissProgressDialog();
            }
        });
    }

    @Override
    public void onRecyclerviewModelDestroy() {
        iRecyclerviewPresenter = null;

    }
}
