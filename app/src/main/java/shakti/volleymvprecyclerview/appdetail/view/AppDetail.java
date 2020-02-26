package shakti.volleymvprecyclerview.appdetail.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import shakti.volleymvprecyclerview.R;
import shakti.volleymvprecyclerview.appdetail.presenter.AppDetailPresenter;
import shakti.volleymvprecyclerview.appdetail.presenter.IAppDetailPresenter;
import shakti.volleymvprecyclerview.library.utils.Utils;

/**
 * Created by Sakthivel on 20-02-2020.
 */
public class AppDetail extends AppCompatActivity implements View.OnClickListener, IAppDetailModelView{

    @BindView(R.id.AppDetail_back_layout)
    RelativeLayout Rl_back;

    @BindView(R.id.AppDetail_project_owner_logo_imageView)
    ImageView Iv_ProjectOwnerLogo;
    @BindView(R.id.AppDetailHeader_projectOwner_details_textView)
    TextView Tv_companyName;
    @BindView(R.id.AppDetailHeader_projectName_textView)
    TextView Tv_projectName;
    @BindView(R.id.AppDetailHeader_projectName_date_textView)
    TextView Tv_projectDate;
    @BindView(R.id.AppDetailHeader_sStory_details_textView)
    TextView Tv_storyDetail;

    @BindView(R.id.AppDetail_projectStage_textView)
    TextView Tv_projectStage;
    @BindView(R.id.AppDetail_investors_textView)
    TextView Tv_investedInvestors;

    @BindView(R.id.AppDetailHeader_youtube_main_layout)
    RelativeLayout Iv_uTubeBtn;


    /**
     * AppDetail Presenter interface instance in order to access the override methods
     */
    private IAppDetailPresenter iAppDetailPresenter;

    private String sSuccessPk = "", sRegisterPk = "";
    private String sProjectOwnerLogo = "", sProjectOwner = "", sProjectName = "", sPostedDate = "", sAppDetail = "",
            sProjectStage = "", sInvestorsCount = "", sUtube = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_detail_screen);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        iAppDetailPresenter = new AppDetailPresenter(this);

/*
        SessionManager sessionManager = new SessionManager(AppDetail.this);
        HashMap<String, String> user = sessionManager.getUserDetails();
        sRegisterPk = user.get(SessionManager.KEY_USER_REGISTER_PK);
*/

        Intent intent = getIntent();
        sSuccessPk = intent.getStringExtra("Intent-id");
        sProjectName = intent.getStringExtra("Intent-name");
        sAppDetail = intent.getStringExtra("Intent-description");
        sProjectOwnerLogo = intent.getStringExtra("Intent-img");

        /*
         * Requesting the api call
         */
        requestApiCall();
    }

    @OnClick({R.id.AppDetail_back_layout, R.id.AppDetailHeader_youtube_main_layout})
    @Override
    public void onClick(View view) {
        if (view == Rl_back) {
            finish();
            Utils.animateFinishActivity(AppDetail.this);
        } else if (view == Iv_uTubeBtn){
            Intent intent = new  Intent(Intent.ACTION_VIEW);

            intent.setPackage("com.google.android.youtube");
            intent.setData(Uri.parse(sUtube));
            startActivity(intent);
        }
    }

    /**
     * Requesting the api call
     */
    private void requestApiCall() {
        HashMap<String, String> params = new HashMap<>();
        params.put("successpk", sSuccessPk);
        params.put("registerpk", sRegisterPk);
        iAppDetailPresenter.validateAppDetailRequest(AppDetail.this, params);
    }

    @Override
    public void onAppDetailViewResponseSuccess(String message, String response) {

        try {
/*            JSONObject object = new JSONObject(response);
            if (object.length() > 0) {

                sProjectOwner = object.getString("projectowner");
                sProjectName = object.getString("projectname");
                sPostedDate = object.getString("posteddate");
                sAppDetail = object.getString("insh_App");
                sProjectStage = object.getString("projectstage");
                sInvestorsCount = object.getString("investorscount");
                sProjectOwnerLogo = object.getString("projectowner_logo");
                sUtube = object.getString("youtubelink");


            }*/

            if(sUtube !=null && sUtube.isEmpty()){

                Iv_uTubeBtn.setVisibility(View.GONE);
            } else {
                Iv_uTubeBtn.setVisibility(View.VISIBLE);
            }

            Tv_companyName.setText(checkEmptyData(sProjectOwner));
            Tv_projectName.setText(checkEmptyData(sProjectName));
            Tv_projectDate.setText(checkEmptyData(sPostedDate));
            Tv_storyDetail.setText(checkEmptyData(sAppDetail));
            Tv_projectStage.setText(checkEmptyData(sProjectStage));
            Tv_investedInvestors.setText(checkEmptyData(sInvestorsCount));


            if (sProjectOwnerLogo.length() > 0) {
                Picasso.with(AppDetail.this)
                        .load(sProjectOwnerLogo)
                        .placeholder(R.drawable.ic_no_image)
                        .error(R.drawable.ic_no_image)
                        .into(Iv_ProjectOwnerLogo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAppDetailViewResponseFailed(String message) {
        Utils.alert(AppDetail.this, getResources().getString(R.string.app_sorry), message, getResources().getString(R.string.app_ok), 0);
    }

    private String checkEmptyData(String data) {
        return Utils.checkEmpty(AppDetail.this, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Utils.onBackPress(AppDetail.this, Utils.EnterExitAnimation);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iAppDetailPresenter.onAppDetailPresenterDestroy();
    }



}

