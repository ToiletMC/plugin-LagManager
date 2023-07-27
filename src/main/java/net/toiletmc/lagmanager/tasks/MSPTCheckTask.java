package net.toiletmc.lagmanager.tasks;

import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.toiletmc.lagmanager.LagManager;
import org.bukkit.Bukkit;

public class MSPTCheckTask implements Runnable {
    private final LagManager plugin;
    private final Spark spark;
    private double doubleMspt;
    private int broTimes = 0;
    private boolean skip = false;
    private int maxMSPT = 150;

    public MSPTCheckTask(LagManager plugin) {
        this.plugin = plugin;
        this.spark = plugin.getSpark();
    }

    @Override
    public void run() {
        if (skip) {
            skip = false;
            return;
        }

        if (broTimes != 0) {
            broTimes = broTimes >= 15 ? 0 : broTimes + 1;
            return;
        }

        if (getDoubleMspt() >= maxMSPT) {
            broadcastMessage();
            broTimes++;
        }
    }

    public int getMaxMSPT() {
        return maxMSPT;
    }

    public void setMaxMSPT(int maxMSPT) {
        this.maxMSPT = maxMSPT;
    }

    public double getDoubleMspt() {
        refresh();
        return doubleMspt;
    }

    public void warnWithoutLagging() {
        broadcastMessage();
    }

    public void warnIfLagging() {
        if (getDoubleMspt() >= maxMSPT) {
            broadcastMessage();
        }
    }

    public void setSkip() {
        this.skip = true;
    }

    private void refresh() {
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> msptInfo = spark.mspt();
        DoubleAverageInfo msptLastMin = msptInfo.poll(StatisticWindow.MillisPerTick.MINUTES_1);
        this.doubleMspt = msptLastMin.max();
    }

    private void broadcastMessage() {
        String message = plugin.getConfig().getString("message.lag_broadcast").replaceAll(
                "%mspt%", String.valueOf(spark.mspt().poll(StatisticWindow.MillisPerTick.MINUTES_1).max()));
        Bukkit.getServer().sendMessage(MiniMessage.miniMessage().deserialize(message));

    }
}
