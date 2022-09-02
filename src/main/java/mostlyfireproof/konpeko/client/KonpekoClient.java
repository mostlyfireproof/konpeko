package mostlyfireproof.konpeko.client;

import mostlyfireproof.konpeko.Konpeko;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public class KonpekoClient implements ClientModInitializer {
    private static boolean glow = false;

    public static boolean getGlow() {
        return glow;
    }

    private boolean isPlayer(Entity entity) {
        boolean result = entity.getType().toString().equals("entity.minecraft.player");
        // System.out.println(entity.getType() + " " + result);
        return result;
    }

    @Override
    public void onInitializeClient() {
        KeyBinding toggleGlow = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.fabric-key-binding-api-v1-testmod.test_keybinding_1",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_G,
                        "key.category.first.test"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggleGlow.isPressed()) {
                glow = !glow;

                assert client.player != null;
                client.player.sendMessage(Text.literal("Glow: " + glow), false);
            }
        });
    }
}
