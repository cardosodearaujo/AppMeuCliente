package br.com.newoutsourcing.walletofclients.Views.Bases;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private int view;
    protected @BindView(android.R.id.content) View View;

    public BaseActivity(int view){
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(this.view);
        ButterKnife.bind(this);
        this.onConfiguration();
    }

    protected abstract void onConfiguration();
}
