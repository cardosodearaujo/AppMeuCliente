package br.com.newoutsourcing.walletofclients.Views.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;

public class ErrorActivity extends AppCompatActivity {

    private ImageView idImgAlert;
    private TextView idTxwErrorMessageCaption;
    private TextView idTxwErrorMessage;
    private Button idBtnErrorClose;
    private View idView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_error);
        this.onInflate();
        this.onConfiguration();
    }

    public void onInflate(){
        this.idView = this.findViewById(android.R.id.content);
        this.idImgAlert = this.findViewById(R.id.idImgAlert);
        this.idTxwErrorMessageCaption = this.findViewById(R.id.idTxwErrorMessageCaption);
        this.idTxwErrorMessage = this.findViewById(R.id.idTxwErrorMessage);
        this.idBtnErrorClose = this.findViewById(R.id.idBtnErrorClose);
    }

    public void onConfiguration(){
        this.idBtnErrorClose.setOnClickListener(this.onClickClose);
        if (!(this.getIntent().getExtras() == null)){
            if (this.getIntent().getExtras().containsKey("Title")){
                if (this.getIntent().getExtras().getString("Title")!= null){
                    this.idTxwErrorMessageCaption.setText(this.getIntent().getExtras().getString("Title"));
                }
            }
            if (this.getIntent().getExtras().containsKey("Message")){
                if (!this.getIntent().getExtras().getString("Message").equals("")){
                    this.idTxwErrorMessage.setText(this.getIntent().getExtras().getString("Message"));
                }
            }
        }
    }

    View.OnClickListener onClickClose = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            FunctionsApp.closeActivity(ErrorActivity.this);
        }
    };
}
