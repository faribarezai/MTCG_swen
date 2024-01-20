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
        String sql = "UPDATE card SET name = ?, damage = ?, element = ?, cardType = ? WHERE cardId = ?";

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

