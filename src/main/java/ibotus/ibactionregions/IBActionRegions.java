package ibotus.ibactionregions;

import ibotus.ibactionregions.commands.ActionRegionsCommand;
import ibotus.ibactionregions.configurations.Config;
import ibotus.ibactionregions.events.RegionsEvent;
import ibotus.ibactionregions.utils.HexColor;
import ibotus.ibactionregions.utils.RegionPlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class IBActionRegions extends JavaPlugin {

    private void msg(String msg) {
        String prefix = HexColor.color("&aIBActionRegions &7| ");
        Bukkit.getConsoleSender().sendMessage(HexColor.color(prefix + msg));
    }

    @Override
    public void onEnable() {
        Config.loadYaml(this);
        ActionRegionsCommand command = new ActionRegionsCommand(this);
        Objects.requireNonNull(this.getCommand("IBActionRegions")).setExecutor(command);
        Objects.requireNonNull(this.getCommand("IBActionRegions")).setTabCompleter(command);
        RegionsEvent regionEvent = new RegionsEvent(this);
        new RegionsEvent(this).startActionBarUpdateTask();
        new RegionPlaceholderExpansion(regionEvent).register();
        Bukkit.getConsoleSender().sendMessage("");
        this.msg("&fDeveloper: &aIBoTuS");
        this.msg("&fVersion: &dv" + this.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage("");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("");
        this.msg("&fDisable plugin.");
        Bukkit.getConsoleSender().sendMessage("");
    }
}
