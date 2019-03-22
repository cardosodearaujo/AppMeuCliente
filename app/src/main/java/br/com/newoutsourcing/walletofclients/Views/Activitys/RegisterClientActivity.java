package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Adapters.SectionsPagerAdapter;
import br.com.newoutsourcing.walletofclients.Views.Fragments.LegalPersonFragment;
import br.com.newoutsourcing.walletofclients.Views.Fragments.PhysicalPersonFragment;

public class RegisterClientActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        setSupportActionBar((Toolbar) findViewById(R.id.idToolbar));

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(PhysicalPersonFragment.newInstance());
        mSectionsPagerAdapter.addFragment(LegalPersonFragment.newInstance());

        mViewPager = findViewById(R.id.idViewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }
}
