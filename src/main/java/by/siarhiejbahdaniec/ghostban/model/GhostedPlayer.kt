package by.siarhiejbahdaniec.ghostban.model

import org.bukkit.inventory.ItemStack

data class GhostedPlayer(
    val id: String,
    val level: Int,
    val experience: Float,
    val inventory: List<ItemStack>,
    val armor: List<ItemStack>,
)
