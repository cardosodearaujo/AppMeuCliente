package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.BaseActivity;
import butterknife.BindView;

public class ContactActivity extends BaseActivity {

    protected @BindView(R.id.idBtnContactClose) Button idBtnContactClose;
    protected @BindView(R.id.idWebView) WebView idWebView;

    public ContactActivity() {
        super(R.layout.activity_contact);
    }

    @Override
    protected void onConfiguration() {
        this.idBtnContactClose.setOnClickListener(this.onClickClose);
        this.idWebView.loadUrl("file:///android_asset/contact.html");
    }

    View.OnClickListener onClickClose = v -> FunctionsTools.closeActivity(ContactActivity.this);
}
