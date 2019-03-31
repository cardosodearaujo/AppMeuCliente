package br.com.newoutsourcing.walletofclients.Views.Adapters.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.newoutsourcing.walletofclients.R;

public class ClientViewHolder  extends RecyclerView.ViewHolder {
    public TextView idTxwName;
    public TextView idTxwCPF_CNPJ;
    public TextView idTxwTipo;

    public ClientViewHolder(View view){
        super(view);
        this.idTxwName = view.findViewById(R.id.idTxwName);
        this.idTxwCPF_CNPJ = view.findViewById(R.id.idTxwCPF_CNPJ);
        this.idTxwTipo = view.findViewById(R.id.idTxwTipo);
    }
}
