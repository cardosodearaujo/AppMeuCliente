package br.com.newoutsourcing.walletofclients.Views.Adapters.Client;

import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Activitys.RegisterClientActivity;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder>{
    private List<Client> clientList = new ArrayList<>();

    public ClientAdapter(List<Client> clientList){
        this.clientList.addAll(clientList);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClientViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_client, parent, false));
    }

    @Override
    public void onBindViewHolder(final ClientViewHolder viewHolder, final int position) {
        if (this.clientList.get(position).getImage() != null && !this.clientList.get(position).getImage().equals("")){
            Picasso.get()
                    .load(Uri.fromFile(new File(this.clientList.get(position).getImage())))
                    .error(R.mipmap.ic_client_circle)
                    .into(viewHolder.idPhotoProfile);
            viewHolder.idPhotoProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        if(this.clientList.get(position).getType() == 1){
            viewHolder.idTxwName.setText(this.clientList.get(position).getPhysicalPerson().getName());
            viewHolder.idTxwCPF_CNPJ.setText(this.clientList.get(position).getPhysicalPerson().getCPF());
            viewHolder.idTxwTipo.setText("PF");
        }else{
            viewHolder.idTxwName.setText(this.clientList.get(position).getLegalPerson().getSocialName());
            viewHolder.idTxwCPF_CNPJ.setText(this.clientList.get(position).getLegalPerson().getCNPJ());
            viewHolder.idTxwTipo.setText("PJ");
        }

        viewHolder.idCardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDetail(v,position);
                    }
                }
        );

        viewHolder.idBtnEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickDetail(v,position);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return this.clientList != null ? this.clientList.size() : 0;
    }


    private void onClickDetail(View v,int position){
        Bundle bundle = new Bundle();
        if (clientList.get(position).getType() == 1){
            bundle.putString("TipoCadastro","F");
        }else{
            bundle.putString("TipoCadastro","J");
        }
        bundle.putSerializable("Client", clientList.get(position));
        FunctionsTools.startActivity(v.getContext(), RegisterClientActivity.class,bundle);
    }
}
