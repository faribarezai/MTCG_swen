package mtcg.repository;

import mtcg.model.Player;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PlayerRepository {

    private final UnitOfWork unitOfWork;

    public PlayerRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void updatePlayer(Player player) {
        String sql = "UPDATE players SET username = ?, password = ?, elo = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, player.getUsername());
            preparedStatement.setString(2, player.getPassword());
            preparedStatement.setInt(3, player.getElo());
            preparedStatement.setLong(4, player.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating player", e);
        }
    }

    // Add other methods for player-related database operations as needed
}
