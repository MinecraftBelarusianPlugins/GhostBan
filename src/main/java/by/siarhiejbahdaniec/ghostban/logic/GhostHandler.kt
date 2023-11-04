package by.siarhiejbahdaniec.ghostban.logic

import by.siarhiejbahdaniec.ghostban.model.GhostedPlayer
import by.siarhiejbahdaniec.ghostban.storage.GhostPlayersRepository
import org.bukkit.entity.Player

class GhostHandler(
    private val repo: GhostPlayersRepository,
) {

    fun handleGhost(player: Player) {
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

        player.apply {
            inventory.clear()
            equipment?.clear()
            health = 0.5
            foodLevel = 0
        }
    }
}