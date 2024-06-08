package ibotus.ibactionregions.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import ibotus.ibactionregions.configurations.Config;
import ibotus.ibactionregions.utils.HexColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class RegionsEvent {

    private final JavaPlugin plugin;

    public RegionsEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public ProtectedRegion getCurrentRegion(Player player) {
        Location location = BukkitAdapter.adapt(player.getLocation());
        ApplicableRegionSet regions = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery().getApplicableRegions(location);
        return regions.getRegions().isEmpty() ? null : regions.iterator().next();
    }

    public void startActionBarUpdateTask() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (Config.getConfig().getBoolean("region-settings.enable")) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    ProtectedRegion currentRegion = getCurrentRegion(player);
                    String message;
                    if (currentRegion == null) {
                        message = Config.getConfig().getString("region-settings.no-region-actionbar");
                    } else {
                        if (Config.getConfig().getBoolean("region-custom-settings.enable")) {
                            if (currentRegion.getOwners().contains(player.getUniqueId())) {
                                message = Config.getConfig().getString("region-custom-settings.owner");
                            } else if (Objects.requireNonNull(Config.getConfig().getConfigurationSection("region-custom-settings.custom-regions")).getKeys(false).contains(currentRegion.getId())) {
                                message = Config.getConfig().getString("region-custom-settings.custom-regions." + currentRegion.getId());
                            } else {
                                message = Config.getConfig().getString("region-settings.region-actionbar");
                            }
                        } else {
                            message = Config.getConfig().getString("region-settings.region-actionbar");
                        }
                    }
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Objects.requireNonNull(HexColor.color(message))));
                }
            }
        }, 0L, 20L);
    }
}

