package mtcg.model;


import java.util.List;

public class BattleLogic {

<<<<<<< HEAD
    public static BattleLogic conductBattle(Player player1, Player player2) {
        // Implement the battle logic as described in the prompt
        // ...

        // Update player stats and ELO
        if(player1.getElo() > player2.getElo()) {
            updatePlayerStats(player1, true);
            updatePlayerStats(player2, false);
        }
        int elo = player1.getElo() + player1.getElo();
        //update elo, winner takes it all,loser loses all
        player1.setElo(elo);
        player2.setElo(0);
=======
    public static BattleLogic conductBattle(User user1, User user2) {
        // Implement the battle logic as described in the prompt
        // ...

        // Update user stats and ELO
        if(user1.getElo() > user2.getElo()) {
            updateUserStats(user1, true);
            updateUserStats(user2, false);
        }
        int elo = user1.getElo() + user1.getElo();
        //update elo, winner takes it all,loser loses all
        user1.setElo(elo);
        user2.setElo(0);
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c

        // Return the battle log
        // ...

        return null;
    }

<<<<<<< HEAD
    private static void updatePlayerStats(Player player, boolean isWinner) {
        // Update player stats, e.g., increment games played, calculate ELO, etc.
        // ...
        List<Player> winner = null;
        if(isWinner) {
            winner.add(player);
=======
    private static void updateUserStats(User user, boolean isWinner) {
        // Update player stats, e.g., increment games played, calculate ELO, etc.
        // ...
        List<User> winner = null;
        if(isWinner) {
            winner.add(user);
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
        }
    }
}
