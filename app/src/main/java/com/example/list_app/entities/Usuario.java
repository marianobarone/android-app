package com.example.list_app.entities;

public class Usuario {

    private String UID;
    private String mail;
    private String userName;
    private GrupoSeleccionado grupoSeleccionado;

    public Usuario(String UID, String mail, String userName, GrupoSeleccionado grupoSeleccionado) {
        this.UID = UID;
        this.mail = mail;
        this.userName = userName;
        this.grupoSeleccionado = grupoSeleccionado;
    }

    @Override
    public String toString() {
        return "Datos Usuario: {" +
                "UID='" + UID + '\'' +
                ", mail='" + mail + '\'' +
                ", userName='" + userName + '\'' +
                ", grupoSeleccionado=" + grupoSeleccionado.toString() +
                '}';
    }

    public Usuario() {

    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GrupoSeleccionado getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(GrupoSeleccionado grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }
}
