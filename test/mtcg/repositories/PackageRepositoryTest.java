package repositories;

import mtcg.dal.UnitOfWork;
import mtcg.repository.PackageRepository;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class PackageRepositoryTest {

   /* @Test
   public void testSavePackage() throws SQLException {
        // Mock the necessary components
        Connection connection = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        UnitOfWork unitOfWork = mock(UnitOfWork.class);

        // Mocking behavior of preparing the statement
        when(unitOfWork.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Create a PackageRepository instance
        PackageRepository packageRepository = new PackageRepository(unitOfWork);

        // Create a Package object with some cardIds
        Package packageToSave = new Package();
        packageToSave.addCardId(1);
        packageToSave.addCardId(2);

        // Mocking cardIdsExist method to return false (indicating cardIds do not exist)
        when(packageRepository.cardIdsExist(connection, packageToSave.getCardIds())).thenReturn(false);

        // Invoke the method
        packageRepository.savePackage(packageToSave);

        // Verify that the appropriate methods were called on the mock PreparedStatement
        verify(preparedStatement).setArray(1, connection.createArrayOf("INTEGER", packageToSave.getCardIds().toArray()));

        // Verify that executeUpdate was called
        verify(preparedStatement).executeUpdate();

        // Add more assertions as needed based on the behavior of your system
    }*/
}

