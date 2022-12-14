package me.luca.permission.command

import cloud.commandframework.Command
import cloud.commandframework.arguments.standard.IntegerArgument
import cloud.commandframework.arguments.standard.LongArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.bukkit.BukkitCommandManager
import eu.thesimplecloud.api.CloudAPI
import eu.thesimplecloud.module.permission.PermissionPool
import eu.thesimplecloud.module.permission.player.PlayerPermissionGroupInfo
import me.luca.permission.SimplePermissionPlugin
import me.luca.permission.util.UUIDFetcher
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * Created by EinsLuca
 * Class create at 01.08.2022 17:58 @simplecloud-simple-permission-commands
 **/
class RankCommand(
    val commandManager: BukkitCommandManager<CommandSender>
) {

    fun register() {
        val commandBuilder: Command.Builder<CommandSender> =
            this.commandManager.commandBuilder("rank", "rang")

        commandManager.command(commandBuilder
            .argument(StringArgument.newBuilder("player"))
            .argument(StringArgument.newBuilder("group"))
            .argument(LongArgument.newBuilder("timeout"))
            .handler {
                val sender = it.sender

                if (sender is ConsoleCommandSender) {
                    val player = it.get<String>("player")
                    val group = it.get<String>("group")
                    val timeOut = it.get<Long>("timeout")

                    val permissionPlayer = PermissionPool.instance.getPermissionPlayerManager().getCachedPermissionPlayer(UUIDFetcher.getUUID(player))

                    val timeOutLong = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(timeOut)
                    permissionPlayer!!.addPermissionGroup(PlayerPermissionGroupInfo(group, timeOutLong))
                    permissionPlayer.update()

                    SimplePermissionPlugin.instance.logger.log(Level.INFO, "Giving $group to ${UUIDFetcher.getUUID(player)}/$player for $timeOut days!")

                }

            })

    }

}