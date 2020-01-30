package cc.funkemunky.scoreboard.wrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CheckWrapper {
    private final String name;
    private final boolean punishable, cancellable;
    private final String type;
}