package br.com.newoutsourcing.walletofclients.App;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FunctionsApp {

    public static int IMAGEM_CAMERA = 0;
    public static int IMAGEM_INTERNA = 1;
    public static ProgressDialog PG_DIALOG;
    public static final String MASCARA_TELEFONE = "(##)####-####";
    public static final String MASCARA_CELULAR = "(##)#####-####";
    public static final String MASCARA_CPF = "###.###.###-##";
    public static final String MASCARA_CNPJ = "##.###.###/####-##";
    public static final String MASCARA_CEP = "##.###-###";
    public static final String MASCARA_DATA = "##/##/####";

    public static void startActivity(Context context, Class classe, Bundle paramentros){
        Intent intent = new Intent(context,classe);
        if (paramentros != null){intent.putExtras(paramentros);}
        context.startActivity(intent);
    }

    public static void closeActivity(Context context){
        ((Activity) context).finish();
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

    public static AlertDialog modal(Context context, String titulo, String menssagem, String mensagemBotao){
        return new AlertDialog.Builder(context)
                .setTitle(titulo)
                .setMessage(menssagem)
                .setNeutralButton(mensagemBotao, null).show();
    }

    public static void startFragment(Fragment fragment, int id, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public static void snackBarShort(View view,String message){
        Snackbar.make(view,message,Snackbar.LENGTH_SHORT).show();
    }

    public static void snackBarLong(View view,String message){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show();
    }

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

    public static void hiddenKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
