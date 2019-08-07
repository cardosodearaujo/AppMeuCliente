package br.com.newoutsourcing.walletofclients.Views.Adapters;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lFragmentList = new ArrayList<>();
    private final List<String> lFragmentTitleList = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm) {
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

    @Override
    public CharSequence getPageTitle(int position){
        return lFragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title, Bundle bundle){
        if (bundle != null) fragment.setArguments(bundle);
        lFragmentList.add(fragment);
        lFragmentTitleList.add(title);
    }
}