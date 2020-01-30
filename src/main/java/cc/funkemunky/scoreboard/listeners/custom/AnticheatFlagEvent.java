package cc.funkemunky.scoreboard.listeners.custom;

import cc.funkemunky.api.events.AtlasEvent;
import cc.funkemunky.api.events.Cancellable;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class AnticheatFlagEvent extends AtlasEvent implements Cancellable {
    @Getter
    @Setter
    private boolean cancelled;

    public final String anticheatName;
    public final Player player;
    public final CheckWrapper wrapper;
    public final float vl;
    public final String messaging;
}