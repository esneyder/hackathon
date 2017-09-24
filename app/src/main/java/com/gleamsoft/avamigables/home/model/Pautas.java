package com.gleamsoft.avamigables.home.model;

import com.parse.ParseFile;

/**
 * Created by Developer on 23/09/2017.
 */

public class Pautas {

private String idempresa;
private String idpunto;
private String idpauta;
private String punto;
private ParseFile foto;
private String direccion;
private String titulo;
private String mensaje;
private Boolean estado;
private String dintance;
 

public Pautas() {
}

public Pautas(String idempresa, String idpunto, String idpauta, String punto, ParseFile foto, String direccion,
              String titulo, String mensaje, Boolean estado, String dintance) {
    this.idempresa = idempresa;
    this.idpunto = idpunto;
    this.idpauta = idpauta;
    this.punto = punto;
    this.foto = foto;
    this.direccion = direccion;
    this.titulo = titulo;
    this.mensaje = mensaje;
    this.estado = estado;
    this.dintance = dintance;
}

public String getIdempresa() {
    return idempresa;
}

public void setIdempresa(String idempresa) {
    this.idempresa = idempresa;
}

public String getIdpunto() {
    return idpunto;
}

public void setIdpunto(String idpunto) {
    this.idpunto = idpunto;
}

public String getIdpauta() {
    return idpauta;
}

public void setIdpauta(String idpauta) {
    this.idpauta = idpauta;
}

public String getPunto() {
    return punto;
}

public void setPunto(String punto) {
    this.punto = punto;
}

 

public ParseFile getFoto() {
    return foto;
}

public void setFoto(ParseFile foto) {
    this.foto = foto;
}

public String getDireccion() {
    return direccion;
}

public void setDireccion(String direccion) {
    this.direccion = direccion;
}

public String getTitulo() {
    return titulo;
}

public void setTitulo(String titulo) {
    this.titulo = titulo;
}

public String getMensaje() {
    return mensaje;
}

public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
}

public Boolean getEstado() {
    return estado;
}

public void setEstado(Boolean estado) {
    this.estado = estado;
}

public String getDintance() {
    return dintance;
}

public void setDintance(String dintance) {
    this.dintance = dintance;
}
}