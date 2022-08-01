package me.luca.permission

import cloud.commandframework.bukkit.BukkitCommandManager
import cloud.commandframework.execution.CommandExecutionCoordinator
import me.luca.permission.command.RankCommand
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.util.function.Function

/**
 * Created by EinsLuca
 * Class create at 01.08.2022 17:56 @simplecloud-simple-permission-commands
 **/
class SimplePermissionPlugin: JavaPlugin() {

    companion object {
        lateinit var instance: SimplePermissionPlugin
    }

    private lateinit var commandManager: BukkitCommandManager<CommandSender>


    override fun onEnable() {
        instance = this

        this.commandManager = BukkitCommandManager(
            this,
            CommandExecutionCoordinator.simpleCoordinator(),
            Function.identity(),
            Function.identity()
        )

        RankCommand(commandManager).register()
    }

    override fun onDisable() {

    }

}