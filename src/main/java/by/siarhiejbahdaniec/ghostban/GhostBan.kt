package by.siarhiejbahdaniec.ghostban

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin


class GhostBan : JavaPlugin() {

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
