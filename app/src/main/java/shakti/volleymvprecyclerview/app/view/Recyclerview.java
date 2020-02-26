package shakti.volleymvprecyclerview.app.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import shakti.volleymvprecyclerview.R;
import shakti.volleymvprecyclerview.app.model.pojo.RecyclerviewPojo;
import shakti.volleymvprecyclerview.app.presenter.IRecyclerviewPresenter;
import shakti.volleymvprecyclerview.app.presenter.RecyclerviewPresenter;
import shakti.volleymvprecyclerview.app.presenter.adapter.RecyclerviewAdapter;
import shakti.volleymvprecyclerview.appdetail.view.AppDetail;
import shakti.volleymvprecyclerview.library.recyclerviewitems.RecyclerTouchListener;
import shakti.volleymvprecyclerview.library.recyclerviewitems.SeparatorDecoration;
import shakti.volleymvprecyclerview.library.utils.NetworkUtil;
import shakti.volleymvprecyclerview.library.utils.Utils;


/**
 * Created by Sakthivel on 15-12-2019.
 */
public class Recyclerview extends Activity implements View.OnClickListener, IRecyclerviewView {

    @BindView(R.id.SuccessStory_back_layout)
    RelativeLayout Rl_back;
    @BindView(R.id.successStory_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.successStory_empty_label)
    TextView Tv_empty;

    /**
     * Recyclerview Presenter interface instance in order to access the override methods
     */
    private IRecyclerviewPresenter iRecyclerviewPresenter;

    private ArrayList<RecyclerviewPojo> recyclerViewList;
    private String sRegisteredPk = "", sUserId = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_screen);
        ButterKnife.bind(this);
        initialize();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(Recyclerview.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if (NetworkUtil.checkNetConnection(Recyclerview.this)) {

                    Intent intent = new Intent(Recyclerview.this, AppDetail.class);
                    intent.putExtra("Intent-id", recyclerViewList.get(position).getId());
                    intent.putExtra("Intent-name",recyclerViewList.get(position).getProjectName());
                    intent.putExtra("Intent-description",recyclerViewList.get(position).getsDescription());
                    intent.putExtra("Intent-img",recyclerViewList.get(position).getsProjectImage());

                    startActivity(intent);
                    Utils.animateStartActivity(Recyclerview.this);

                } else {
                    Utils.noInternetAlert(Recyclerview.this);
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }) {
        });
    }

    private void initialize() {
        recyclerViewList = new ArrayList<>();
        iRecyclerviewPresenter = new RecyclerviewPresenter(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Recyclerview.this));
        float height = getResources().getDimension(R.dimen._1sdp);
        recyclerView.addItemDecoration(new SeparatorDecoration(Recyclerview.this, Color.parseColor("#edeef0"), height));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        /*
         * Requesting the api call
         */
        requestSuccessStoryApi();

    }

    @OnClick({R.id.SuccessStory_back_layout})
    @Override
    public void onClick(View view) {
        if (view == Rl_back) {
            finish();
            Utils.animateFinishActivity(Recyclerview.this);
        }
    }

    private void requestSuccessStoryApi() {
        HashMap<String, String> params = new HashMap<>();
      //  params.put("userpk", sUserId);
      //  params.put("registerpk", sRegisteredPk);
        iRecyclerviewPresenter.validateRecyclerviewRequest(Recyclerview.this, params);
    }

    @Override
    public void onRecyclerviewResponseSuccess(String message, String response) {

        try {
            JSONObject object = new JSONObject(response);
            if (object.length() > 0) {

                JSONArray responseArray = object.getJSONArray("data");
                recyclerViewList.clear();
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject responseObject = responseArray.getJSONObject(i);
                    RecyclerviewPojo pojo = new RecyclerviewPojo();
                    pojo.setId(responseObject.getString("pk"));
                    pojo.setProjectName(responseObject.getString("name"));
                    pojo.setProjectStage(responseObject.getString("prjd_projstage"));
                    pojo.setsProjectImage(responseObject.getString("logo"));
                    pojo.setsDescription(responseObject.getString("description"));

                    recyclerViewList.add(pojo);

                }
            }

            if (recyclerViewList.size() == 0) {
                Tv_empty.setVisibility(View.VISIBLE);
            } else {
                Tv_empty.setVisibility(View.GONE);
            }

            RecyclerviewAdapter adapter = new RecyclerviewAdapter(Recyclerview.this, recyclerViewList);
            recyclerView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRecyclerviewResponseFailed(String message) {
        Utils.alert(Recyclerview.this, getResources().getString(R.string.app_sorry), message, getResources().getString(R.string.app_ok), 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.onBackPress(Recyclerview.this, Utils.EnterExitAnimation);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requestSuccessStoryApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iRecyclerviewPresenter.onRecyclerviewPresenterDestroy();
    }
}