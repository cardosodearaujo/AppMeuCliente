package br.com.newoutsourcing.walletofclients.Repository.Database.Tables;

import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.AdditionalInformation;
import br.com.newoutsourcing.walletofclients.Objects.Address;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Objects.LegalPerson;
import br.com.newoutsourcing.walletofclients.Objects.PhysicalPerson;
import br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.TableConfigurationDatabase;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class TBClientDatabase extends TableConfigurationDatabase {

    private enum Fields {
        ID_CLIENT,
        IMAGE,
        TYPE
    }

    public TBClientDatabase(Context context){
        super(context);
        super.Table = "TB_CLIENT";
    }

    public static TBClientDatabase newInstance(Context context){
        return new TBClientDatabase(context);
    }

    @Override
    public List<Client> Select(long clientId){
        super.openDatabaseInstance();
        try{
            List<Client> list = new ArrayList<Client>();

            if (clientId > 0){
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Where " + Fields.ID_CLIENT.name() + " = " + clientId
                        + " Order by " + Fields.ID_CLIENT.name();
            }else{
                this.SQL
                        = " Select " + this.getFields() + " From " + this.Table
                        + " Order by " + Fields.ID_CLIENT.name();
            }

            this.cursor = this.database.rawQuery(this.SQL,null);

            if (this.cursor.getCount()>0){
                this.cursor.moveToFirst();
                Client client;
                do{
                    client = new Client();

                    client.setClientId(this.cursor.getInt(0));
                    client.setImage(this.cursor.getString(1));
                    client.setType(this.cursor.getInt(2));

                    List<PhysicalPerson> physicalPersonList = TB_PHYSICAL_PERSON.Select(client.getClientId());
                    if (physicalPersonList != null && physicalPersonList.size() > 0 && physicalPersonList.get(0).getPhysicalPersonId() >= 0){
                        client.setPhysicalPerson(physicalPersonList.get(0));
                    }

                    List<LegalPerson> legalPersonList = TB_LEGAL_PERSON.Select(client.getClientId());
                    if (legalPersonList != null && legalPersonList.size() > 0 && legalPersonList.get(0).getLegalPersonId() >= 0){
                        client.setLegalPerson(legalPersonList.get(0));
                    }

                    List<Address> addressList = TB_ADDRESS.Select(clientId);
                    if (addressList != null && addressList.size() > 0 && addressList.get(0).getAddressId() >= 0){
                        client.setAddress(addressList.get(0));
                    }

                    List<AdditionalInformation> additionalInformationList = TB_ADDITIONAL_INFORMATION.Select(client.getClientId());
                    if (additionalInformationList != null && additionalInformationList.size() > 0 && additionalInformationList.get(0).getAdditionalInformationId() >= 0){
                        client.setAdditionalInformation(additionalInformationList.get(0));
                    }

                    list.add(client);
                }while (this.cursor.moveToNext());
            }

            this.cursor.close();

            return list;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public long Insert(Client client){
        super.openDatabaseInstance();
        try{
            ContentValues values = new ContentValues();

            values.put(Fields.IMAGE.name(),client.getImage());
            values.put(Fields.TYPE.name(),client.getType());

            return this.database.insert(this.Table,null,values);

        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Update(Client client){
        super.openDatabaseInstance();
        try {
            ContentValues values = new ContentValues();

            values.put(Fields.IMAGE.name(), client.getImage());
            values.put(Fields.TYPE.name(), client.getType());

            this.database.update(this.Table, values,
                    Fields.ID_CLIENT.name() + " = " + client.getClientId(),
                    null);

            return true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    public Boolean Delete(Client client){
        super.openDatabaseInstance();
        try {
            if (client.getClientId() > 0) {
                if (client.getAddress() != null) TB_ADDRESS.Delete(client.getAddress());
                if (client.getAdditionalInformation() != null) TB_ADDITIONAL_INFORMATION.Delete(client.getAdditionalInformation());
                if (client.getType() == 1 && client.getPhysicalPerson() != null){
                    TB_PHYSICAL_PERSON.Delete(client.getPhysicalPerson());
                }else if (client.getType() == 2 && client.getLegalPerson() != null) {
                    TB_LEGAL_PERSON.Delete(client.getLegalPerson());
                }

                this.database.delete(this.Table,
                        Fields.ID_CLIENT.name() + " = " + client.getClientId(),
                        null);
            }
            return  true;
        }catch (Exception ex){
            throw ex;
        }finally {
            super.closeDatabaseInstance();
        }
    }

    private String getFields(){
        String StringFields = "";

        for(Fields Field: Fields.values()){
            StringFields += Field.name() + ",";
        }

        if (StringFields.length() > 0){
            StringFields = StringFields.substring(0,StringFields.length()-1);
        }else{
            StringFields = "*";
        }

        return StringFields;
    }
}
