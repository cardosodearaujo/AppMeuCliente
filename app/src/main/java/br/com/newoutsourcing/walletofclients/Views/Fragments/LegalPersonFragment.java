package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class LegalPersonFragment extends Fragment implements FragmentsCallback {
    private Toolbar idToolbar;
    private TextView idTxwClientPJDescriptionData;
    private EditText idEdtClientPJSocialName;
    private EditText idEdtClientPJFantasyName;
    private EditText idEdtClientPJCNPJ;
    private EditText idEdtClientPJIE;
    private EditText idEdtClientPJIM;
    private TextView idTxwClientPJDescriptionAdditionalData;
    private EditText idEdtClientPJSite;
    private EditText idEdtClientPJObservation;
    private ViewPager idViewPager;
    private CircleImageView idImgClientPJPhoto;

    public LegalPersonFragment() {
    }

    public static LegalPersonFragment newInstance() {
        return new LegalPersonFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_legal_person, container, false);
        this.onInflate(view);
        this.onConfiguration();
        return view;
    }

    private void onInflate(View view){
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idViewPager = this.getActivity().findViewById(R.id.idViewPager);
        this.idEdtClientPJSocialName = view.findViewById(R.id.idEdtClientPJSocialName);
        this.idEdtClientPJFantasyName = view.findViewById(R.id.idEdtClientPJFantasyName);
        this.idEdtClientPJCNPJ = view.findViewById(R.id.idEdtClientPJCNPJ);
        this.idEdtClientPJIE = view.findViewById(R.id.idEdtClientPJIE);
        this.idEdtClientPJIM = view.findViewById(R.id.idEdtClientPJIM);
        this.idImgClientPJPhoto = view.findViewById(R.id.idImgClientPJPhoto);
    }

    private void onConfiguration(){
        this.idToolbar.setSubtitle("Pessoa juridica");
        this.idEdtClientPJCNPJ.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_CNPJ, this.idEdtClientPJCNPJ));
        this.idImgClientPJPhoto.setOnClickListener(this.onClickTakePhoto);
    }

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
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(adapter, 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idOption) {
                Intent intent;
                switch (idOption) {
                    case 0: //Tirar Foto
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, FunctionsApp.IMAGEM_CAMERA);
                        dialog.cancel();
                        break;
                    case 1: //Pegar da galeria
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"),FunctionsApp.IMAGEM_INTERNA);
                        dialog.cancel();
                        break;
                }
            }
        });
        alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.getPhoto();
            } else {
                FunctionsApp.showSnackBarShort(this.getView(),"Permissão negada!");
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
                        this.idImgClientPJPhoto.setImageBitmap(bitmap);
                        this.idImgClientPJPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }else if(requestCode == FunctionsApp.IMAGEM_CAMERA){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null){
                        this.idImgClientPJPhoto.setImageBitmap(bitmap);
                        this.idImgClientPJPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
            }
        }catch (Exception ex){
            FunctionsApp.showSnackBarShort(this.getView(),ex.getMessage());
        }
    }

    @Override
    public boolean onSave() {
        return true;
    }

    @Override
    public void onClear() {
        this.idEdtClientPJSocialName.setText("");
        this.idEdtClientPJFantasyName.setText("");
        this.idEdtClientPJCNPJ.setText("");
        this.idEdtClientPJIE.setText("");
        this.idEdtClientPJIM.setText("");
        this.idImgClientPJPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_client_circle));
    }

    private View.OnClickListener onClickTakePhoto = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            getPermissions();
        }
    };
}
