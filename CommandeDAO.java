package com.example.project_java.BD;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO extends BaseDAO<Commande> {
    private Connection connexion;

    public CommandeDAO() throws SQLException {
        super();
    }
    @Override
    public void save(Commande object) throws SQLException {

        String request = "insert into commande (date, status, total_prix, id_livreur) values (?, ?, ?, ?)";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setString(1, object.getDate());
        this.preparedStatement.setString(2, object.getStatus());
        this.preparedStatement.setDouble(3, object.getTotal());
        this.preparedStatement.setInt(4, object.getId_livreur());

        this.preparedStatement.execute();

    }

    @Override
    public void update(Commande object) throws SQLException {
        String request = "UPDATE `commande` SET `date`=?,`status`=?,`total_prix`=?,`id_livreur`=? WHERE `id_commande`= ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setString(1, object.getDate());
        this.preparedStatement.setString(2, object.getStatus());
        this.preparedStatement.setDouble(3, object.getTotal());
        this.preparedStatement.setInt(4, object.getId_livreur());
        this.preparedStatement.setInt(5, object.getId());
        this.preparedStatement.execute();
    }

    @Override
    public void delete(Commande object) throws SQLException {
        String request = "DELETE FROM commande WHERE id_commande = ?";

        // mapping objet table

        this.preparedStatement = this.connection.prepareStatement(request);
        // mapping
        this.preparedStatement.setInt(1, object.getId());

        this.preparedStatement.execute();

    }

    @Override
    public List<Commande> getAll() throws SQLException {

        List<Commande> mylist = new ArrayList<Commande>();
        String request = "select * from commande ";
        this.statement = this.connection.createStatement();

        this.resultSet = this.statement.executeQuery(request);
        while (this.resultSet.next()) {
            mylist.add(new Commande(this.resultSet.getInt(1),
                    this.resultSet.getString(2),
                    this.resultSet.getString(3),
                    this.resultSet.getDouble(4),
                    this.resultSet.getInt(5)));
        }

        return mylist;
    }

    @Override
    public Commande getOne(Long id) throws SQLException {
        String request = "select * from commande WHERE id_commande = ?";
        this.preparedStatement = this.connection.prepareStatement(request);
        this.preparedStatement.setLong(1, id);
        this.resultSet = this.preparedStatement.executeQuery();
        if (this.resultSet.next()) {
            return new Commande(this.resultSet.getInt(1),
                    this.resultSet.getString(2),
                    this.resultSet.getString(3),
                    this.resultSet.getDouble(4),
                    this.resultSet.getInt(5));
        }
        return null;
    }
}