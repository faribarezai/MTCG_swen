package mtcg.repository;

import mtcg.model.User;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;

import java.sql.*;

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
    public void updateUser(User user) {
        String sql = "UPDATE mUser SET username = ?, password = ?, elo = ? WHERE userId = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getElo());
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating User", e);
        }
    }


}
