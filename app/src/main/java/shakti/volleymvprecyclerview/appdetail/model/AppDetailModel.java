package shakti.volleymvprecyclerview.appdetail.model;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import shakti.volleymvprecyclerview.appdetail.presenter.IAppDetailPresenter;
import shakti.volleymvprecyclerview.iconstant.IConstant;
import shakti.volleymvprecyclerview.library.utils.NetworkUtil;
import shakti.volleymvprecyclerview.library.utils.Utils;
import shakti.volleymvprecyclerview.library.volley.ServiceRequest;

/**
 * Created by Sakthivel on 20-02-2020.
 */
public class AppDetailModel implements IAppDetailModel{
    /**
     * App Detail Presenter interface instance in order to access the override methods
     */
    private IAppDetailPresenter iAppDetailPresenter;

    private Context mContext;
    private HashMap<String, String> params;

    public AppDetailModel(IAppDetailPresenter mIAppDetailPresenter) {
        iAppDetailPresenter = mIAppDetailPresenter;
    }

    @Override
    public void validateAppDetailRequest(Context context, HashMap<String, String> mParams) {
        this.mContext = context;
        this.params = mParams;

        if (NetworkUtil.checkNetConnection(mContext)) {
            AppDetailRequest(IConstant.BaseUrl);
        } else {
            Utils.noInternetAlert(context);
        }
    }

    private void AppDetailRequest(final String Url) {
        Utils.showProgressDialog(mContext);

        ServiceRequest mRequest = new ServiceRequest(mContext);
        mRequest.makeServiceRequest(Url, Request.Method.POST, params, new ServiceRequest.ServiceListener() {
            @Override
            public void onCompleteListener(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.length() > 0) {
                        String sStatus = object.getString("status");
                        String  sMessage = object.getString("msg");

                        if (sStatus.equals("1")) {
                            iAppDetailPresenter.onResponseSuccess(sMessage, response);
                        } else {
                            iAppDetailPresenter.onResponseFailed(sMessage);
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
    public void onAppDetailModelDestroy() {
        iAppDetailPresenter = null;
    }

}
