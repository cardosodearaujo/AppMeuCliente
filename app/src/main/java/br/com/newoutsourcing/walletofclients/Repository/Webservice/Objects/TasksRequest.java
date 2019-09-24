package br.com.newoutsourcing.walletofclients.Repository.Webservice.Objects;

import br.com.newoutsourcing.walletofclients.Objects.Tasks;

public class TasksRequest {
    private long Codigo;
    private String Title;
    private long CodigoClient;
    private long AllDay;
    private String Date;
    private String Hour;
    private String Observation;

    public long getCodigo() {
        return Codigo;
    }

    public void setCodigo(long codigo) {
        Codigo = codigo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public long getCodigoClient() {
        return CodigoClient;
    }

    public void setCodigoClient(long codigoClient) {
        CodigoClient = codigoClient;
    }

    public long getAllDay() {
        return AllDay;
    }

    public void setAllDay(long allDay) {
        AllDay = allDay;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public TasksRequest convert(Tasks t){
        TasksRequest tasks = new TasksRequest();

        if (t.getIdNuvem() > 0) tasks.setCodigo(t.getIdNuvem());
        tasks.setTitle(t.getTitle());
        tasks.setCodigo(t.getClienteId());
        tasks.setAllDay(t.getAllDay());
        tasks.setDate(t.getDate());
        tasks.setHour(t.getHour());
        tasks.setObservation(t.getObservation());
        return tasks;
    }
}
