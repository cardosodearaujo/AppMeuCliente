package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.ActivityBase;
import butterknife.BindView;

public class ContactActivity extends ActivityBase {

    protected @BindView(R.id.idBtnContactClose) Button idBtnContactClose;
    protected @BindView(R.id.idWebView) WebView idWebView;
    protected @BindView(R.id.idBtnNews) ImageButton idBtnNews;
    private boolean loadReleases = false;

    public ContactActivity() {
        super(R.layout.activity_contact);
    }

    @Override
    protected void onConfiguration() {
        this.idBtnContactClose.setOnClickListener(this.onClickClose);
        this.idBtnNews.setOnClickListener(this.onClickNews);
        this.idWebView.loadUrl("file:///android_asset/contact.html");
    }

    View.OnClickListener onClickClose = v -> FunctionsTools.closeActivity(ContactActivity.this);

    View.OnClickListener onClickNews = v -> {
        if (!this.loadReleases){
            this.loadReleases = true;
            this.idBtnNews.setBackgroundResource(R.mipmap.ic_arrow_red);
            this.idWebView.loadUrl("file:///android_asset/news_releases.html");
        }else{
            this.loadReleases = false;
            this.idBtnNews.setBackgroundResource(R.mipmap.ic_new_realeses);
            this.idWebView.loadUrl("file:///android_asset/contact.html");
        }
    };
}
