package by.siarhiejbahdaniec.ghostban.repository

import by.siarhiejbahdaniec.ghostban.model.GhostedPlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File

class GhostPlayersRepository(dir: File) {

    companion object {
        private const val FILENAME = "ghosts.yml"

        private const val KEY_LEVEL = "%s.lvl"
        private const val KEY_EXP = "%s.exp"
        private const val KEY_INVENTORY = "%s.inventory"
        private const val KEY_ARMOR = "%s.armor"
    }

    private val file = File(dir, FILENAME)
    private val configuration = YamlConfiguration.loadConfiguration(file)

    fun addGhost(ghostedPlayer: GhostedPlayer) {
        configuration.set(
            KEY_LEVEL.format(ghostedPlayer.id),
            ghostedPlayer.level
        )
        configuration.set(
            KEY_EXP.format(ghostedPlayer.id),
            ghostedPlayer.experience
        )
        configuration.set(
            KEY_INVENTORY.format(ghostedPlayer.id),
            ghostedPlayer.inventory
        )
        configuration.set(
            KEY_ARMOR.format(ghostedPlayer.id),
            ghostedPlayer.armor
        )
    }

    fun containsGhost(id: String): Boolean {
        return configuration.contains(id)
    }

    fun removeGhost(id: String): GhostedPlayer {
        @Suppress("UNCHECKED_CAST")
        return GhostedPlayer(
            id = id,
            level = configuration.getInt(KEY_LEVEL.format(id)),
            experience = configuration.getDouble(KEY_EXP.format(id)).toFloat(),
            inventory = configuration.getList(KEY_INVENTORY.format(id)) as List<ItemStack>,
            armor = configuration.getList(KEY_ARMOR.format(id)) as List<ItemStack>,
        )
    }
}