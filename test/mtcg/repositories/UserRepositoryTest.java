package repositories;
import mtcg.dal.UnitOfWork;

import mtcg.model.Card;
import mtcg.model.ElementType;
import mtcg.model.User;
import mtcg.model.CardType;
import mtcg.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
        import java.util.List;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {


    @Test
    public void testUserDoesNotExist() throws SQLException {

        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        Mockito.when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);

        ResultSet resultSet = mock(ResultSet.class);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true); // User exists

        UserRepository userRepository = new UserRepository(unitOfWork);
        User user = new User("existingUser", "password", new ArrayList<>(), new ArrayList<>(), 100, 1500, 10, 5);

        assertFalse(userRepository.userExists(user));
    }


    @Test
    public void testUserLogged_Success() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        UnitOfWork unitOfWork = mock(UnitOfWork.class);

        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        User user = new User("username", "password", new ArrayList<>(), new ArrayList<>(), 0, 0, 0, 0);

        ResultSet resultSet = mock(ResultSet.class);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet != null && resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        boolean result = userRepository.userLogged(user);

        verify(preparedStatement).setString(1, "username");
        verify(preparedStatement).setString(2, "password");
        verify(preparedStatement).executeQuery();

        assertTrue(result);
    }

    @Test
    public void testUserLogged_Failure() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        UnitOfWork unitOfWork = mock(UnitOfWork.class);

        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        User user = new User("username", "password", new ArrayList<>(), new ArrayList<>(), 0, 0, 0, 0);

        ResultSet resultSet = mock(ResultSet.class);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet != null && resultSet.next()).thenReturn(true);// Assuming there is a result, but count is 0
        when(resultSet.getInt(1)).thenReturn(0);

        boolean result = userRepository.userLogged(user);

        verify(preparedStatement).setString(1, "username");
        verify(preparedStatement).setString(2, "password");
        verify(preparedStatement).executeQuery();

        assertFalse(result);
    }


    @Test
    public void testSaveUser() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        Mockito.when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        User user = new User("newUser", "password", new ArrayList<>(), new ArrayList<>(), 100, 1500, 10, 5);
        userRepository.saveUser(user);

        verify(preparedStatement).setString(1, "newUser");
        verify(preparedStatement).setString(2, "password");
        verify(preparedStatement).setInt(3, 100);
        verify(preparedStatement).setInt(4, 1500);

        verify(preparedStatement).executeUpdate();
    }


    @Test
    public void testFindByUsername() throws SQLException {
        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        Mockito.when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true); // User exists

        Mockito.when(resultSet.getInt("userId")).thenReturn(1);
        Mockito.when(resultSet.getString("password")).thenReturn("password");
        Mockito.when(resultSet.getInt("elo")).thenReturn(1500);
        Mockito.when(resultSet.getInt("coins")).thenReturn(100);
        Mockito.when(resultSet.getString("bio")).thenReturn("User Bio");
        Mockito.when(resultSet.getString("image")).thenReturn("User Image");
        Mockito.when(resultSet.getString("changename")).thenReturn("User Change Name");
        Mockito.when(resultSet.getInt("wins")).thenReturn(10);
        Mockito.when(resultSet.getInt("losses")).thenReturn(5);

        User user = userRepository.findByUsername("existingUser");

        verify(preparedStatement).setString(1, "existingUser");
        verify(preparedStatement).executeQuery();

        assertNotNull(user);
        assertEquals("existingUser", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(1500, user.getElo());
        assertEquals(100, user.getCoins());
        assertEquals("User Bio", user.getBio());
        assertEquals("User Image", user.getImage());
        assertEquals("User Change Name", user.getChangename());
        assertEquals(10, user.getWins());
        assertEquals(5, user.getLosses());
    }


    @Test
    public void testGetAllUser() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        UnitOfWork unitOfWork = mock(UnitOfWork.class);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        // Mocking data for the result set
        when(resultSet.next()).thenReturn(true, true, false); // Simulating two users
        when(resultSet.getInt("userId")).thenReturn(1, 2);
        when(resultSet.getString("username")).thenReturn("user1", "user2");
        when(resultSet.getString("password")).thenReturn("pass1", "pass2");
        when(resultSet.getInt("elo")).thenReturn(1500, 1600);
        when(resultSet.getInt("coins")).thenReturn(100, 200);
        when(resultSet.getString("bio")).thenReturn("Bio1", "Bio2");
        when(resultSet.getString("image")).thenReturn("Image1", "Image2");
        when(resultSet.getString("changename")).thenReturn("Change1", "Change2");
        when(resultSet.getInt("wins")).thenReturn(10, 15);
        when(resultSet.getInt("losses")).thenReturn(5, 8);

        UserRepository userRepository = new UserRepository(unitOfWork);

        List<User> userList = userRepository.getAllUser();

        // Verify that the appropriate methods were called on the mock PreparedStatement
        verify(preparedStatement).executeQuery();

        // Verify the size and content of the user list
        assertEquals(2, userList.size());

        User user1 = userList.get(0);
        assertEquals(1, user1.getUserId());
        assertEquals("user1", user1.getUsername());
        assertEquals("pass1", user1.getPassword());
        assertEquals(1500, user1.getElo());
        assertEquals(100, user1.getCoins());
        assertEquals("Bio1", user1.getBio());
        assertEquals("Image1", user1.getImage());
        assertEquals("Change1", user1.getChangename());
        assertEquals(10, user1.getWins());
        assertEquals(5, user1.getLosses());

        User user2 = userList.get(1);
        assertEquals(2, user2.getUserId());
        assertEquals("user2", user2.getUsername());
        assertEquals("pass2", user2.getPassword());
        assertEquals(1600, user2.getElo());
        assertEquals(200, user2.getCoins());
        assertEquals("Bio2", user2.getBio());
        assertEquals("Image2", user2.getImage());
        assertEquals("Change2", user2.getChangename());
        assertEquals(15, user2.getWins());
        assertEquals(8, user2.getLosses());
    }



    @Test
    public void testUpdateUserData() throws SQLException {

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        User user = new User("userToUpdate", "password", new ArrayList<>(), new ArrayList<>(), 100, 1500, 10, 5);
        user.setBio("Old Bio");
        user.setImage("Old Image");
        user.setChangename("Old Change Name");

        userRepository.updateUserData(user);

        verify(preparedStatement).setString(1, "Old Bio");
        verify(preparedStatement).setString(2, "Old Image");
        verify(preparedStatement).setString(3, "Old Change Name");
        verify(preparedStatement).setString(4, "userToUpdate");

        user.setBio("New Bio");
        user.setImage("New Image");
        user.setChangename("New Change Name");

        verify(preparedStatement).executeUpdate();

        assertEquals("New Bio", user.getBio());
        assertEquals("New Image", user.getImage());
        assertEquals("New Change Name", user.getChangename());
    }


    @Test
    public void testUpdateCoinOfUser() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        UnitOfWork unitOfWork = mock(UnitOfWork.class);
        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        User user = new User("userToUpdate", "password", new ArrayList<>(), new ArrayList<>(), 100, 1500, 10, 5);

        userRepository.updateCoinOfUser(user);

        verify(preparedStatement).setInt(1, 100);
        verify(preparedStatement).setString(2, "userToUpdate");
        verify(preparedStatement).executeUpdate();

        assertEquals(100, user.getCoins());
    }


    @Test
    public void testUpdateStack() throws SQLException {

        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        Mockito.when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        UserRepository userRepository = new UserRepository();
        User user = new User("userWithStack", "password", new ArrayList<>(), new ArrayList<>(), 100, 1500, 10, 5);
        int cardId = 123;

        userRepository.updateStack(user, cardId);
    }


    @Test
    public void testUpdateDeck() throws SQLException {
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        UnitOfWork unitOfWork = mock(UnitOfWork.class);

            when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);
        UserRepository userRepository = new UserRepository(unitOfWork);

        User user = new User("userWithDeck", "password", new ArrayList<>(), new ArrayList<>(), 100, 1500, 10, 5);

       // public Card(int userId, String name,int damage, ElementType elem, CardType ct) {
        List<Card> deckCards = Arrays.asList(
                new Card(1,"GoblinWater",40,ElementType.WATER, CardType.SPELL),
                new Card(1, "Knight", 50,ElementType.FIRE, CardType.MONSTER),
                new Card(1,"WaterTroll", 30, ElementType.WATER,CardType.SPELL)
        );

        userRepository.updateDeck(user, deckCards);
    }



}
