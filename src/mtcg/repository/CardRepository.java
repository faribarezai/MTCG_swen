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
        String sql = "UPDATE card SET cardId=?, name = ?, damage = ?, element = ?, cardType = ? WHERE cardId = ?";

        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, card.getCardId());
            preparedStatement.setString(2, card.getName());
            preparedStatement.setInt(3, card.getDamage());
            preparedStatement.setString(4, String.valueOf((card.getElementType())));
            preparedStatement.setString(5, card.getCardType().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error updating card", e);
        }
    }

    // Add other methods for card-related database operations as needed
}

