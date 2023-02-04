package com.chris.proyectofinal;

public class DataModal {
    private String name;
    private String usuario;

    public DataModal(String name, String usuario) {
        this.name = name;
        this.usuario = usuario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
