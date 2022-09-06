package mostlyfireproof.konpeko.client;

import mostlyfireproof.konpeko.Konpeko;
import mostlyfireproof.konpeko.ui.MenuGui;
import mostlyfireproof.konpeko.ui.MenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class KonpekoClient implements ClientModInitializer {
    private static boolean glow = false;
    private static boolean avoidAC = false;
    private Random random = new Random();

    public static boolean getGlow() {
        return glow;
    }

    public static void toggleGlow() {
        glow = !glow;
        Konpeko.LOGGER.info("Glow: " + glow);
    }

    /**
     * Turns the glow effect off or on
     * @param b true for on, false for off
     */
    public static void setGlow(boolean b) {
        glow = b;
        // Konpeko.LOGGER.debug("Glow: " + glow);
    }

    public static void setAvoidAC(boolean b) {
        avoidAC = b;
        // Konpeko.LOGGER.debug("avoidAC: " + avoidAC);
    }

    /**
     * Checks if a given entity is a player
     * @param entity
     * @return
     */
    private boolean isPlayer(Entity entity) {
        boolean result = entity.getType().toString().equals("entity.minecraft.player");
        // System.out.println(entity.getType() + " " + result);
        return result;
    }

    /**
     * Attacks whatever entity is in the player's crosshair
     * @param client
     */
    public void attackEntity(MinecraftClient client) {
        if (client.crosshairTarget == null) {
            Konpeko.LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
        }
        else if (client.player.isRiding()) {
            return;
        }
        else {
            switch (client.crosshairTarget.getType()) {
                case ENTITY:
                    // miss chance to try to avoid AC
                    if (!avoidAC || random.nextFloat() <= 0.3F) {
                        if (client.player.getAttackCooldownProgress(0) == 1.0) {
                            client.interactionManager.attackEntity(client.player, ((EntityHitResult)client.crosshairTarget).getEntity());
                            client.player.swingHand(Hand.MAIN_HAND);
                        }
                    }
                    break;
                case BLOCK:
                case MISS:
                    if (!avoidAC || random.nextFloat() <= .3F) {
                        client.player.swingHand(Hand.MAIN_HAND);
                    }
                    break;
            }
        }
    }

    @Override
    public void onInitializeClient() {
        KeyBinding autoSwing = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.konpeko.keybind_1",
                        // InputUtil.Type.MOUSE,
                        // GLFW.GLFW_MOUSE_BUTTON_4,
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_G,
                        "key.category.first.test"));
        KeyBinding openMenu = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.konpeko.keybind_2",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_V,
                        "key.category.first.test"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (autoSwing.isPressed()) {
                //toggleGlow();
                attackEntity(MinecraftClient.getInstance());
            }
            if (openMenu.isPressed()) {
                MinecraftClient.getInstance().setScreen(new MenuScreen(new MenuGui()));
            }

        });
    }
}
