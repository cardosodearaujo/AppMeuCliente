package br.com.newoutsourcing.walletofclients.Views.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lFragmentList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return lFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        lFragmentList.add(fragment);
    }
}