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
import br.com.newoutsourcing.walletofclients.Views.Bases.TableConfigurationBase;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDITIONAL_INFORMATION;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_ADDRESS;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_PHYSICAL_PERSON;

public class ClientTable extends TableConfigurationBase<Client> {

    public enum Fields {
        ID_CLIENT,
        IMAGE,
        TYPE
    }

    public ClientTable(Context context){
        super(context);
        super.Table = "TB_CLIENT";
    }

    public static ClientTable newInstance(Context context){
        return new ClientTable(context);
    }

    @Override
    public List<Client> Select(long clientId){
        this.SQL
                = " Select " + this.getFields() + " From " + this.Table
                + " Where " + Fields.ID_CLIENT.name() + " = " + clientId
                + " Order by " + Fields.ID_CLIENT.name();
        return this.Consulta(SQL);
    }

    public List<Client> Select(String search){
        this.SQL
                = " Select " + this.getFields() + " From " + this.Table
                + " Left Join " + TB_PHYSICAL_PERSON.Table + " On " + this.Table + "." + Fields.ID_CLIENT.name() + " = " + TB_PHYSICAL_PERSON.Table + "." + PhysicalPersonTable.Fields.ID_CLIENT.name()
                + " Left Join " + TB_LEGAL_PERSON.Table + " On " + this.Table + "." + Fields.ID_CLIENT.name() + " = " + TB_LEGAL_PERSON.Table + "." + LegalPersonTable.Fields.ID_CLIENT.name();
                if (!search.isEmpty()){
                    this.SQL
                            = this.SQL
                            + " Where " + TB_PHYSICAL_PERSON.Table + "." + PhysicalPersonTable.Fields.NAME.name() + " Like '%" + QuoteParam(search) + "%'"
                            + " Or " + TB_PHYSICAL_PERSON.Table + "." + PhysicalPersonTable.Fields.NICKNAME.name() + " Like '%"+ QuoteParam(search) +"%'"
                            + " Or " + TB_PHYSICAL_PERSON.Table + "." + PhysicalPersonTable.Fields.CPF.name() + " Like '%"+ QuoteParam(search) +"%'"
                            + " Or " + TB_PHYSICAL_PERSON.Table + "." + PhysicalPersonTable.Fields.RG.name() + " Like '%"+ QuoteParam(search) +"%'"
                            + " Or " + TB_LEGAL_PERSON.Table + "."+ LegalPersonTable.Fields.SOCIAL_NAME.name() + " Like '%"+QuoteParam(search)+"%'"
                            + " Or " + TB_LEGAL_PERSON.Table + "."+ LegalPersonTable.Fields.FANTASY_NAME.name() + " Like '%" + QuoteParam(search) + "%'"
                            + " Or " + TB_LEGAL_PERSON.Table + "."+ LegalPersonTable.Fields.CNPJ.name()+ " Like '%" + QuoteParam(search) + "%'"
                            + " Or " + TB_LEGAL_PERSON.Table + "."+ LegalPersonTable.Fields.IE.name() + " Like '%" + QuoteParam(search) + "%'";
                }

        return this.Consulta(SQL);
    }

    @Override
    public List<Client> Select(){
        this.SQL
                = " Select " + this.getFields() + " From " + this.Table
                + " Order by " + Fields.ID_CLIENT.name();
        return this.Consulta(SQL);
    }

    private List<Client> Consulta(String SQL){
        if (SQL.isEmpty()) return new ArrayList<Client>();
        super.openDatabaseInstance();
        try{
            List<Client> list = new ArrayList();

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

                    List<Address> addressList = TB_ADDRESS.Select(client.getClientId());
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    protected String getFields(){
        String StringFields = "";

        for(Fields Field: Fields.values()){
            StringFields += "TB_CLIENT." + Field.name() + ",";
        }

        if (StringFields.length() > 0){
            StringFields = StringFields.substring(0,StringFields.length()-1);
        }else{
            StringFields = "*";
        }

        return StringFields;
    }
}
