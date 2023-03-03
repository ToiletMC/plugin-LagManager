package net.toiletmc.lagmanager;

import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

public class Observer {
    private final LagManager plugin;
    private final Spark spark;
    private double doubleMspt;
    private int broTimes = 0;

    private void refresh() {
        GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> msptInfo = spark.mspt();
        DoubleAverageInfo msptLastMin = msptInfo.poll(StatisticWindow.MillisPerTick.SECONDS_10);
        this.doubleMspt = msptLastMin.max();
    }

    public Observer(LagManager plugin) {
        this.plugin = plugin;
        this.spark = plugin.getSpark();
    }

    public double getDoubleMspt() {
        refresh();
        return doubleMspt;
    }

    public void warnIfLagging() {
        if (getDoubleMspt() >= 150) {
            broadcastMessage();
        }
    }

    public void warnWithoutLagging() {
        broadcastMessage();
    }

    private void broadcastMessage() {
        if (broTimes == 0) {
            ((Audience) Bukkit.getServer()).sendMessage(plugin.getHexToiletMC()
                    .append(Component.text("当前服务器负载较高，请勿在大型刷怪塔或粘液基地久留。\n详情：")
                            .color(plugin.textColor()))
                    .append(Component.text("https://toiletmc.net/help/start/rules")
                            .clickEvent(ClickEvent.openUrl("https://toiletmc.net/help/start/rules"))
                            .color(plugin.textColor())
                            .decorate(TextDecoration.UNDERLINED)));
        }

        if (broTimes >= 15) {
            broTimes = 0;
        } else {
            broTimes++;
        }
    }
}
