package shakti.volleymvprecyclerview.appdetail.presenter;

import android.content.Context;

import java.util.HashMap;

import shakti.volleymvprecyclerview.appdetail.model.AppDetailModel;
import shakti.volleymvprecyclerview.appdetail.model.IAppDetailModel;
import shakti.volleymvprecyclerview.appdetail.view.AppDetail;
import shakti.volleymvprecyclerview.appdetail.view.IAppDetailModelView;

/**
 * Created by Sakthivel on 20-02-2020.
 */
public class AppDetailPresenter implements IAppDetailPresenter {

    /**
     * AppDetail View interface instance in order to access the override methods
     */
    private IAppDetailModelView iAppDetailView;
    /**
     * AppDetail Model interface instance in order to access the override methods
     */
    private IAppDetailModel iAppDetailModel;

    public AppDetailPresenter(AppDetail mIAppDetailView) {
        iAppDetailView = mIAppDetailView;
        initialize();

    }

    private void initialize() {
        iAppDetailModel = new AppDetailModel(this);
    }

    @Override
    public void validateAppDetailRequest(Context context, HashMap<String, String> mParams) {
        iAppDetailModel.validateAppDetailRequest(context, mParams);
    }

    @Override
    public void onResponseSuccess(String message, String response) {
        iAppDetailView.onAppDetailViewResponseSuccess(message, response);
    }

    @Override
    public void onResponseFailed(String message) {
        iAppDetailView.onAppDetailViewResponseFailed(message);
    }

    @Override
    public void onAppDetailPresenterDestroy() {
        iAppDetailView = null;
        iAppDetailModel.onAppDetailModelDestroy();
    }
}
