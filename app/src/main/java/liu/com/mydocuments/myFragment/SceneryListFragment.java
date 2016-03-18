package liu.com.mydocuments.myFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liu.com.mydocuments.R;
import liu.com.mydocuments.adapter.RceneryRecycleAdapter;
import liu.com.mydocuments.constants.Constant;
import liu.com.mydocuments.jsonBean.SceneryInfo;
import liu.com.mydocuments.util.DataUtil;
import okhttp3.Call;

public class SceneryListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.swipe_refresh_ly)
    SwipeRefreshLayout swipeRefreshLy;
    @Bind(R.id.rcv_scenery)
    RecyclerView rcvScenery;

    public static final int PULL_TOREFRESH = 0;
    public static final int PULL_TOADD_LOAD = 1;
    RceneryRecycleAdapter adapter;
    List<SceneryInfo.RetDataEntity.TicketListEntity> contents;

    public int pageNo = 1;
    public int pageSize;

    public SceneryListFragment() {

    }

    public static SceneryListFragment newInstance() {
        SceneryListFragment fragment = new SceneryListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRecycelView();
        setSwipeRefreshLy();
        swipeRefreshLy.setRefreshing(true);
        getData(PULL_TOREFRESH);
    }

    private void setSwipeRefreshLy(){
        swipeRefreshLy.setOnRefreshListener(this);
    }
    private void setRecycelView() {
        contents = new ArrayList<>();
        rcvScenery.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RceneryRecycleAdapter(getActivity(), contents);
        rcvScenery.setAdapter(adapter);
    }
    public void getData(final int refreshFlag) {
        OkHttpUtils
                .get()
                .url(Constant.QunarUrl.listUrl)
                .addHeader(Constant.API_KEY, Constant.BKEY_VALUE)
                .addParams("pageno", pageNo + "")
                .addParams("pagesize", "20")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        String responseStr = DataUtil.Unicode2GBK(response);
                        Log.e(TAG, Constant.QunarUrl.listUrl + "\nreqponse-->:" + responseStr);
                        SceneryInfo info = new Gson().fromJson(responseStr, SceneryInfo.class);
                        int errNum = info.getErrNum();
                        String errMess = info.getErrMsg();
                        List<SceneryInfo.RetDataEntity.TicketListEntity> currentList = info.getRetData().getTicketList();
                        if (refreshFlag == PULL_TOREFRESH) {
                            contents.clear();
                            contents.addAll(currentList);
                            swipeRefreshLy.setRefreshing(false);
                        } else {
                            contents.addAll(currentList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onRefresh() {
        getData(PULL_TOREFRESH);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
