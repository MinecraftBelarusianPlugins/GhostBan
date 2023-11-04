package by.siarhiejbahdaniec.ghostban.logic

import by.siarhiejbahdaniec.ghostban.GhostBan
import by.siarhiejbahdaniec.ghostban.config.ConfigHolder
import by.siarhiejbahdaniec.ghostban.config.ConfigKeys
import by.siarhiejbahdaniec.ghostban.model.GhostedPlayer
import by.siarhiejbahdaniec.ghostban.storage.GhostPlayersRepository
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.WeatherType
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player

class GhostHandler(
    private val repo: GhostPlayersRepository,
    private val configHolder: ConfigHolder,
) {

    fun handlePlayer(player: Player) {
        synchronized(GhostHandler::class.java) {
            if (player.isDead) {
                return
            }
            if (!player.hasPermission(GhostBan.HUMANITY_PERMISSION)) {
                handleGhost(player)
            } else {
                handleHumanity(player)
            }
        }
    }

    fun getGhostRespawnLocation(): Location {
        return Location(
            Bukkit.getWorld(configHolder.getString(ConfigKeys.ghostSpawnWorld)),
            configHolder.getDouble(ConfigKeys.ghostSpawnX),
            configHolder.getDouble(ConfigKeys.ghostSpawnY),
            configHolder.getDouble(ConfigKeys.ghostSpawnZ),
        )
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
            player.sendMessage(configHolder.getString(ConfigKeys.ghostMessage))
        }

        player.apply {
            inventory.clear()
            equipment?.clear()
            saturation = 0f
            foodLevel = 0
            health = 0.1
            setPlayerWeather(WeatherType.DOWNFALL)
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
            health = getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 10.0
            foodLevel = 20
            saturation = 5f
            inventory.contents = ghostedPlayer.inventory.toTypedArray()
            inventory.setArmorContents(ghostedPlayer.armor.toTypedArray())
            resetPlayerWeather()
        }
        player.sendMessage(configHolder.getString(ConfigKeys.humanityMessage))
    }
}