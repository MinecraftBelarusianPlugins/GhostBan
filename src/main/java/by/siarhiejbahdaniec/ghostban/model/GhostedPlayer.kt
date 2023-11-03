package by.siarhiejbahdaniec.ghostban.model

import org.bukkit.inventory.ItemStack
import java.util.*

data class GhostedPlayer(
    val id: UUID,
    val level: Int,
    val experience: Float,
    val inventory: List<ItemStack>,
    val armor: List<ItemStack>,
)
