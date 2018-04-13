package com.cloudjibe.android_googlecloudfirestore;

import java.sql.Timestamp;
import java.util.Date;

public class SensorDataModel {
    Double humidity, temperature, ownerid;
    String sensorname;
    Date dateandtime;

    public SensorDataModel()
    {}

    public SensorDataModel(String sensorname, Double ownerid, Double humidity, Double temperature, Date dateandtime)
    {
        this.humidity = humidity;
        this.temperature = temperature;
        this.sensorname = sensorname;
        this.dateandtime = dateandtime;
        this.ownerid = ownerid;

    }

    public Double getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Double ownerid) {
        this.ownerid = ownerid;
    }

    public Date getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(Date dateandtime) {
        this.dateandtime = dateandtime;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getSensorname() {
        return sensorname;
    }

    public void setSensorname(String sensorname) {
        this.sensorname = sensorname;
    }
}
