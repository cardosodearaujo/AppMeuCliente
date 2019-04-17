package br.com.newoutsourcing.walletofclients.App;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.newoutsourcing.walletofclients.Views.Activitys.ErrorActivity;

public class FunctionsApp {
    /**Controles do banco de dados**/
    public static final String DB_NAME = "BD_WalletOfClients";
    public static final int DB_VERSION = 1;

    /**Controles da camera**/
    public static int IMAGEM_CAMERA = 0;
    public static int IMAGEM_INTERNA = 1;

    /**Controles de layout**/
    public static ProgressDialog PG_DIALOG;
    public static final String MASCARA_TELEFONE = "(##)####-####";
    public static final String MASCARA_CELULAR = "(##)#####-####";
    public static final String MASCARA_CPF = "###.###.###-##";
    public static final String MASCARA_CNPJ = "##.###.###/####-##";
    public static final String MASCARA_CEP = "##.###-###";
    public static final String MASCARA_DATA = "##/##/####";

    /**Funções de Layout**/
    public static void startActivity(Context context, Class classe, Bundle paramentros){
        Intent intent = new Intent(context,classe);
        if (paramentros != null){intent.putExtras(paramentros);}
        context.startActivity(intent);
    }

    public static void closeActivity(Context context){
        ((Activity) context).finish();
    }

    public static void startFragment(Fragment fragment, int id, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public static void showPgDialog(Context context){
        PG_DIALOG = new ProgressDialog(context);
        PG_DIALOG.setMessage("Aguarde...");
        PG_DIALOG.setIndeterminate(false);
        PG_DIALOG.setCancelable(false);
        PG_DIALOG.setProgress(0);
        PG_DIALOG.show();
    }

    public static void closePgDialog(){
        if (PG_DIALOG.isShowing()){ PG_DIALOG.dismiss();}
    }

    public static AlertDialog showAlertDialog(Context context, String titulo, String menssagem, String mensagemBotao){
        return new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(menssagem)
                .setNeutralButton(mensagemBotao, null).show();
    }

    public static void showSnackBarShort(View view, String message){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackBarLong(View view, String message){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show();
    }

    public static void closeKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showMessageError(Context context,String title, String message){
        Bundle bundle = new Bundle();
        bundle.putString("Title",title);
        bundle.putString("Message",message);
        startActivity(context, ErrorActivity.class, bundle);
    }

    /**Funções de data**/
    public static String getCurrentDate(){
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
        return formatDate.format(data);
    }

    public static String formatDate(String date){
        try{
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date data = format.parse(date);
            format.applyPattern("dd-MM-yyyy HH:mm:ss");
            return format.format(data);
        }catch (Exception ex){
            return "";
        }
    }

    public static Date parseDate(String myDate){
        Date date = new Date();
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = (Date)formatter.parse(myDate);
            return date;
        } catch (ParseException e) {}
        return date;
    }

    /**Tratamento de imagem**/
    public static String parseBitmapToBase64(Bitmap bpm){
        try{
            if (bpm == null){return "";}
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bpm.compress(Bitmap.CompressFormat.JPEG,100, stream);
            byte[] byteArray = stream.toByteArray();
            String base64 = Base64.encodeToString(byteArray,Base64.NO_WRAP);
            return "data:image/jpg;base64," + base64;
        }catch (Exception ex){return null;}
    }

    public static Bitmap parseBase64ToBitmap(String b64){
        try{
            if (b64.isEmpty()) return null;
            byte[] decodedString = Base64.decode(b64.split(",")[1], Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedByte;
        }catch (Exception ex){
            return null;
        }
    }

    public static byte[] parseBitmapToByte(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap parseByteToBitmap(byte[] bites){
        return BitmapFactory.decodeByteArray(bites, 0, bites.length);
    }

    /**Indices de Spinner**/
    public static int getState(String UF){
        int Index = 0;
        switch (UF){
            case "AC":
                 Index = 0;
                 break;
            case "AL":
                Index = 1;
                break;
            case "AP":
                Index = 2;
                break;
            case "AM":
                Index = 3;
                break;
            case "BA":
                Index = 4;
                break;
            case "CE":
                Index = 5;
                break;
            case "DF":
                Index = 6;
                break;
            case "ES":
                Index = 7;
                break;
            case "GO":
                Index = 8;
                break;
            case "MA":
                Index = 9;
                break;
            case "MT":
                Index = 10;
                break;
            case "MS":
                Index = 11;
                break;
            case "MG":
                Index = 12;
                break;
            case "PA":
                Index = 13;
                break;
            case "PB":
                Index = 14;
                break;
            case "PR":
                Index = 15;
                break;
            case "PE":
                Index = 16;
                break;
            case "PI":
                Index = 17;
                break;
            case "RJ":
                Index = 18;
                break;
            case "RN":
                Index = 19;
                break;
            case "RS":
                Index = 20;
                break;
            case "RO":
                Index = 21;
                break;
            case "RR":
                Index = 22;
                break;
            case "SC":
                Index = 23;
                break;
            case "SP":
                Index = 24;
                break;
            case "SE":
                Index = 25;
                break;
            case "TO":
                Index = 26;
                break;
        }
        return Index;
    }

    public static int getSex(String Sex){
        int Index = 0;
        switch (Sex){
            case "I":
                Index = 0;
                break;
            case "F":
                Index = 1;
                break;
            case "M":
                Index = 2;
                break;
        }
        return Index;
    }
}
