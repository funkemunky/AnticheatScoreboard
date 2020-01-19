package cc.funkemunky.scoreboard.listeners.custom;

import cc.funkemunky.api.events.Cancellable;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@RequiredArgsConstructor
public class AnticheatFlagEvent extends Event implements Cancellable {
    @Getter
    @Setter
    private boolean cancelled;

    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public final String anticheatName;
    public final Player player;
    public final CheckWrapper wrapper;
    public final float vl;
    public final String messaging;

    public HandlerList getHandlers() {
        return handlerList;
    }
}