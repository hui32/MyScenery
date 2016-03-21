package liu.com.mydocuments.myFragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        setRecycleViewScrollListener();
    }

    /** 设置刷新监听*/
    private void setSwipeRefreshLy(){
        swipeRefreshLy.setOnRefreshListener(this);
    }
    private void setRecycelView() {
        contents = new ArrayList<>();
        rcvScenery.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RceneryRecycleAdapter(getActivity(), contents);
        rcvScenery.setAdapter(adapter);
    }
    /** 从网络获取数据*/
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

    private void setRecycleViewScrollListener(){
        rcvScenery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {/**滑动停止**/
                    Log.d("test", "recyclerView总共的Item个数:" +
                            String.valueOf(recyclerView.getLayoutManager().getItemCount() - 1));
                    Log.d("test", "recyclerView可见的Item个数:" +
                            String.valueOf(recyclerView.getChildCount()));
                    /**
                     * 监听是否滑动到底部
                     */
                    //获取可见的最后一个view
                    View lastChildView = recyclerView.getChildAt(
                            recyclerView.getChildCount() - 1);


                    //获取可见的最后一个view的位置
                    int lastChildViewPosition = recyclerView.getChildAdapterPosition(lastChildView);

                    //判断lastPosition是不是最后一个position
                    if (lastChildViewPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        pageNo++;
                        getData(PULL_TOADD_LOAD);
                        Toast.makeText(getActivity(), "滑动到底部了", Toast.LENGTH_SHORT).show();
                    }

                    /**监听是否滑动到顶部**/
                    //获取可见的第一个view
                    View firstVisibleView = recyclerView.getChildAt(0);

                    //获取可见的第一个view的位置
                    int firstVisiblePosition = recyclerView.getChildAdapterPosition(firstVisibleView);

                    if (firstVisiblePosition == 0) {
                        Toast.makeText(getActivity(), "滑动到顶部了", Toast.LENGTH_SHORT).show();
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {/**手指还在recycleview上**/
                } else if (newState == RecyclerView.SCROLL_STATE_SETTLING) {/**手指离开屏幕，单recycleview仍在滚动**/
                }
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
