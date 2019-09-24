package br.com.newoutsourcing.walletofclients.Objects;

import java.io.Serializable;

public class Tasks implements Serializable {
    private long tasksId;
    private long clienteId;
    private String title;
    private int allDay;
    private String date;
    private String hour;
    private String observation;
    private long idNuvem;
    private String update;

    public Tasks(){
        this.tasksId = 0;
        this.clienteId = 0;
        this.title = "";
        this.allDay = 0;
        this.date = "";
        this.hour = "";
        this.observation = "";
        this.idNuvem = 0;
        this.update = "";
    }

    public Tasks(long tasksId, long clienteId, String title, int allDay, String date, String hour, String observation, long idNuvem, String update){
        this.tasksId = tasksId;
        this.clienteId = clienteId;
        this.title = title;
        this.allDay = allDay;
        this.date = date;
        this.hour = hour;
        this.observation = observation;
        this.idNuvem = idNuvem;
        this.update = update;
    }

    public long getTasksId() {
        return tasksId;
    }

    public void setTasksId(long tasksId) {
        this.tasksId = tasksId;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAllDay() {
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public long getIdNuvem() {
        return idNuvem;
    }

    public void setIdNuvem(long idNuvem) {
        this.idNuvem = idNuvem;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
