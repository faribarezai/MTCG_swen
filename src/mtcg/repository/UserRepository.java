package mtcg.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import mtcg.model.Card;
import mtcg.model.CardType;
import mtcg.model.User;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private UnitOfWork unitOfWork = new UnitOfWork();

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
                System.out.println("User count in database: " + count);
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
                    String bio= resultSet.getString("bio");
                    String image= resultSet.getString("image");
                    String changename= resultSet.getString("changename");
                    int wins = resultSet.getInt("wins");
                    int losses = resultSet.getInt("losses");
                    // Create and return a User object
                    User user = new User(username, password, deck, stack, coins, elo, wins, losses);
                    user.setUserId(userId); // Set the retrieved userId
                    user.setBio(bio);
                    user.setImage(image);
                    user.setChangename(changename);

                    return user;
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error finding User by username", e);
        }

        // Return null if no user is found
        return null;
    }

    public void updateUser(User user) {
        String sql = "UPDATE mUser SET stack = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            // Assuming 'stack' is a List<Card> and you have a method to serialize it to an appropriate format
            String serializedStack = serializeStack(user.getStack());

            preparedStatement.setString(1, serializedStack);
            preparedStatement.setString(2, user.getUsername());
            // Execute the update
            preparedStatement.executeUpdate();

    } catch (SQLException e) {
            throw new DataAccessException("Error finding User by username", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    public void updateUserData(User user) {
            String sql = "UPDATE mUser SET bio = ?, image = ?, changename = ? WHERE username = ?";
              System.out.println("before prepareStmt: " + user.getBio());
            try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
                preparedStatement.setString(1, user.getBio());
                preparedStatement.setString(2, user.getImage());
                preparedStatement.setString(3, user.getChangename());
                preparedStatement.setString(4, user.getUsername());

                System.out.println("in prepareStmt: " + user.getBio());
                // Execute the update
                preparedStatement.executeUpdate();

                System.out.println("after execute prepareStmt: " + user.getBio());
            } catch (SQLException e) {
                throw new DataAccessException("Error updating User data", e);
            }
        }


    private String serializeStack(List<Card> stack) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(stack);
    }

    public List<User> getAllUser() {
        String sql = "SELECT * FROM mUser";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String username=resultSet.getString("username");
                String password = resultSet.getString("password");
                int elo = resultSet.getInt("elo");
                List<Card> deck = new ArrayList<>();
                List<Card> stack = new ArrayList<>();
                int coins = resultSet.getInt("coins");
                String bio= resultSet.getString("bio");
                String image= resultSet.getString("image");
                String changename= resultSet.getString("changename");
                int wins = resultSet.getInt("wins");
                int losses = resultSet.getInt("losses");
                // Create and return a User object

                User user = new User(username, password, deck, stack, coins, elo, wins, losses);
                user.setUserId(userId); // Set the retrieved userId
                user.setBio(bio);
                user.setImage(image);
                user.setChangename(changename);


                userList.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
        }

        return userList;
    }

 /*   public void savetoStack(User user, int cardId) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password")) {

            String sql = "INSERT INTO mUser (username, stack) VALUES (?,?)";
            System.out.println("before Saving to stack");

            try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
                Integer[] cardIdsArray = {cardId};
                preparedStatement.setString(1, user.getUsername());
                System.out.println("in prepstm Savetostack.");
                preparedStatement.setArray(2, connection.createArrayOf("INTEGER", cardIdsArray));

                preparedStatement.executeUpdate();
                System.out.println("Cards saved successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    */


    public void updateStack(User user, int cardId) throws SQLException {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password")) {
            String sql = "UPDATE mUser SET stack = array_append(stack, ?) WHERE username = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, cardId);
                preparedStatement.setString(2, user.getUsername());

                preparedStatement.executeUpdate();
                System.out.println("Card added to stack successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




}
