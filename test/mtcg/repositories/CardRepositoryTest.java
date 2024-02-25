package repositories;

import mtcg.dal.UnitOfWork;
import mtcg.model.Card;
import mtcg.model.CardType;
import mtcg.model.ElementType;
import mtcg.repository.CardRepository;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CardRepositoryTest {

    @Test
    public void testSaveCard() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);

        CardRepository cardRepository = new CardRepository(unitOfWork);

        Card cardToSave = new Card(1, "CardName", 50, ElementType.FIRE, CardType.MONSTER);

        cardRepository.saveCard(cardToSave);

        verify(preparedStatement).setString(1, cardToSave.getName());
        verify(preparedStatement).setInt(2, cardToSave.getDamage());
        verify(preparedStatement).setString(3, String.valueOf(cardToSave.getElement()));
        verify(preparedStatement).setString(4, String.valueOf(cardToSave.getCardType()));
        verify(preparedStatement).executeUpdate();

    }


    @Test
    public void testGetAllCardsOfaUser() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        CardRepository cardRepository = new CardRepository(unitOfWork);

        int userId = 1;

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Simulate one result

        when(resultSet.getInt("cardId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(userId);
        when(resultSet.getString("name")).thenReturn("CardName");
        when(resultSet.getInt("damage")).thenReturn(50);
        when(resultSet.getString("element")).thenReturn("FIRE");
        when(resultSet.getString("cardType")).thenReturn("MONSTER");

        List<Card> result = cardRepository.getAllCards(userId);

        verify(preparedStatement).setInt(1, userId);
        verify(preparedStatement).executeQuery();  // Called twice, once for checking and once for retrieving data

        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    public void testGetCards() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);

        CardRepository cardRepository = new CardRepository(unitOfWork);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);  // Simulate one result

        when(resultSet.getInt("cardId")).thenReturn(1);
        when(resultSet.getInt("userId")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("CardName");
        when(resultSet.getInt("damage")).thenReturn(50);
        when(resultSet.getString("element")).thenReturn("FIRE");
        when(resultSet.getString("cardType")).thenReturn("MONSTER");

        List<Card> result = cardRepository.getCards();

        verify(preparedStatement).executeQuery();

        assertNotNull(result);
        assertEquals(1, result.size());

    }

    @Test
    public void testUpdateCardByUserID() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        CardRepository cardRepository = new CardRepository(unitOfWork);

        Card card = new Card(1, "CardName", 50, ElementType.FIRE, CardType.MONSTER);
        card.setCardId(1);

        int userIdToUpdate = 2;
        cardRepository.updateCardByUserID(card, userIdToUpdate);

        verify(preparedStatement).setInt(1, userIdToUpdate);
        verify(preparedStatement).setInt(2, card.getCardId());
        verify(preparedStatement).executeUpdate();

    }

    @Test
    public void testGetAllCardsOfaUserUserNotExists() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        CardRepository cardRepository = new CardRepository(unitOfWork);

        int userId = 1;

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        List<Card> result = cardRepository.getAllCards(userId);

        verify(preparedStatement).setInt(1, userId);
        verify(preparedStatement).executeQuery();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}


