package cc.funkemunky.scoreboard.utils.menu.preset.button;

import cc.funkemunky.api.utils.ItemBuilder;
import cc.funkemunky.scoreboard.utils.menu.button.Button;
import org.bukkit.Material;

public class FillerButton extends Button {

    public FillerButton() {
        super(false, new ItemBuilder(Material.STAINED_GLASS_PANE).name(" ").durability(15).build());
    }
}
