package by.siarhiejbahdaniec.ghostban

import by.siarhiejbahdaniec.ghostban.config.ConfigHolder
import by.siarhiejbahdaniec.ghostban.logic.GhostHandler
import by.siarhiejbahdaniec.ghostban.logic.GhostListener
import by.siarhiejbahdaniec.ghostban.storage.GhostPlayersRepository
import net.luckperms.api.LuckPerms
import net.luckperms.api.event.node.NodeMutateEvent
import net.luckperms.api.model.user.User
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin


class GhostBan : JavaPlugin(), ConfigHolder {

    companion object {
        const val HUMANITY_PERMISSION = "ghostban.humanity"
    }

    private val ghostHandler = GhostHandler(
        repo = GhostPlayersRepository(
            dir = dataFolder
        ),
        configHolder = this
    )

    override fun onEnable() {
        setupConfig()

        val pluginManager = Bukkit.getPluginManager()
        pluginManager.registerEvents(
            GhostListener(
                ghostHandler = ghostHandler,
            ),
            this
        )

        requireNotNull(getCommand("ghostban_reload"))
            .setExecutor { sender, _, _, _ ->
                reloadConfigFromDisk()
                sender.sendMessage("[GhostBan] Канфіг перазагружаны")
                true
            }

        initLuckPermsObserver()
    }

    private fun setupConfig() {
        saveDefaultConfig()
        getConfig().options().copyDefaults(true)
        saveConfig()
        reloadConfig()
    }

    private fun initLuckPermsObserver() {
        val luckPerms = Bukkit.getServicesManager()
            .getRegistration(LuckPerms::class.java)
            .let(::requireNotNull)
            .provider

        luckPerms.eventBus.subscribe(this@GhostBan, NodeMutateEvent::class.java) { event ->
            val target = event.target
            if (target is User) {
                val player = Bukkit.getServer().getPlayer(target.uniqueId)
                if (player != null) {
                    ghostHandler.handlePlayer(player)
                    teleportIfPlayerIsGhost(player)
                }
            }
        }
    }

    private fun teleportIfPlayerIsGhost(player: Player) {
        if (!player.hasPermission(HUMANITY_PERMISSION)) {
            Bukkit.getScheduler().callSyncMethod(this) {
                player.teleport(ghostHandler.getGhostRespawnLocation())
            }
        }
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    override fun getString(key: String): String {
        return config.getString(key).orEmpty()
    }

    override fun getDouble(key: String): Double {
        return config.getDouble(key, 0.0)
    }

    override fun reloadConfigFromDisk() {
        reloadConfig()
    }
}
