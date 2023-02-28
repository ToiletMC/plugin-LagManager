package net.toiletmc.lagmanager.commands;

import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import net.toiletmc.lagmanager.LagManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;

@Commands(@org.bukkit.plugin.java.annotation.command.Command(name = "lagmanager", permission = "lagmanager.command.admin"))
@Permission(name = "lagmanager.command.admin", defaultValue = PermissionDefault.OP)
public class CommandLagManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Get the MSPT statistic (will be null on platforms that don't support measurement!)
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> mspt = LagManager.getSpark().mspt();

        // Retrieve the averages in the last minute
        DoubleAverageInfo msptLastMin = mspt.poll(StatisticWindow.MillisPerTick.MINUTES_1);
        double msptMean = msptLastMin.mean();
        double mspt95Percentile = msptLastMin.percentile95th();

        if (msptMean >= 250) {
            Bukkit.broadcastMessage("检测到服务器较卡，请...");//todo 修改提示语句，并添加链接提示。
        }

        sender.sendMessage("Command Success!");

        return true;
    }
}
