package net.toiletmc.lagmanager;

import me.lucko.spark.api.Spark;
import net.toiletmc.lagmanager.commands.CommandLagManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Plugin;

import java.util.logging.Logger;

@Plugin(name = "LagManager", version = "1.0")
public final class LagManager extends JavaPlugin {
    private static Spark spark;
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        RegisteredServiceProvider<Spark> provider = Bukkit.getServicesManager().getRegistration(Spark.class);
        if (provider != null) {
            spark = provider.getProvider();
        } else {
            getLogger().severe("Failed to get Spark service");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        this.getCommand("lagreminder").setExecutor(new CommandLagManager());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public JavaPlugin getInstance() {
        return instance;
    }

    public static Logger Logger() {
        return instance.getLogger();
    }

    public static Spark getSpark() {
        return spark;
    }
}
