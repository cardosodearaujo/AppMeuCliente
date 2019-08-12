package br.com.newoutsourcing.walletofclients.Views.Adapters.Client;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.newoutsourcing.walletofclients.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientViewHolder  extends RecyclerView.ViewHolder {
    public CircleImageView idPhotoProfile;
    public TextView idTxwName;
    public TextView idTxwCPF_CNPJ;
    public TextView idTxwTipo;
    public ImageButton idBtnEdit;
    public CardView idCardView;

    public ClientViewHolder(View view){
        super(view);
        this.idPhotoProfile = view.findViewById(R.id.idPhotoProfile);
        this.idTxwName = view.findViewById(R.id.idTxwName);
        this.idTxwCPF_CNPJ = view.findViewById(R.id.idTxwCPF_CNPJ);
        this.idTxwTipo = view.findViewById(R.id.idTxwTipo);
        this.idBtnEdit = view.findViewById(R.id.idBtnEdit);
        this.idCardView = view.findViewById(R.id.idCardView);
    }
}
