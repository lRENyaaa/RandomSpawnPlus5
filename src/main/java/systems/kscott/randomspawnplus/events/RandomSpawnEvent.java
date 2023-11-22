package systems.kscott.randomspawnplus.events;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RandomSpawnEvent extends Event {

    @Getter
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    @Getter
    private final Location location;
    @Getter
    private final Player player;
    @Getter
    private final SpawnType spawnType;


    public RandomSpawnEvent(Location location, Player player, SpawnType spawnType) {
        this.location = location;
        this.player = player;
        this.spawnType = spawnType;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
