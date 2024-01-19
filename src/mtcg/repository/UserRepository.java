package mtcg.repository;

import mtcg.model.User;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {

    private final UnitOfWork unitOfWork;

    public UserRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void updatePlayer(User user) {
        String sql = "UPDATE players SET username = ?, password = ?, elo = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setInt(3, user.getElo());
            preparedStatement.setLong(4, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating player", e);
        }
    }

    // Add other methods for player-related database operations as needed
}
