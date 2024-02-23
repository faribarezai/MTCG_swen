package mtcg.repository;

import mtcg.dal.UnitOfWork;
import mtcg.model.Card;
import mtcg.model.Package;

import java.sql.*;
import java.util.List;

public class PackageRepository {
    private UnitOfWork unitOfWork = new UnitOfWork();
    public PackageRepository(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }
    public PackageRepository() {
    }

    public void savePackage(Package pckg) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "password")) {
            // Check if any of the cardIds already exist
            if (cardIdsExist(connection, pckg.getCardIds())) {
                System.out.println("cardIds already exist. Package not saved.");
                return;  // Do not proceed with saving the package
            }
            String sql = "INSERT INTO package (cardIds) VALUES (?)";
            try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
                Integer[] cardIdsArray = pckg.getCardIds().toArray(new Integer[0]);
                preparedStatement.setArray(1, connection.createArrayOf("INTEGER", cardIdsArray));

                preparedStatement.executeUpdate();
                System.out.println("Package saved successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private boolean cardIdsExist(Connection connection, List<Integer> cardIds) {
        String sql = "SELECT COUNT(*) FROM package WHERE cardIds @> ?::integer[]";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            //try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM package WHERE cardIds @> ?::integer[]")) {
            // Check if any row matches the given cardIds
            Array cardIdsArray = connection.createArrayOf("integer", cardIds.toArray());
            preparedStatement.setArray(1, cardIdsArray);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
        return false;
    }
}