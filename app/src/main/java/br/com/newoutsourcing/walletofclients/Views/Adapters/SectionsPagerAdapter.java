package br.com.newoutsourcing.walletofclients.Views.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.newoutsourcing.walletofclients.Views.Fragments.LegalPersonFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.PhysicalPersonFragment;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment selectedFragment;
        switch (position){
            case 0:
                selectedFragment = PhysicalPersonFragment.newInstance();
                break;
            case 1:
                selectedFragment = LegalPersonFragment.newInstance();
                break;
            default:
                selectedFragment = PhysicalPersonFragment.newInstance();
                break;
        }
        return selectedFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}