package net.toiletmc.lagmanager;

import me.lucko.spark.api.Spark;
import net.kyori.adventure.text.format.TextColor;
import net.toiletmc.lagmanager.commands.CommandCompleter;
import net.toiletmc.lagmanager.commands.CommandLagManager;
import net.toiletmc.lagmanager.listeners.onPlayerJoin;
import net.toiletmc.lagmanager.tasks.MSPTCheckTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LagManager extends JavaPlugin {
    private MSPTCheckTask checkTask;
    private final TextColor textColor = TextColor.fromCSSHexString("#9fdd8c");
    private Spark spark;
    private static LagManager instance;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();

        // 挂钩 spark
        RegisteredServiceProvider<Spark> provider = Bukkit.getServicesManager().getRegistration(Spark.class);
        if (provider != null) {
            spark = provider.getProvider();
            getLogger().info("已挂钩到 spark");
        } else {
            getLogger().severe("spark 服务异常，已停用插件");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        checkTask = new MSPTCheckTask(this);
        getCommand("lagmanager").setExecutor(new CommandLagManager(this));
        getCommand("lagmanager").setTabCompleter(new CommandCompleter());
        getServer().getPluginManager().registerEvents((new onPlayerJoin(this)), this);
        getServer().getScheduler().runTaskTimer(this, checkTask, 20L * 30L, 20L * 30L);

        Logger().info("正在监控服务器，如果服务器变卡我会通知玩家 :)");
    }

    @Override
    public void onDisable() {

    }

    public Logger Logger() {
        return getLogger();
    }

    public Spark getSpark() {
        return spark;
    }

    public TextColor textColor() {
        return textColor;
    }

    public MSPTCheckTask getMSPTCheckTask() {
        return checkTask;
    }
}
