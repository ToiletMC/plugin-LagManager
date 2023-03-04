package net.toiletmc.lagmanager;

import me.lucko.spark.api.Spark;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.toiletmc.lagmanager.commands.CommandLagManager;
import net.toiletmc.lagmanager.listeners.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.dependency.Dependency;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.Website;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.plugin.java.annotation.plugin.author.Authors;

import java.util.logging.Logger;


@Plugin(name = "LagManager", version = "1.1.4")
@Dependency("spark")
@ApiVersion(ApiVersion.Target.v1_19)
@Authors(@Author("TheLittle_Yang"))
@Website("https://toiletmc.net")
public final class LagManager extends JavaPlugin {
    private final TextColor textColor = TextColor.fromCSSHexString("#9fdd8c");
    private static Observer observer;
    private Spark spark;
    private final Component hexToiletMC = MiniMessage.miniMessage().deserialize("<gradient:#02a4d3:#007ec7> ToiletMC »</gradient><#9fdd8c> ");
    private static LagManager instance;

    @Override
    public void onEnable() {
        instance = this;

        // 挂钩 spark
        RegisteredServiceProvider<Spark> provider = Bukkit.getServicesManager().getRegistration(Spark.class);
        if (provider != null) {
            spark = provider.getProvider();
            getLogger().info("已挂钩到 spark");
        } else {
            getLogger().severe("spark 服务异常，已停用插件");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        observer = new Observer(this);

        getServer().getPluginManager().registerEvents((new PlayerJoin(this)), this);
        getCommand("lagmanager").setExecutor(new CommandLagManager(this));

        getServer().getScheduler().runTaskTimer(this, () -> {
            observer.warnLimitedIfLagging();
        }, 20L * 30L, 20L * 30L);

        Logger().info("正在监控服务器，如果服务器变卡我会通知玩家 :)");
    }

    @Override
    public void onDisable() {

    }

    public static LagManager getInstance() {
        return instance;
    }

    public Logger Logger() {
        return getLogger();
    }

    public Spark getSpark() {
        return spark;
    }

    public Component getHexToiletMC() {
        return hexToiletMC;
    }

    public TextColor textColor() {
        return textColor;
    }

    public Observer getObserver() {
        return observer;
    }
}
