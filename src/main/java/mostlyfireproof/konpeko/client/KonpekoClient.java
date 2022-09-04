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

@Environment(EnvType.CLIENT)
public class KonpekoClient implements ClientModInitializer {
    private static boolean glow = false;

    public static boolean getGlow() {
        return glow;
    }

    public static void toggleGlow() {
        glow = !glow;
        Konpeko.LOGGER.info("Glow: " + glow);
    }

    /**
     * Turns the glow effect off or on
     * @param b
     */
    public static void setGlow(boolean b) {
        glow = b;
        Konpeko.LOGGER.info("Glow: " + glow);
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
     * Copied from MinecraftClient.java doItemUse() since that's private
     * @param client
     */
    public boolean attackEntity(MinecraftClient client) {
//        client.player.getAttackCooldownProgressPerTick()
        if (client.crosshairTarget == null) {
            Konpeko.LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
            return false;
        }
        else if (client.player.isRiding()) {
            return false;
        }
        else {
            boolean bl = false;
            switch (client.crosshairTarget.getType()) {
                case ENTITY:
                    client.interactionManager.attackEntity(client.player, ((EntityHitResult)client.crosshairTarget).getEntity());
                    break;
                case BLOCK:
                    BlockHitResult blockHitResult = (BlockHitResult)client.crosshairTarget;
                    BlockPos blockPos = blockHitResult.getBlockPos();
                    if (!client.world.getBlockState(blockPos).isAir()) {
                        client.interactionManager.attackBlock(blockPos, blockHitResult.getSide());
                        if (client.world.getBlockState(blockPos).isAir()) {
                            bl = true;
                        }
                        break;
                    }
                case MISS:
                    client.player.resetLastAttackedTicks();
            }

            client.player.swingHand(Hand.MAIN_HAND);
            return bl;
        }
    }

    @Override
    public void onInitializeClient() {
        KeyBinding autoSwing = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.konpeko.keybind_1",
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
