package mtcg.model;


import java.util.List;

public class BattleLogic {

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

        // Return the battle log
        // ...

        return null;
    }

    private static void updatePlayerStats(Player player, boolean isWinner) {
        // Update player stats, e.g., increment games played, calculate ELO, etc.
        // ...
        List<Player> winner = null;
        if(isWinner) {
            winner.add(player);
        }
    }
}
