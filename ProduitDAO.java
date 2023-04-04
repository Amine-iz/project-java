package com.example.project_java.BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO extends BaseDAO<Produit>{
    public ProduitDAO() throws SQLException {

        super();
    }
    @Override
    public void save(Produit object) throws SQLException {

        String request = "insert into produit (nom_prod , pv_prod, description_prod) values (? ,? , ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNom());

        this.preparedStatement.setDouble(2 , object.getPv());
        this.preparedStatement.setString(3 , object.getDescription());


        this.preparedStatement.execute();

    }

    @Override
    public void update(Produit object) throws SQLException {
        String request ="UPDATE `produit` SET `id_produit`= ?, `nom_prod`= ?, `pv_prod`=? , `description_prod`= ? WHERE `id_produit`= ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_produit());
        this.preparedStatement.setString(2, object.getNom());
        this.preparedStatement.setDouble(3, object.getPv());
        this.preparedStatement.setString(4, object.getDescription());
        this.preparedStatement.setDouble(5, object.getId_produit());
        this.preparedStatement.execute();
    }

    @Override
    public void delete(Produit object) throws SQLException {
        String request = "DELETE FROM produit WHERE id_produit = ?";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setInt(1 , object.getId_produit());

        this.preparedStatement.execute();
    }

    @Override
    public List<Produit> getAll()  throws SQLException {

        List<Produit> mylist = new ArrayList<Produit>();

        String request = "select * from produit ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
            mylist.add(new Produit(this.resultSet.getInt(1)  ,this.resultSet.getString(2),
                    this.resultSet.getDouble(3), this.resultSet.getString(4)));


        }


        return mylist;
    }

    @Override
    public Produit getOne(Long id) throws SQLException {
        String request = "select * from produit WHERE id_produit = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        if (this.resultSet.next()) {
            return new Produit(this.resultSet.getInt(1)  ,this.resultSet.getString(2),
                    this.resultSet.getDouble(3), this.resultSet.getString(4));
        }
        return null;
    }
}
