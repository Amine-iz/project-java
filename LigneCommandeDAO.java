package com.example.project_java.BD;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LigneCommandeDAO extends BaseDAO<LigneCommande>{

    public LigneCommandeDAO() throws SQLException {
        super();
    }

    @Override
    public void save(LigneCommande object) throws SQLException {
        String request = "INSERT INTO ligne_commande (id_commande, id_produit, quantite) VALUES (?, ?, ?)";

        this.preparedStatement = this.connection.prepareStatement(request);

        // Mapping objet table
        this.preparedStatement.setInt(1, object.getCommande().getId());
        this.preparedStatement.setInt(2, object.getProduit().getId_produit());
        this.preparedStatement.setInt(3, object.getQuantite());

        this.preparedStatement.execute();
    }

    @Override
    public void update(LigneCommande object) throws SQLException {
        String request = "UPDATE ligne_commande SET id_commande = ?, id_produit = ?, quantite = ? WHERE id_ligne = ?";

        this.preparedStatement = this.connection.prepareStatement(request);

        // Mapping objet table
        this.preparedStatement.setInt(1, object.getCommande().getId());
        this.preparedStatement.setInt(2, object.getProduit().getId_produit());
        this.preparedStatement.setInt(3, object.getQuantite());
        this.preparedStatement.setInt(4, object.getId());

        this.preparedStatement.execute();
    }

    @Override
    public void delete(LigneCommande object) throws SQLException {
        String request = "DELETE FROM ligne_commande WHERE id_ligne = ?";

        this.preparedStatement = this.connection.prepareStatement(request);

        // Mapping objet table
        this.preparedStatement.setInt(1, object.getId());

        this.preparedStatement.execute();
    }

    @Override
    public List<LigneCommande> getAll() throws SQLException {
        List<LigneCommande> mylist = new ArrayList<LigneCommande>();

        String request = "SELECT * FROM ligne_commande";

        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery(request);

        // Parcours de la table
        while (this.resultSet.next()) {

            // Mapping table objet
            CommandeDAO commandeDAO = new CommandeDAO();
            ProduitDAO produitDAO = new ProduitDAO();

            Commande commande = commandeDAO.getOne(resultSet.getLong("id_commande"));
            Produit produit = produitDAO.getOne(resultSet.getLong("id_produit"));

            mylist.add(new LigneCommande(
                    resultSet.getInt("id_ligne"),
                    commande,
                    produit,
                    resultSet.getInt("quantite")
            ));
        }

        return mylist;
    }

    @Override
    public LigneCommande getOne(Long id) throws SQLException {

        return null;
    }

}

