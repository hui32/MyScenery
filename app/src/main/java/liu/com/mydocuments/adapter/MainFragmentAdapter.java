package liu.com.mydocuments.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liuhui on 2016/2/19.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    List<String> titleList;
    public MainFragmentAdapter(FragmentManager fm,List<Fragment> fragmentList,List<String> titleList){
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position%titleList.size());
    }
}
