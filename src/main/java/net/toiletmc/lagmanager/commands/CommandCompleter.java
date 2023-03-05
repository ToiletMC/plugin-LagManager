package net.toiletmc.lagmanager.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        final String[] COMMANDS = {"test", "force", "debug", "reload"};

        List<String> list = new ArrayList<>();

        StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), list);

        Collections.sort(list);

        return list;
    }
}
