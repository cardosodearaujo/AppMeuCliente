package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.util.ArrayList;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;

public class ImageFragment extends Fragment implements FragmentsCallback {

    private CircleImageView idImgClient;
    private LinearLayout idLLImgClient;

    public ImageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        this.onInflate(view);
        this.onConfiguration();
        if (this.getArguments() != null && this.getArguments().containsKey("Client")) this.onLoad((Client)this.getArguments().getSerializable("Client"));
        return view;
    }

    public static ImageFragment newInstance(){
        return new ImageFragment();
    }

    private void onInflate(View view){
        this.idImgClient = view.findViewById(R.id.idImgClient);
        this.idLLImgClient = view.findViewById(R.id.idLLImgClient);
    }

    private void onConfiguration(){
        this.idImgClient.setOnClickListener(this.onClickTakePhoto);
        this.idLLImgClient.setOnClickListener(this.onClickTakePhoto);
    }

    private View.OnClickListener onClickTakePhoto = view -> getPermissions();

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else{
            this.getPhoto();
        }
    }

    private void getPhoto() {
        AlertDialog alert;
        ArrayList<String> itens = new ArrayList<String>();

        itens.add("Tirar foto");
        itens.add("Escolher da galeria");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.alert_dialog_question, itens);
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity(),R.style.Theme_MaterialComponents_Light_Dialog);
        builder.setSingleChoiceItems(adapter, 0, (dialog, idOption) -> {
            Intent intent;
            switch (idOption) {
                case 0: //Tirar Foto
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, FunctionsApp.IMAGEM_CAMERA);
                    dialog.cancel();
                    break;
                case 1: //Pegar da galeria
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"),FunctionsApp.IMAGEM_INTERNA);
                    dialog.cancel();
                    break;
            }
        });
        alert = builder.create();
        alert.setTitle("Escolha uma opção:");
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.getPhoto();
            } else {
                FunctionsApp.showSnackBarLong(this.getView(),"Permissão negada!");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if (resultCode == RESULT_OK){
                Bitmap bitmap;
                if (requestCode == FunctionsApp.IMAGEM_INTERNA){
                    Uri imagemSelecionada = data.getData();
                    String[] colunas = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                    cursor.moveToFirst();

                    int indexColuna = cursor.getColumnIndex(colunas[0]);
                    String pathImg = cursor.getString(indexColuna);
                    cursor.close();

                    bitmap = BitmapFactory.decodeFile(pathImg);
                    if (bitmap != null){
                        this.idImgClient.setImageBitmap(bitmap);
                        this.idImgClient.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }else if(requestCode == FunctionsApp.IMAGEM_CAMERA){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null){
                        this.idImgClient.setImageBitmap(bitmap);
                        this.idImgClient.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
            }
        }catch (Exception ex){
            FunctionsApp.showAlertDialog(this.getContext(),"Erro",ex.getMessage(),"Fechar");
        }
    }

    @Override
    public void onLoad(Client client) {
        if (client != null){
            Picasso.get()
                    .load(Uri.fromFile(new File(client.getImage())))
                    .error(R.mipmap.ic_client_circle)
                    .into(this.idImgClient);
            this.idImgClient.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public boolean onValidate() {
        boolean save = false;
        if (this.idImgClient.getDrawable() != null &&  ((BitmapDrawable) this.idImgClient.getDrawable()).getBitmap() != null){
            save = true;
        }
        return save;
    }

    @Override
    public Client onSave(Client client) {
        if (this.onValidate()){
            String pathImage = FunctionsApp.saveImage(((BitmapDrawable) this.idImgClient.getDrawable()).getBitmap());
            if (!pathImage.isEmpty()){
                client.setImage(pathImage);
            }
        }
        return client;
    }

    @Override
    public void onClear() {
        Picasso.get().load(R.mipmap.ic_client_circle).error(R.mipmap.ic_client_circle).into(this.idImgClient);
    }
}
