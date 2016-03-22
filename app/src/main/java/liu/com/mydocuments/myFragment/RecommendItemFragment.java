package liu.com.mydocuments.myFragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import liu.com.mydocuments.R;
import liu.com.mydocuments.SceneryWvDetailActivity;
import liu.com.mydocuments.constants.Constant;
import liu.com.mydocuments.jsonBean.SceneryDetail;
import liu.com.mydocuments.util.DataUtil;
import liu.com.mydocuments.util.JumpUtil;
import okhttp3.Call;

/** 推荐景点*/
public class RecommendItemFragment extends BaseFragment {
    @Bind(R.id.ll_item)
    LinearLayout ll_item;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.iv_scenery)
    ImageView ivScenery;
    @Bind(R.id.tv_describle)
    TextView tvDescrible;

    private static final String SC_ID = "SC_ID";
    private static final String PAGE_CONTENT = "PAGE_CONTENT";
    private static final String POITION = "POSITON";

    private String sc_id;
    private String page_content;
    private int posi;
    RecommendItemFragment fragment;
    public RecommendItemFragment() {

        Log.e("RecommendItemFragment"," RecommendItemFragment "+posi);

    }

    public static RecommendItemFragment newInstance(String sc_id, String content, int position) {
        Log.e("RecommendItemFragment"," newInstance "+position);
        RecommendItemFragment fragment = new RecommendItemFragment();
        Bundle args = new Bundle();
        args.putString(SC_ID, sc_id);
        args.putString(PAGE_CONTENT, content);
        args.putInt(POITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sc_id = getArguments().getString(SC_ID);
            page_content = getArguments().getString(PAGE_CONTENT);
            posi = getArguments().getInt(POITION);
        }
        Log.e("RecommendItemFragment"," onCreate ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rec_item, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();

    }

    public void getData() {
        OkHttpUtils
                .get()
                .url(Constant.QunarUrl.detailUrl)
                .addHeader(Constant.API_KEY, Constant.BKEY_VALUE)
                .addParams(Constant.RequestParam.SCENERY_ID, sc_id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "requestUrl = " + Constant.QunarUrl.detailUrl + "\nreqponse-->:" + DataUtil.Unicode2GBK(response));
                        String responseStr = DataUtil.Unicode2GBK(response);
                        SceneryDetail detail = new Gson().fromJson(responseStr, SceneryDetail.class);
                        showUI(detail);
                    }
                });
    }

    private void showUI(final SceneryDetail detail) {
        SceneryDetail.RetDataEntity.TicketDetailEntity.DataEntity.DisplayEntity.TicketEntity ticket
                = new SceneryDetail.RetDataEntity.TicketDetailEntity.DataEntity.DisplayEntity.TicketEntity();
        ticket = detail.retData.ticketDetail.data.display.ticket;
        tvName.setText(ticket.spotName);
        tvAddress.setText(ticket.address);
        Glide.with(this)
                .load(ticket.imageUrl)
                .centerCrop()
                .into(ivScenery);
        tvDescrible.setText(ticket.description);

        ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(detail.retData.ticketDetail.loc)){
                    Bundle b = new Bundle();
                    b.putString(Constant.IntentParam.DETAIL_URL,detail.retData.ticketDetail.loc);
                    JumpUtil.jumpWithBoundle(getActivity(), SceneryWvDetailActivity.class,b);
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
