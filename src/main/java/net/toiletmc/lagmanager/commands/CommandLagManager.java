package net.toiletmc.lagmanager.commands;

import net.toiletmc.lagmanager.LagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "lagmanager", aliases = {"lm"}, permission = "lagmanager.command.admin"))
@Permission(name = "lagmanager.command.admin", defaultValue = PermissionDefault.OP)
public class CommandLagManager implements CommandExecutor {
    private final LagManager plugin;

    public CommandLagManager(LagManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        double doubleMspt = plugin.getObserver().getDoubleMspt();
        String mspt = String.format("%.1f", doubleMspt);
        plugin.getObserver().warnIfLagging();

        if (!sender.hasPermission("lagmanager.admin")) {
            sender.sendMessage(ChatColor.RED + "未知的指令");
            return true;
        }

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "test":
                    plugin.getObserver().warnIfLagging();
                    break;
                case "force":
                    plugin.getObserver().warnWithoutLagging();
                    break;
                case "debug":
                    if (doubleMspt <= 100) {
                        sender.sendMessage("MSPT：" + mspt + "，服务器现在很健康 " + ChatColor.GREEN + ":)");
                    } else {
                        sender.sendMessage("MSPT：" + mspt + "，服务器不是很健康 " + ChatColor.RED + ":(");
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "未知的指令");
                    break;
            }
        }

        return true;
    }
}
