package mostlyfireproof.konpeko.mixin;

import mostlyfireproof.konpeko.client.KonpekoClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityGlowMixin {
    @Inject(at = @At("RETURN"), method = "isGlowing()Z", cancellable = true)
    private void injected(CallbackInfoReturnable<Boolean> info) {
        boolean play = ((Entity)(Object)this).isPlayer();
        info.setReturnValue(KonpekoClient.getGlow() && play);
    }
}
