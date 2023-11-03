package by.siarhiejbahdaniec.ghostban

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class GhostBan : JavaPlugin() {

    companion object {
        const val PERMISSION = "ghost.ban"
    }

    override fun onEnable() {
        val executor = GhostCommand()
        requireNotNull(getCommand("ghost")).setExecutor(executor)

        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(GhostListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
