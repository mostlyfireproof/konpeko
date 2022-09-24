package mostlyfireproof.konpeko.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(EntityRenderer.class)
public class NametagMixin {
    @ModifyVariable(method = "renderLabelIfPresent", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private Text injected(Text value) {
        Text customText = Text.of("test");
        return customText;
    }
//    @ModifyVariable(method = "renderLabelIfPresent(LEntity;LText;LMatrixStack;LVertexConsumerProvider;I)V", at = @At("HEAD"), argsOnly = true, index = 1)
//    private Text injected(Text text) {
//        Text newText; //= ???
//        return newText;
//    }

}
