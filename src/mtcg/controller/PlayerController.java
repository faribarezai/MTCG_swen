package mtcg.controller;

import mtcg.model.Player;
import mtcg.service.PlayerService;

public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService userService) {
        this.playerService = userService;
    }

    public void handlePlayerUpdate(Player player) {
        playerService.updatePlayer(player);
    }
}
