package com.example.project_java.BD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LivreurDAO extends BaseDAO<Livreur>{
    public LivreurDAO() throws SQLException {

        super();
    }

    @Override
    public void save(Livreur object) throws SQLException {

        String request = "insert into livreur (nom , telephone) values (? , ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1 , object.getNom());

        this.preparedStatement.setString(2 , object.getTelephone());


        this.preparedStatement.execute();

    }

    @Override
    public void update(Livreur object) throws SQLException {
        String request ="UPDATE `livreur` SET `id_livreur`= ?, `nom`= ?, `telephone`= ? WHERE `id_livreur`= ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, object.getId_livreur());
        this.preparedStatement.setString(2, object.getNom());
        this.preparedStatement.setString(3, object.getTelephone());
        this.preparedStatement.setLong(4, object.getId_livreur());
        this.preparedStatement.execute();


    }

    @Override
    public void delete(Livreur object) throws SQLException {
        String request = "DELETE FROM livreur WHERE id_livreur = ?";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setLong(1 , object.getId_livreur());


        this.preparedStatement.execute();

    }

    @Override
    public List<Livreur> getAll()  throws SQLException {

        List<Livreur> mylist = new ArrayList<Livreur>();

        String request = "select * from livreur ";

        this.statement = this.connection.createStatement();

        this.resultSet =   this.statement.executeQuery(request);

// parcours de la table
        while ( this.resultSet.next()){

// mapping table objet
            mylist.add(new Livreur(this.resultSet.getLong(1) ,
                    this.resultSet.getString(2) , this.resultSet.getString(3)));


        }


        return mylist;
    }

    @Override
    public Livreur getOne(Long id) throws SQLException {
        String request = "select * from livreur WHERE id_livreur = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        if (this.resultSet.next()) {
            return new Livreur(this.resultSet.getLong(1),
                    this.resultSet.getString(2),
                    this.resultSet.getString(3));
        }
        return null;
    }

    public boolean exists(int id) throws SQLException {
        String request = "select * from livreur WHERE id_livreur = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        return this.resultSet.next();
    }
}
