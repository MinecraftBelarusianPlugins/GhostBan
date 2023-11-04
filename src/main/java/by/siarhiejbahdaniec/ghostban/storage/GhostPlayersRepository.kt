package by.siarhiejbahdaniec.ghostban.storage

import by.siarhiejbahdaniec.ghostban.model.GhostedPlayer
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import java.io.File
import java.util.*

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
        val id = ghostedPlayer.id.toString()
        configuration.set(
            KEY_LEVEL.format(id),
            ghostedPlayer.level
        )
        configuration.set(
            KEY_EXP.format(id),
            ghostedPlayer.experience
        )
        configuration.set(
            KEY_INVENTORY.format(id),
            ghostedPlayer.inventory
        )
        configuration.set(
            KEY_ARMOR.format(id),
            ghostedPlayer.armor
        )
        configuration.save(file)
    }

    fun containsGhost(id: UUID): Boolean {
        return configuration.contains(id.toString())
    }

    fun removeGhost(id: UUID): GhostedPlayer {
        val idString = id.toString()
        @Suppress("UNCHECKED_CAST")
        val ghostedPlayer = GhostedPlayer(
            id = id,
            level = configuration.getInt(KEY_LEVEL.format(idString)),
            experience = configuration.getDouble(KEY_EXP.format(idString)).toFloat(),
            inventory = configuration.getList(KEY_INVENTORY.format(idString)) as List<ItemStack>,
            armor = configuration.getList(KEY_ARMOR.format(idString)) as List<ItemStack>,
        )
        configuration.set(idString, null)
        configuration.save(file)
        return ghostedPlayer
    }
}