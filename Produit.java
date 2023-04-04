package com.example.project_java.BD;

public class Produit {
    private int id_produit;
    private double pv;
    private String nom, description;

    public Produit(int id_produit, String nom, double pv, String description) {
        this.id_produit = id_produit;
        this.pv = pv;
        this.nom = nom;
        this.description = description;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public double getPv() {
        return pv;
    }

    public void setPv(double pv) {
        this.pv = pv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public String toString() {
        return "Livreur{" +
                "id_produit=" + id_produit +
                ", nom='" + nom + '\'' +
                ", pv='" + pv + '\'' +
                ", Description='" + description + '\'' +
                '}';
    }
}
