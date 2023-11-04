package by.siarhiejbahdaniec.ghostban.logic

import by.siarhiejbahdaniec.ghostban.GhostBan
import by.siarhiejbahdaniec.ghostban.model.GhostedPlayer
import by.siarhiejbahdaniec.ghostban.storage.GhostPlayersRepository
import org.bukkit.entity.Player

class GhostHandler(
    private val repo: GhostPlayersRepository,
) {

    fun handlePlayer(player: Player) {
        if (!player.hasPermission(GhostBan.HUMANITY_PERMISSION)) {
            handleGhost(player)
        } else {
            handleHumanity(player)
        }
    }

    private fun handleGhost(player: Player) {
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
            starvationRate = 1
        }
    }

    private fun handleHumanity(player: Player) {
        if (!repo.containsGhost(player.uniqueId)) {
            return
        }
        val ghostedPlayer = repo.removeGhost(player.uniqueId)
        with(player) {
            level = ghostedPlayer.level
            exp = ghostedPlayer.experience
            inventory.contents = ghostedPlayer.inventory.toTypedArray()
            inventory.setArmorContents(ghostedPlayer.armor.toTypedArray())
        }
    }
}