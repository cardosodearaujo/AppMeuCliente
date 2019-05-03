package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

public class ContactActivity extends AppCompatActivity {

    private Button idBtnContactClose;
    private WebView idWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.onInflate();
        this.onConfiguration();
    }

    private void onInflate(){
        super.setContentView(R.layout.activity_contact);
        this.idBtnContactClose = this.findViewById(R.id.idBtnContactClose);
        this.idWebView = this.findViewById(R.id.idWebView);
    }

    private void onConfiguration(){
        this.idBtnContactClose.setOnClickListener(this.onClickClose);
        this.idWebView.loadUrl("file:///android_asset/contact.html");
    }

    View.OnClickListener onClickClose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FunctionsApp.closeActivity(ContactActivity.this);
        }
    };
}
