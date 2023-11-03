package by.siarhiejbahdaniec.ghostban.logic

import by.siarhiejbahdaniec.ghostban.model.GhostedPlayer
import by.siarhiejbahdaniec.ghostban.storage.GhostPlayersRepository
import org.bukkit.Location
import org.bukkit.entity.Player

class GhostMaker(
    private val repo: GhostPlayersRepository,
) {

    fun makeGhost(player: Player) {
        if (!repo.containsGhost(player.uniqueId)) {
            val ghostedPlayer = GhostedPlayer(
                id = player.uniqueId,
                level = player.level,
                experience = player.exp,
                inventory = player.inventory.contents.toList(),
                armor = player.inventory.armorContents.toList(),
            )
            repo.addGhost(ghostedPlayer)
        }

        player.health = 0.5
        player.foodLevel = 0
        player.teleport(
            Location(null, 100.0, 100.0, 100.0)
        )
    }
}