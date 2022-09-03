package mostlyfireproof.konpeko.ui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import io.github.cottonmc.cotton.gui.widget.icon.ItemIcon;
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

//        WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
//        root.add(icon, 0, 2, 1, 1);

        WButton button = new WButton(Text.literal("Toggle Glow"));
        button.setOnClick(KonpekoClient::toggleGlow);
        button.setIcon(new ItemIcon(new ItemStack(Items.GLOWSTONE_DUST)));

        root.add(button, 0, 3, 20, 20);

        WLabel label = new WLabel(Text.literal("Test"), 0xFFFFFF);
        root.add(label, 0, 4, 2, 1);

        root.validate(this);

    }
}
