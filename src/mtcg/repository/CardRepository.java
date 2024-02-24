package mtcg.repository;


import mtcg.model.Card;
import mtcg.dal.DataAccessException;
import mtcg.dal.UnitOfWork;
import mtcg.model.CardType;
import mtcg.model.ElementType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private UnitOfWork unitOfWork = new UnitOfWork();

    public CardRepository() {}
    public CardRepository(UnitOfWork unitOfWork) { this.unitOfWork= unitOfWork;}

    public void saveCard(Card card) {
        String sql = "INSERT INTO card (userId, name, damage, element, cardType) VALUES (NULL, ?, ?, CAST(? AS elementtype), CAST(? AS cardtype))";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            //preparedStatement.setInt(1, card.getUserId());
            preparedStatement.setString(1, card.getName());
            preparedStatement.setInt(2, card.getDamage());
            preparedStatement.setString(3, String.valueOf(card.getElement()));
            preparedStatement.setString(4, String.valueOf(card.getCardType()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }


    public List<Card> getAllCards(int userId) {
        List<Card> cards = new ArrayList<>();

        String sql = "SELECT * FROM card WHERE userId = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);  // Set the userId parameter
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Process the result set and populate your List<Card>
                while (resultSet.next()) {
                    int cardId = resultSet.getInt("cardId");
                    int userid= resultSet.getInt("userId");
                    String name = resultSet.getString("name");
                    int damage = resultSet.getInt("damage");
                    String elementTypeStr = resultSet.getString("element");
                    String cardTypeStr = resultSet.getString("cardType");


                    // Convert elementTypeStr and cardTypeStr to corresponding enums
                    ElementType elementType = ElementType.valueOf(elementTypeStr);
                    CardType cardType = CardType.valueOf(cardTypeStr);

                    // Create a Card object and add it to your List<Card>
                    Card card = new Card(userid, name, damage, elementType, cardType);
                    card.setCardId(cardId);
                    cards.add(card);
                }
            }   return cards;
        } catch (SQLException e) {
            e.printStackTrace();  // Handle or log any exceptions that may occur
        }

        return cards;
    }


    public List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM card";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int cardId = resultSet.getInt("cardId");
                int userId = resultSet.getInt("userId");
                String name = resultSet.getString("name");
                int damage = resultSet.getInt("damage");
                String elementTypeStr = resultSet.getString("element");
                String cardTypeStr = resultSet.getString("cardType");

                // Convert elementTypeStr and cardTypeStr to corresponding enums
                ElementType elementType = ElementType.valueOf(elementTypeStr);
                CardType cardType = CardType.valueOf(cardTypeStr);

                // Create a Card object and add it to your List<Card>
                Card card = new Card(userId, name, damage, elementType, cardType);
                card.setCardId(cardId);
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle or log any exceptions that may occur
        }

        return cards;
    }



    public void updateCardByUserID(Card card, int uid) {
        String sql = "UPDATE card SET userId = ? WHERE cardId = ?";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            card.setUserId(uid);
            preparedStatement.setInt(1, uid);
            preparedStatement.setInt(2, card.getCardId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error updating card", e);
        }
    }
}

