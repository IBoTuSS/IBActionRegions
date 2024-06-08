package ibotus.ibactionregions.utils;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import ibotus.ibactionregions.configurations.Config;
import ibotus.ibactionregions.events.RegionsEvent;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RegionPlaceholderExpansion extends PlaceholderExpansion {

    private final RegionsEvent regionEvent;

    public RegionPlaceholderExpansion(RegionsEvent regionEvent) {
        this.regionEvent = regionEvent;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "ArkanaRegion";
    }

    @Override
    public @NotNull String getAuthor() {
        return "IBoTuS";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        switch (identifier) {
            case "region": {
                ProtectedRegion currentRegion = regionEvent.getCurrentRegion(player);
                return currentRegion != null ? Config.getConfig().getString("region-settings.region-actionbar") : "";
            }
            case "no-region": {
                ProtectedRegion currentRegion = regionEvent.getCurrentRegion(player);
                return currentRegion == null ? Config.getConfig().getString("region-settings.no-region-actionbar") : "";
            }
            case "owner": {
                ProtectedRegion currentRegion = regionEvent.getCurrentRegion(player);
                return currentRegion != null && currentRegion.getOwners().contains(player.getUniqueId()) ? currentRegion.getId() : "";
            }
        }

        return null;
    }
}