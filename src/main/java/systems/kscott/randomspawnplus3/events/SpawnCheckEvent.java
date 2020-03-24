package systems.kscott.randomspawnplus3.events;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SpawnCheckEvent extends Event {

    @Getter
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Getter
    private Location location;

    @Getter
    private boolean valid;

    @Getter
    private String validReason = "UNK";


    public SpawnCheckEvent(Location location) {
        this.location = location;
        this.valid = true;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }


    public void setValid(boolean valid, String reason) {
        this.validReason = reason;
        this.valid = valid;
    }


}
