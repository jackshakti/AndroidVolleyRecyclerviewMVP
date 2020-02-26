package shakti.volleymvprecyclerview.app.presenter;

import android.content.Context;

import java.util.HashMap;

import shakti.volleymvprecyclerview.app.model.IRecyclerviewModel;
import shakti.volleymvprecyclerview.app.model.RecyclerviewModel;
import shakti.volleymvprecyclerview.app.view.IRecyclerviewView;

/**
 * Created by Sakthivel on 15-12-2019.
 */
public class RecyclerviewPresenter implements IRecyclerviewPresenter {

    /**
     * Recyclerview View interface instance in order to access the override methods
     */
    private IRecyclerviewView iRecyclerviewView;

    /**
     * Recyclerview Model interface instance in order to access the override methods
     */
    private IRecyclerviewModel iRecyclerviewModel;

    public RecyclerviewPresenter(IRecyclerviewView mIRecyclerviewView){
        iRecyclerviewView = mIRecyclerviewView;
        initialize();
    }

    private void initialize(){
        iRecyclerviewModel = new RecyclerviewModel(this);
    }

    @Override
    public void validateRecyclerviewRequest(Context context, HashMap<String, String> mParams) {
        iRecyclerviewModel.validateRecyclerviewRequest(context, mParams);
    }

    @Override
    public void onRecyclerviewResponseSuccess(String message, String response) {
        iRecyclerviewView.onRecyclerviewResponseSuccess(message, response);
    }

    @Override
    public void onRecyclerviewResponseFailed(String message) {
        iRecyclerviewView.onRecyclerviewResponseFailed(message);
    }

    @Override
    public void onRecyclerviewPresenterDestroy() {
        iRecyclerviewView = null;
        iRecyclerviewModel.onRecyclerviewModelDestroy();

    }
}
