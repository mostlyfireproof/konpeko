package mostlyfireproof.konpeko.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KonpekoClient implements ClientModInitializer {
    private static boolean glow = false;

    public static boolean getGlow() {
        return glow;
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
                client.player.sendMessage(Text.literal("Glow: " + glow), false);
                glow = !glow;

                assert client.world != null;
                Iterable<Entity> entities = client.world.getEntities();

                for (Entity e: entities) {
                    //if (e.isPlayer()) {
                        e.setGlowing(glow);
                    //}
                }
            }
        });
    }
}
