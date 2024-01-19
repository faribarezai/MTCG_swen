package mtcg.model;


import java.util.List;

public class BattleLogic {

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

        // Return the battle log
        // ...

        return null;
    }

    private static void updateUserStats(User user, boolean isWinner) {
        // Update player stats, e.g., increment games played, calculate ELO, etc.
        // ...
        List<User> winner = null;
        if(isWinner) {
            winner.add(user);
        }
    }
}
