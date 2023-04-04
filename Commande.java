package com.example.project_java.BD;

import java.time.LocalDate;

public class Commande {
    private int id;
    private String date;
    private String status;
    private Double total;
    private int id_livreur;
    public Commande(int id, String date, String status, Double total, int id_livreur) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.total = total;
        this.id_livreur = id_livreur;
    }
    public int getId_livreur() {
        return id_livreur;
    }
    public void setId_livreur(int id_livreur) {
        this.id_livreur = id_livreur;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
}

