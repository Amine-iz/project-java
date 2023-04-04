package com.example.project_java.BD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO extends BaseDAO<Login> {

    public LoginDAO() throws SQLException {
        super();
    }

    @Override
    public void save(Login login) throws SQLException {
        String query = "INSERT INTO login (username, password) VALUES (?, ?)";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login.getUsername());
        preparedStatement.setString(2, login.getPassword());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(Login login) throws SQLException {
        String query = "UPDATE login SET username = ?, password = ? WHERE id_login = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, login.getUsername());
        preparedStatement.setString(2, login.getPassword());
        preparedStatement.setLong(3, login.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Login login) throws SQLException {
        String query = "DELETE FROM login WHERE id_login = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, login.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Login> getAll() throws SQLException {
        String query = "SELECT * FROM login";
        statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        List<Login> logins = new ArrayList<>();
        while (resultSet.next()) {
            Login login = new Login(resultSet.getInt("id_login"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
            logins.add(login);
        }
        return logins;
    }

    @Override
    public Login getOne(Long id) throws SQLException {
        String query = "SELECT * FROM login WHERE id_login = ?";
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return new Login(resultSet.getInt("id_login"),
                    resultSet.getString("username"),
                    resultSet.getString("password"));
        }
        return null;
    }

    public Login getLoginByUsernameAndPassword(String username, String password) throws SQLException {
        String query = "SELECT * FROM login WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String retrievedUsername = resultSet.getString("username");
                    String retrievedPassword = resultSet.getString("password");

                    return new Login(id, retrievedUsername, retrievedPassword);
                } else {
                    return null;
                }
            }
        }
    }
}

