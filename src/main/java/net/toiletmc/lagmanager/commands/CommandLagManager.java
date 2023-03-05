package net.toiletmc.lagmanager.commands;

import net.toiletmc.lagmanager.LagManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandLagManager implements CommandExecutor {
    private final LagManager plugin;

    public CommandLagManager(LagManager plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!sender.hasPermission("lagmanager.admin")) {
            sender.sendMessage(ChatColor.RED + "未知的指令");
            return true;
        }

        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "test" -> plugin.getMSPTCheckTask().warnIfLagging();
                case "force" -> plugin.getMSPTCheckTask().warnWithoutLagging();
                case "reload" -> {
                    plugin.reloadConfig();
                    plugin.getMSPTCheckTask().setMaxMSPT(plugin.getConfig().getInt("max_mspt"));
                }
                case "debug" -> {
                    sender.sendMessage("当前mspt：" + plugin.getMSPTCheckTask().getDoubleMspt());
                    sender.sendMessage("最大mspt：" + plugin.getMSPTCheckTask().getMaxMSPT());
                }
                default -> sender.sendMessage(ChatColor.RED + "未知的指令");
            }
        }

        return true;
    }
}
