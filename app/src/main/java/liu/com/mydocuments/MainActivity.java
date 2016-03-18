package liu.com.mydocuments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import liu.com.mydocuments.constants.Constant;
import liu.com.mydocuments.myFragment.RecommendFragment;
import liu.com.mydocuments.myFragment.SceneryListFragment;
import liu.com.mydocuments.util.DataUtil;
import liu.com.mydocuments.view.GlideCircleTransform;
import okhttp3.Call;

public class MainActivity extends BaseActivity {
    String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.id_toolbar)
    Toolbar id_toolbar;
    @Bind(R.id.id_nv_menu)
    NavigationView id_nv_menu;
    @Bind(R.id.id_drawer_layout)
    DrawerLayout id_drawer_layout;
    @Bind(R.id.iv_top_photo)
    ImageView ivTopPhoto;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.ll_nav_header)
    LinearLayout llNavHeader;
    private RecommendFragment recommendFragment;
    private SceneryListFragment sceneryListFragment;
    private List<Fragment> fragments;
    FragmentManager fm;
    public int pageNo = 52;
    public int pageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolBar(id_toolbar,"今日推荐");
        setHomeAsUpListener();

        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        recommendFragment = new RecommendFragment().newInstance();
        fragments.add(recommendFragment);
        sceneryListFragment = new SceneryListFragment().newInstance();
        fragments.add(sceneryListFragment);

        initFragments();
        setNavicatHeaderInfo();

    }

    /** 界面切换控制 */
    public void setFragments(int index) {
        for (int i = 0; i < fragments.size(); i++) {
            FragmentTransaction ft = fm.beginTransaction();
            Fragment fragment = fragments.get(i);
            if (i == index) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        //currentTab = index;
    }

    /** 初始化fragments */
    private void initFragments() {
        for (Fragment fragment : fragments) {
            fm.beginTransaction().add(R.id.fl_container, fragment).hide(fragment).commit();
        }
        setFragments(0);
    }

    /**
     * 选择现实的fragment
     */
    private void setSelectedFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction t = fm.beginTransaction();
        //t.show(fragment).commit();
        t.replace(R.id.fl_container, fragment).commit();
    }

    /** 设置标题头像和昵称等信息*/
    private void setNavicatHeaderInfo() {
        //1.For version 23.1.1 of the Support Library, you can now get a reference to the header view using
        //View headerLayout = navigationView.getHeaderView(0);

        //2.View headerLayout = id_nv_menu.inflateHeaderView(R.layout.main_menu_header);

        Glide.with(mContext)
                .load(R.drawable.top_per_photo)
                .transform(new GlideCircleTransform(mContext)).into(ivTopPhoto);
        tvNickname.setText("hui");
    }

    //navigaton监听
    private void setHomeAsUpListener() {
        id_nv_menu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            private MenuItem lastMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (lastMenuItem != null) lastMenuItem.setChecked(false);
                menuItem.setChecked(true);
                lastMenuItem = menuItem;
                id_drawer_layout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
//                        if (recommendFragment == null) {
//                            recommendFragment = RecommendFragment.newInstance();
//                        }
//                        setSelectedFragment(recommendFragment);

                        setTopTitle("今日推荐");
                        setFragments(0);
                        break;

                    case R.id.nav_messages:
//                        if (sceneryListFragment == null) {
//                            sceneryListFragment = SceneryListFragment.newInstance();
//                        }
//                        setSelectedFragment(sceneryListFragment);

                        setTopTitle("所有景点");
                        setFragments(1);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            id_drawer_layout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData() {
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
                        Log.e(TAG, pageNo + "  reqponse-->:" + DataUtil.Unicode2GBK(response));
                    }
                });
    }
}
