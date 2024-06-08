package ibotus.ibactionregions.commands;

import ibotus.ibactionregions.configurations.Config;
import ibotus.ibactionregions.utils.HexColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ActionRegionsCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;

    public ActionRegionsCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("ibactionregions.reload")) {
                Config.loadYaml(this.plugin);
            } else {
                sender.sendMessage(Objects.requireNonNull(HexColor.color(Config.getConfig().getString("messages.permission"))));
            }
        } else {
            sender.sendMessage(Objects.requireNonNull(HexColor.color(Config.getConfig().getString("messages.usage"))));
        }
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("reload");
        }
        return Collections.emptyList();
    }
}
