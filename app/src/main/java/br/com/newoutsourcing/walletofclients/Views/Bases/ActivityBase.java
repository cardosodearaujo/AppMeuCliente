package br.com.newoutsourcing.walletofclients.Views.Bases;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ActivityBase extends AppCompatActivity {
    private Unbinder unbind;
    private int view;
    protected @BindView(android.R.id.content) View View;

    public ActivityBase(int view){
        this.view = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(this.view);
        this.unbind = ButterKnife.bind(this);
        this.onConfiguration();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unbind.unbind();
    }

    protected abstract void onConfiguration();
}
