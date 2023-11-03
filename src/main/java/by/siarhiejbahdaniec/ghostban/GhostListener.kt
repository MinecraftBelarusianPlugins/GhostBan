package by.siarhiejbahdaniec.ghostban

import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerTeleportEvent

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
    fun onEvent(event: PlayerTeleportEvent) {
        handleEvent(
            event = event,
            player = event.player
        )
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
    fun onEvent(event: EntityPickupItemEvent) {
        val entity = event.entity
        if (entity is Player) {
            handleEvent(
                event = event,
                player = entity
            )
        }
    }

    private fun handleEvent(event: Cancellable, player: Player) {
        if (player.hasPermission("ghost.ban")) {
            event.isCancelled = true
        }
    }
}