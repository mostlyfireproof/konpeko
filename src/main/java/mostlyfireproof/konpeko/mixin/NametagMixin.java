package mostlyfireproof.konpeko.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class NametagMixin {
    private String formatHealth(Float health) { //, Float maxHealth) {
        return String.valueOf(health.intValue());
    }

    @ModifyVariable(method = "renderLabelIfPresent", at = @At("HEAD"), argsOnly = true)
    private Text injected(Text value, Entity entity) {
        if (entity instanceof PlayerEntity) {
            LivingEntity le = (LivingEntity) entity;
            String nametag = value.getString();
            nametag += " ♡" + formatHealth(le.getHealth()); //, le.getMaxHealth());
            return Text.of(nametag);
        }
        else {
            return value;
        }
//        Text customText = Text.of("test");
//        return customText;
    }
}
