package cc.funkemunky.scoreboard.anticheat;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Violation {
    public String checkName;
    public double vlCount;
    public boolean cancelled;
}
