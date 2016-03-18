package liu.com.mydocuments.myFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import liu.com.mydocuments.R;
import liu.com.mydocuments.adapter.ExpViewPagerAdapter;

public class RecommendFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
//    @Bind(R.id.vp_exp)
    ViewPager vp_exp;
    private ExpViewPagerAdapter adapter;
    private List<Fragment> fragments;
    public RecommendFragment() {

    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend, container ,false);
        vp_exp = (ViewPager) view.findViewById(R.id.vp_exp);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        adapter = new ExpViewPagerAdapter(getActivity().getSupportFragmentManager(),fragments);
        vp_exp.setOffscreenPageLimit(fragments.size());
        vp_exp.setPageMargin(65);
        vp_exp.setAdapter(adapter);
    }

    /** 初始viewpager中fragmengt*/
    private void initData() {
        String[] ids = getResources().getStringArray(R.array.scenery_id);
        fragments = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            RecommendItemFragment fragment = RecommendItemFragment.newInstance(ids[i], "", i);
            fragments.add(fragment);
        }
    }
}
