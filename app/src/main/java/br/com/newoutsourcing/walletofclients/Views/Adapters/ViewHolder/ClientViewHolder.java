package br.com.newoutsourcing.walletofclients.Views.Adapters.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import br.com.newoutsourcing.walletofclients.R;

public class ClientViewHolder  extends RecyclerView.ViewHolder {
    public TextView idTxwNome;
    public TextView idTxwCPF_CNPJ;
    public TextView idTxwTipo;

    public ClientViewHolder(View view){
        super(view);
        this.idTxwNome = view.findViewById(R.id.idTxwNome);
        this.idTxwCPF_CNPJ = view.findViewById(R.id.idTxwCPF_CNPJ);
        this.idTxwTipo = view.findViewById(R.id.idTxwTipo);
    }
}
