package com.gleamsoft.avamigables.home.model;

/**
 * Created by Developer on 24/09/2017.
 */

public class metroPlus
{
private String id;
private String busId;
private int distanciaSolicitud;
private String direccionSolicitud;
private String fecha;
private Boolean valorPrioridad;
private int minutosRetraso;
private int tiempoMedio;
private String sentido;

public metroPlus(String id, String busId, int distanciaSolicitud, String direccionSolicitud, String fecha, Boolean valorPrioridad, int minutosRetraso, int tiempoMedio, String sentido) {
    this.id = id;
    this.busId = busId;
    this.distanciaSolicitud = distanciaSolicitud;
    this.direccionSolicitud = direccionSolicitud;
    this.fecha = fecha;
    this.valorPrioridad = valorPrioridad;
    this.minutosRetraso = minutosRetraso;
    this.tiempoMedio = tiempoMedio;
    this.sentido = sentido;
}

public String getId() {
    return id;
}

public void setId(String id) {
    this.id = id;
}

public String getBusId() {
    return busId;
}

public void setBusId(String busId) {
    this.busId = busId;
}

public int getDistanciaSolicitud() {
    return distanciaSolicitud;
}

public void setDistanciaSolicitud(int distanciaSolicitud) {
    this.distanciaSolicitud = distanciaSolicitud;
}

public String getDireccionSolicitud() {
    return direccionSolicitud;
}

public void setDireccionSolicitud(String direccionSolicitud) {
    this.direccionSolicitud = direccionSolicitud;
}

public String getFecha() {
    return fecha;
}

public void setFecha(String fecha) {
    this.fecha = fecha;
}

public Boolean getValorPrioridad() {
    return valorPrioridad;
}

public void setValorPrioridad(Boolean valorPrioridad) {
    this.valorPrioridad = valorPrioridad;
}

public int getMinutosRetraso() {
    return minutosRetraso;
}

public void setMinutosRetraso(int minutosRetraso) {
    this.minutosRetraso = minutosRetraso;
}

public int getTiempoMedio() {
    return tiempoMedio;
}

public void setTiempoMedio(int tiempoMedio) {
    this.tiempoMedio = tiempoMedio;
}

public String getSentido() {
    return sentido;
}

public void setSentido(String sentido) {
    this.sentido = sentido;
}
}
