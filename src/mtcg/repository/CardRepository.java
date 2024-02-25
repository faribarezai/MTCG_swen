package mtcg.repository;


import mtcg.model.Card;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CardRepository {

    private final UnitOfWork unitOfWork;

    public CardRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    public void updateCard(Card card) {
<<<<<<< HEAD
        String sql = "UPDATE cards SET name = ?, damage = ?, element_type = ?, card_type = ? WHERE id = ?";
=======
        String sql = "UPDATE card SET name = ?, damage = ?, element = ?, cardType = ? WHERE cardId = ?";
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c

        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setString(1, card.getName());
            preparedStatement.setInt(2, card.getDamage());
            preparedStatement.setString(3, String.valueOf((card.getElementType())));
            preparedStatement.setString(4, card.getCardType().toString());
            preparedStatement.setInt(5, card.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating card", e);
        }
    }

    // Add other methods for card-related database operations as needed
}

