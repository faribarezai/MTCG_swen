package mtcg.repository;

import mtcg.dal.UnitOfWork;
import mtcg.model.Card;
import mtcg.model.Package;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<Integer> getAllCardIdsFromPackages() {
        List<Integer> existingCardIds = new ArrayList<>();

        String sql = "SELECT cardIds FROM package";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Array cardIdsArray = resultSet.getArray("cardIds");

                // Convert the array to a List<Integer>
                List<Integer> cardIds = Arrays.asList((Integer[]) cardIdsArray.getArray());

                existingCardIds.addAll(cardIds);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle or log any exceptions that may occur
        }

        return existingCardIds;
    }

    public List<Integer> getCardIdsFromPackagById(int id) {
        List<Integer> existingCardIds = new ArrayList<>();

        String sql = "SELECT cardIds FROM package WHERE pckgId=? ";
        try (PreparedStatement preparedStatement = unitOfWork.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);  // Set the value for the pckgId parameter

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Array cardIdsArray = resultSet.getArray("cardIds");

                    // Convert the array to a List<Integer>
                    List<Integer> cardIds = Arrays.asList((Integer[]) cardIdsArray.getArray());

                    existingCardIds.addAll(cardIds);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle or log any exceptions that may occur
        }

        return existingCardIds;
    }



}