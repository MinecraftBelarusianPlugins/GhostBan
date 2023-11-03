package by.siarhiejbahdaniec.ghostban

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class GhostCommand : CommandExecutor {

    companion object {
        private const val GHOST_TEAM_NAME = "ghosts_team"
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command, label: String,
        args: Array<out String>
    ): Boolean {
        return true
    }
}