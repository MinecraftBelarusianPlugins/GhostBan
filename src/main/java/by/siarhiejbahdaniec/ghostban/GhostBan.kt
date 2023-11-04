package by.siarhiejbahdaniec.ghostban

import by.siarhiejbahdaniec.ghostban.logic.GhostHandler
import by.siarhiejbahdaniec.ghostban.logic.GhostListener
import by.siarhiejbahdaniec.ghostban.storage.GhostPlayersRepository
import net.luckperms.api.LuckPerms
import net.luckperms.api.event.node.NodeMutateEvent
import net.luckperms.api.model.user.User
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class GhostBan : JavaPlugin() {

    companion object {
        const val HUMANITY_PERMISSION = "ghostban.humanity"
    }

    private val ghostHandler = GhostHandler(
        repo = GhostPlayersRepository(
            dir = dataFolder
        ),
    )

    override fun onEnable() {
        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(GhostListener(ghostHandler), this)

        initLuckPermsObserver()
    }

    private fun initLuckPermsObserver() {
        val luckPerms = Bukkit.getServicesManager()
            .getRegistration(LuckPerms::class.java)
            .let(::requireNotNull)
            .provider

        with(luckPerms.eventBus) {
            subscribe(this@GhostBan, NodeMutateEvent::class.java) { event ->
                val target = event.target
                if (target is User) {
                    val player = Bukkit.getServer().getPlayer(target.uniqueId)
                    if (player != null) {
                        ghostHandler.handlePlayer(player)
                    }
                }
            }
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
