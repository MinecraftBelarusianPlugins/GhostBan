package by.siarhiejbahdaniec.ghostban

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.*

class GhostListener: Listener {

    @EventHandler
    fun onEvent(event: BlockBreakEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
    }

    @EventHandler
    fun onEvent(event: BlockPlaceEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
    }

    @EventHandler
    fun onEvent(event: PlayerDropItemEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
    }

    @EventHandler
    fun onEvent(event: PlayerInteractEntityEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
    }

    @EventHandler
    fun onEvent(event: PlayerInteractEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
    }

    @EventHandler
    fun onEvent(event: PlayerTeleportEvent) {
        when (event.cause) {
            PlayerTeleportEvent.TeleportCause.COMMAND,
            PlayerTeleportEvent.TeleportCause.EXIT_BED,
            PlayerTeleportEvent.TeleportCause.PLUGIN -> {
                handleEvent(
                    event = event,
                    player = event.player
                )
            }
            else -> {
                // ignore
            }
        }
    }

    @EventHandler
    fun onEvent(event: PlayerBedEnterEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
    }

    @EventHandler
    fun onEvent(event: PlayerExpChangeEvent) {
        if (event.player.hasPermission(GhostBan.PERMISSION)) {
            event.player.exp = 0f
        }
    }

    @EventHandler
    fun onEvent(event: InventoryOpenEvent) {
        val player = event.player
        if (player is Player && event.inventory.holder !is Player) {
            handleEvent(
                event = event,
                player = player
            )
        }
    }

    @EventHandler
    fun onEvent(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is Player) {
            handleEvent(
                event = event,
                player = entity
            )
        }
    }

    @EventHandler
    fun onEvent(event: EntityDamageByEntityEvent) {
        val entity = event.damager
        if (entity is Player) {
            handleEvent(
                event = event,
                player = entity
            )
        }
    }

    @EventHandler
    fun onEvent(event: EntityPickupItemEvent) {
        val entity = event.entity
        if (entity is Player) {
            handleEvent(
                event = event,
                player = entity
            )
        }
    }

    @EventHandler
    fun onEvent(event: EntityTargetEvent) {
        val entity = event.target
        if (entity is Player) {
            handleEvent(
                event = event,
                player = entity
            )
        }
    }

    private fun handleEvent(event: Cancellable, player: Player) {
        if (player.hasPermission(GhostBan.PERMISSION)) {
            event.isCancelled = true
        }
    }
}