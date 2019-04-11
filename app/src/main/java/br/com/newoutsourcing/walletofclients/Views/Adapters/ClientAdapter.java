package br.com.newoutsourcing.walletofclients.Views.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ViewHolder.ClientViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder>{

    private List<Client> clientList;

    public ClientAdapter(List<Client> clientList){
        this.clientList = clientList;
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClientViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_client, parent, false));
    }

    @Override
    public void onBindViewHolder(ClientViewHolder viewHolder, final int position) {
        if (this.clientList.get(position).getImage() != null && !this.clientList.get(position).getImage().equals("")){
            viewHolder.idPhotoProfile.setImageBitmap(FunctionsApp.parseBase64ToBitmap(this.clientList.get(position).getImage()));
            viewHolder.idPhotoProfile.setScaleType(CircleImageView.ScaleType.CENTER_CROP);
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

        viewHolder.idBtnEdit.setOnClickListener(onClickEdit);

        viewHolder.idBtnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TB_CLIENT.Delete(clientList.get(position));
                        removeItem(position);
                        FunctionsApp.showSnackBarLong(v,"Cliente excluido com sucesso!");
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return clientList != null ? clientList.size() : 0;
    }

    public void insertItem(Client client){
        this.clientList.add(client);
        this.notifyItemInserted(clientList.size());
    }

    private void updateItem(int position,Client client) {
        this.clientList.set(position,client);
        notifyItemChanged(position);
    }

    public void removeItem(int position){
        this.clientList.remove(position);
        notifyItemRemoved(position);
    }

    View.OnClickListener onClickEdit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FunctionsApp.showSnackBarLong(v,"Edição!");
        }
    };

}
