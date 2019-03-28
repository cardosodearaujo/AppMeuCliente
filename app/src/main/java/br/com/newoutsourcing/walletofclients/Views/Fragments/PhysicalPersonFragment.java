package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.R;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;

public class PhysicalPersonFragment extends Fragment {

    private Toolbar idToolbar;
    private TextView idTxwClientPFDescriptionData;
    private EditText idEdtClientPFName;
    private EditText idEdtClientPFNickName;
    private EditText idEdtClientPFCPF;
    private EditText idEdtClientPFRG;
    private Spinner idSpnClientPFSexo;
    private TextView idTxwClientPFDescriptionAdditionalData;
    private EditText idEdtClientPFSite;
    private EditText idEdtClientPFObservation;
    private ViewPager idViewPager;
    private EditText idEdtClientPFDate;
    private LinearLayout idLLClientPFTakePhoto;
    private CircleImageView idImgClientPFPhoto;

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
        this.LoadConfigurationToView(view);
        this.LoadInformationToView();
        return view;
    }

    public static PhysicalPersonFragment newInstance() {
        return new PhysicalPersonFragment();
    }

    private void LoadConfigurationToView(View view){
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idViewPager = this.getActivity().findViewById(R.id.idViewPager);
        this.idEdtClientPFName = view.findViewById(R.id.idEdtClientPFName);
        this.idEdtClientPFNickName = view.findViewById(R.id.idEdtClientPFNickName);
        this.idEdtClientPFCPF = view.findViewById(R.id.idEdtClientPFCPF);
        this.idEdtClientPFRG = view.findViewById(R.id.idEdtClientPFRG);
        this.idSpnClientPFSexo = view.findViewById(R.id.idSpnClientPFSexo);
        this.idEdtClientPFSite = view.findViewById(R.id.idEdtClientPFSite);
        this.idEdtClientPFObservation = view.findViewById(R.id.idEdtClientPFObservation);
        this.idEdtClientPFDate = view.findViewById(R.id.idEdtClientPFDate);
        this.idLLClientPFTakePhoto = view.findViewById(R.id.idLLClientPFTakePhoto);
        this.idImgClientPFPhoto = view.findViewById(R.id.idImgClientPFPhoto);
    }

    private void LoadInformationToView(){
        this.idToolbar.setSubtitle("Pessoa física");
        this.idEdtClientPFDate.addTextChangedListener(new MaskEditTextChangedListener("##/##/####", this.idEdtClientPFDate));
        this.idEdtClientPFCPF.addTextChangedListener(new MaskEditTextChangedListener("###.###.###-##", this.idEdtClientPFCPF));
        this.idEdtClientPFDate.setText(FunctionsApp.getCurrentDate());
        this.idEdtClientPFDate.setOnClickListener(this.onClickDate);
        this.idLLClientPFTakePhoto.setOnClickListener(this.onClickTakePhoto);
        this.idImgClientPFPhoto.setOnClickListener(this.onClickTakePhoto);
    }

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

    private View.OnClickListener onClickTakePhoto = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            getPermissions();
        }
    };

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else{
            this.takePhoto();
        }
    }

    private void takePhoto() {
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
                        intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent,FunctionsApp.IMAGEM_INTERNA);
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
                this.takePhoto();
            } else {
                FunctionsApp.snackBarShort(this.getView(),"Permissão negada!");
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
                    Cursor cursor = getActivity().getContentResolver().query(imagemSelecionada, colunas, null, null, null);
                    cursor.moveToFirst();

                    int indexColuna = cursor.getColumnIndex(colunas[0]);
                    String pathImg = cursor.getString(indexColuna);
                    cursor.close();

                    bitmap = BitmapFactory.decodeFile(pathImg);
                    if (bitmap != null){
                        this.idImgClientPFPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        this.idImgClientPFPhoto.setImageBitmap(bitmap);
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
            FunctionsApp.snackBarShort(this.getView(),ex.getMessage());
        }
    }
}
