package mostlyfireproof.konpeko.ui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.data.Texture;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
import mostlyfireproof.konpeko.Konpeko;
import mostlyfireproof.konpeko.client.KonpekoClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;

public class MenuGui extends LightweightGuiDescription {
    private int defaultWidth = 256;
    private int defaultHeight = 100;
    public MenuGui() {
        // Setup:
        WTabPanel tabs = new WTabPanel();
        setRootPanel(tabs);

        WGridPanel combat = new WGridPanel();
        combat.setSize(defaultWidth, defaultHeight);
        combat.setInsets(Insets.ROOT_PANEL);

        WGridPanel util = new WGridPanel();
        util.setSize(defaultWidth, defaultHeight);
        util.setInsets(Insets.ROOT_PANEL);

        WGridPanel misc = new WGridPanel();
        misc.setSize(defaultWidth, defaultHeight);
        misc.setInsets(Insets.ROOT_PANEL);

        WGridPanel info = new WGridPanel();
        info.setSize(defaultWidth, defaultHeight);
        info.setInsets(Insets.ROOT_PANEL);

        tabs.add(combat, tab -> tab.title(Text.literal("Combat")));
        tabs.add(util, tab -> tab.title(Text.literal("Util")));
        tabs.add(misc, tab -> tab.title(Text.literal("Misc")));
        tabs.add(info, tab -> tab.title(Text.literal("Info")));


        // Combat Menu:
        WToggleButton toggleButtonGlow = new WToggleButton(Text.literal("Toggle Glow"));
        toggleButtonGlow.setOnToggle(on -> {
            if (on) {
                KonpekoClient.setGlow(true);
            } else {
                KonpekoClient.setGlow(false);
            }
            Konpeko.LOGGER.info("Glow toggled to " + (on ? "on" : "off"));
        });
        combat.add(toggleButtonGlow, 0, 0, 10, 1);

        WToggleButton toggleAntiAntiCheat = new WToggleButton(Text.literal("Toggle AC Avoidance"));
        toggleAntiAntiCheat.setOnToggle(on -> {
            if (on) {
                KonpekoClient.setAvoidAC(true);
            } else {
                KonpekoClient.setAvoidAC(false);
            }
            Konpeko.LOGGER.info("AntiAntiCheat toggled to " + (on ? "on" : "off"));
        });
        combat.add(toggleAntiAntiCheat, 0, 1, 10, 1);


        // Utility Menu:
        // ideas: name scrambler, freecam

        // Misc. Menu:
        WToggleButton toggleButtonRolex = new WToggleButton(Text.literal("Force Rolex"));
        toggleButtonRolex.setOnToggle(on -> {
            Konpeko.LOGGER.info("Rolex toggled to " + (on ? "on" : "off"));
        });
        //toggleButtonRolex.setOnImage(new Texture(new Identifier("minecraft:textures/item/redstone.png")));
        misc.add(toggleButtonRolex, 0, 0);


        // Info Menu:
        WLabel title = new WLabel(Text.literal("Konpeko"), 0x3f3f3f); // 0x3f3f3f
        info.add(title, 5, 0, 2, 1);
        WLabel author = new WLabel(Text.literal("A Minecraft utility mod by mostlyfireproof"));
        info.add(author, 0, 1);

        //WSprite icon = new WSprite(new Identifier("minecraft:textures/item/golden_carrot.png"));
        //root.add(icon, 10, 10, 1, 1);


        // Validation:
        combat.validate(this);
        util.validate(this);
        misc.validate(this);
        info.validate(this);
        tabs.validate(this);

    }
}
