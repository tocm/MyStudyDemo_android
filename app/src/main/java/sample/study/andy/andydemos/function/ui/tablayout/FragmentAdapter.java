package sample.study.andy.andydemos.function.ui.tablayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy.chen on 2016/12/6.
 */

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private final static String TAG = FragmentAdapter.class.getSimpleName();
    private List<Fragment> mFragments = new ArrayList<>();
    private List<CategoryEntity> mCategoryLists = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryLists.get(position).getName();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    //新增页面
    public void addItem(CategoryEntity categoryEntity) {
        Log.d(TAG,"addItem "+categoryEntity.getName());
        mCategoryLists.add(categoryEntity);
        mFragments.add(ContentViewpageFragment.newInstance(Integer.toString(categoryEntity.getId()),categoryEntity.getName() + "aaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbb"));
    }
}
