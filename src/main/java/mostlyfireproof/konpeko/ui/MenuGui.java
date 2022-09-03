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
    public MenuGui() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        //WSprite icon = new WSprite(new Identifier("minecraft:textures/item/golden_carrot.png"));
        //root.add(icon, 10, 10, 1, 1);

        WLabel label = new WLabel(Text.literal("Konpeko Menu"), 0x3f3f3f); // 0x3f3f3f
        root.add(label, 5, 0, 2, 1);

        WToggleButton toggleButtonGlow = new WToggleButton(Text.literal("Toggle Glow"));
        toggleButtonGlow.setOnToggle(on -> {
            if (on) {
                KonpekoClient.setGlow(true);
            } else {
                KonpekoClient.setGlow(false);
            }
        });
        root.add(toggleButtonGlow, 0, 2, 10, 1);

        WToggleButton toggleButtonRolex = new WToggleButton(Text.literal("Force Rolex"));
        toggleButtonRolex.setOnToggle(on -> {
            Konpeko.LOGGER.info("Rolex toggled to " + (on ? "on" : "off"));
        });
        //toggleButtonRolex.setOnImage(new Texture(new Identifier("minecraft:textures/item/redstone.png")));

        root.add(toggleButtonRolex, 0, 3, 10, 1);


        root.validate(this);

    }
}
