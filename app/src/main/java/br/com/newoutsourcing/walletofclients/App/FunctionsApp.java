package br.com.newoutsourcing.walletofclients.App;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.newoutsourcing.walletofclients.R;

public class FunctionsApp {
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
    public static final String MASCARA_HORA = "##:##";

    /**Funções de Layout**/
    public static void startActivity(Context context, Class classe, Bundle paramentros){
        Intent intent = new Intent(context,classe);
        if (paramentros != null){intent.putExtras(paramentros);}
        context.startActivity(intent);
    }

    public static void closeActivity(Context context){
        ((Activity) context).finish();
    }

    public static void startFragment(Fragment fragment, int id, FragmentManager fragmentManager, Bundle bundle){
        if (bundle != null) fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id,fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public static void showPgDialog(Context context, String Title){
        if (Title == null) Title = "Processo em andamento";
        PG_DIALOG = new ProgressDialog(context);
        PG_DIALOG.setTitle(Title);
        PG_DIALOG.setIndeterminate(false);
        PG_DIALOG.setCancelable(false);
        PG_DIALOG.setProgress(0);
        PG_DIALOG.show();
    }

    public static void closePgDialog(){
        if (PG_DIALOG.isShowing()){ PG_DIALOG.dismiss();}
    }

    public static AlertDialog showAlertDialog(Context context, String titulo, String menssagem, String mensagemBotao){
        return new AlertDialog.Builder(context, R.style.Theme_MaterialComponents_Light_Dialog)
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

    public static void showKeybord(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static String createFolder(String path){
        File rootPath = new File(Environment.getExternalStorageDirectory(), path);
        if(!rootPath.exists()){rootPath.mkdirs();}
        return rootPath.getAbsoluteFile().toString();
    }

    public static String saveImage(Bitmap bitmap){
        try{
            FunctionsApp.createFolder("WalletOfClients");
            String  path = FunctionsApp.createFolder("WalletOfClients/Images");
            String name =  "Image_" +  String.valueOf(Math.random()) + ".png";
            File file = new File(path,name);
            if (file.exists())file.delete();

            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,outputStream);
            outputStream.flush();
            outputStream.close();
            return file.getAbsolutePath();
        }catch (IOException ex){
            return "";
        }
    }

    public static String saveArchive(String archive, String name){
        try{
            String path = FunctionsApp.createFolder("WalletOfClients");
            File file = new File(path,name);
            if (file.exists())file.delete();

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(archive);
            writer.flush();
            writer.close();
            return file.getCanonicalFile().getAbsolutePath();
        }catch (IOException ex){
            return "";
        }
    }

    public static String formatCNPJ(String CNPJ){
        return CNPJ
                .replace(".","")
                .replace("/","")
                .replace("-","");
    }

    public static String formatCPF(String CPF){
        return CPF
                .replace(".","")
                .replace("-","");
    }

    public static String formatCellphone(String cellphone){
        return  cellphone
                .replace("(","")
                .replace(")","")
                .replace("-","");
    }

    public static String formatTelephone(String phone){
        return phone
                .replace("(","")
                .replace(")","")
                .replace("-","");
    }

    public static String formatCEP(String CEP){
        return CEP
                .replace(".","")
                .replace("-","");
    }

    /**Funções de data**/
    public static String getCurrentDate(){
        return getCurrentDate("dd/MM/yyyy");
    }

    public static String getCurrentTime(){
        return getCurrentTime("kk:mm");
    }

    public static String getCurrentDate(String format){
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
        return formatDate.format(data);
    }

    public  static String getCurrentTime(String format){
        Date data = new Date(System.currentTimeMillis());
        SimpleDateFormat formatDate = new SimpleDateFormat(format);
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
    public static String parseBitmapToBase64(Bitmap bitmap){
        try{
            if (bitmap == null){return "";}
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, stream);
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

    /**Email**/
    public static void SendMail(){
        try {
            SimpleEmail email = new SimpleEmail();
            email.setSSLOnConnect(true);
            email.setHostName( "smtp.gmail.com" );
            email.setSslSmtpPort( "465" );
            email.setAuthenticator( new DefaultAuthenticator( "everaldocardosodearaujo@gmail.com" ,  "M1n3Rv@7" ) );
            email.setFrom( "everaldocardosodearaujo@gmail.com");
            email.setDebug(true);
            email.setSubject( "Email de teste do app" );
            email.setMsg( "Vou te mandar usuarios por aqui." );
            email.addTo( "everaldocardosodearaujo@gmail.com" );
            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
