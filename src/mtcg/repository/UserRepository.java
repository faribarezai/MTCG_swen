package mtcg.repository;

import mtcg.model.Card;
import mtcg.model.User;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private UnitOfWork unitOfWork= new UnitOfWork();

    public UserRepository(UnitOfWork unitOfWork) {

        this.unitOfWork = unitOfWork;
    }

    public UserRepository() {}

    public boolean userExists(User user) {
        String sql = "SELECT COUNT(*) FROM mUser WHERE username = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count >0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return false;
    }


    //check User exists with name +psw for login
    public boolean userLogged(User user) {

        String sql = "SELECT COUNT(*) FROM mUser WHERE username = ? AND password= ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count >0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //saved User
    public void saveUser(User user) {

        String sql = "INSERT INTO mUser (username, password, coins, elo) VALUES (?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)){
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getCoins());
            preparedStatement.setInt(4, user.getElo());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//Update User
    public void updateCoinOfUser(User user) {
        String sql = "UPDATE mUser SET coins=? WHERE username = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getCoins());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error updating User", e);
        }
    }


    public User findByUsername(String username) {
        String sql = "SELECT * FROM mUser WHERE username = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Extract user information from the result set
                    int userId = resultSet.getInt("userId");
                    String password = resultSet.getString("password");
                    int elo = resultSet.getInt("elo");
                    List<Card> deck = new ArrayList<>();
                    List<Card> stack = new ArrayList<>();
                    int coins = resultSet.getInt("coins");
                    // Create and return a User object
                    return new User(username, password, deck, stack,coins, elo);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding User by username", e);
        }

        // Return null if no user is found
        return null;
    }
}
