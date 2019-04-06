package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class PhysicalPersonFragment extends Fragment implements FragmentsCallback {

    private Toolbar idToolbar;
    private CircleImageView idImgClientPFPhoto;
    private EditText idEdtClientPFName;
    private EditText idEdtClientPFNickName;
    private EditText idEdtClientPFCPF;
    private EditText idEdtClientPFRG;
    private Spinner idSpnClientPFSexo;
    private EditText idEdtClientPFDate;

    public PhysicalPersonFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_physical_person, container, false);
        this.onInflate(view);
        this.onConfiguration();
        return view;
    }

    public static PhysicalPersonFragment newInstance() {
        return new PhysicalPersonFragment();
    }

    private void onInflate(View view){
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idEdtClientPFName = view.findViewById(R.id.idEdtClientPFName);
        this.idEdtClientPFNickName = view.findViewById(R.id.idEdtClientPFNickName);
        this.idEdtClientPFCPF = view.findViewById(R.id.idEdtClientPFCPF);
        this.idEdtClientPFRG = view.findViewById(R.id.idEdtClientPFRG);
        this.idSpnClientPFSexo = view.findViewById(R.id.idSpnClientPFSexo);
        this.idEdtClientPFDate = view.findViewById(R.id.idEdtClientPFDate);
        this.idImgClientPFPhoto = view.findViewById(R.id.idImgClientPFPhoto);
    }

    private void onConfiguration(){
        this.idToolbar.setSubtitle("Pessoa física");
        this.idEdtClientPFDate.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_DATA, this.idEdtClientPFDate));
        this.idEdtClientPFCPF.addTextChangedListener(new MaskEditTextChangedListener(FunctionsApp.MASCARA_CPF, this.idEdtClientPFCPF));
        this.idEdtClientPFDate.setText(FunctionsApp.getCurrentDate());
        this.idEdtClientPFDate.setOnClickListener(this.onClickDate);
        this.idImgClientPFPhoto.setOnClickListener(this.onClickTakePhoto);
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
                        this.idImgClientPFPhoto.setImageBitmap(bitmap);
                        this.idImgClientPFPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }else if(requestCode == FunctionsApp.IMAGEM_CAMERA){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null){
                        this.idImgClientPFPhoto.setImageBitmap(bitmap);
                        this.idImgClientPFPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }
            }
        }catch (Exception ex){
            FunctionsApp.showSnackBarShort(this.getView(),ex.getMessage());
        }
    }

    private boolean onValidate(){
        Boolean save = true;

        if (this.idEdtClientPFName.getText().toString().isEmpty()){
            this.idEdtClientPFName.setError("Informe o nome.");
            save = false;
        }else{
            this.idEdtClientPFName.setError(null);
        }

        if (this.idEdtClientPFCPF.getText().toString().isEmpty()){
            this.idEdtClientPFCPF.setError("Informe o CPF.");
            save = false;
        }else{
            this.idEdtClientPFCPF.setError(null);
        }

        if (this.idEdtClientPFRG.getText().toString().isEmpty()){
            this.idEdtClientPFRG.setError("Informe o RG.");
            save = false;
        }else{
            this.idEdtClientPFRG.setError(null);
        }

        if (this.idEdtClientPFDate.getText().toString().isEmpty()){
            this.idEdtClientPFDate.setError("Informe a data de nascimento.");
            save = false;
        }else{
            this.idEdtClientPFDate.setError(null);
        }

        return save;
    }

    @Override
    public boolean onSave() {
        try{
            if (!this.onValidate()){ return  false;}

            Client client = new Client();
            client.setImage("");
            client.setType(1);

            client.setClientId(TB_CLIENT.Insert(client));

            client.getPhysicalPerson().setName(this.idEdtClientPFName.getText().toString());
            client.getPhysicalPerson().setNickname(this.idEdtClientPFNickName.getText().toString());
            client.getPhysicalPerson().setCPF(this.idEdtClientPFCPF.getText().toString());
            client.getPhysicalPerson().setRG(this.idEdtClientPFRG.getText().toString());
            client.getPhysicalPerson().setBirthDate(this.idEdtClientPFDate.getText().toString());
            switch (this.idSpnClientPFSexo.getSelectedItemPosition()){
                case 0:
                    client.getPhysicalPerson().setSex("I");
                    break;
                case 1:
                    client.getPhysicalPerson().setSex("F");
                    break;
                case 2:
                    client.getPhysicalPerson().setSex("M");
                    break;
            }

            TB_PHYSICAL_PERSON.Insert(client.getPhysicalPerson());

            return true;
        }catch (Exception ex){
            FunctionsApp.showSnackBarShort(getView(),ex.getMessage());
            return false;
        }
    }

    @Override
    public void onClear() {
        this.idEdtClientPFName.setError(null);
        this.idEdtClientPFNickName.setError(null);
        this.idEdtClientPFCPF.setError(null);
        this.idEdtClientPFRG.setError(null);
        this.idEdtClientPFDate.setError(null);
        this.idEdtClientPFName.setText("");
        this.idEdtClientPFNickName.setText("");
        this.idEdtClientPFCPF.setText("");
        this.idEdtClientPFRG.setText("");
        this.idSpnClientPFSexo.setSelection(0);
        this.idEdtClientPFDate.setText(FunctionsApp.getCurrentDate());
        this.idImgClientPFPhoto.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_client_circle));
    }

    /*Metodos para o DatePicker*/
    private View.OnClickListener onClickDate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    getActivity(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    onDateSetListener,
                    year, month, day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String data = String.valueOf(dayOfMonth) + "/"
                    + String.valueOf(monthOfYear + 1) + "/"
                    + String.valueOf(year);
            idEdtClientPFDate.setText(data);
        }
    };

    /* Metodos para a camera */
    private View.OnClickListener onClickTakePhoto = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            getPermissions();
        }
    };
}
