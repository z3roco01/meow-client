package z3roco01.meowclient.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z3roco01.meowclient.MeowClient;

@Mixin(ToastManager.class)
public abstract class ToastManagerMixin {
    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
    private void draw(DrawContext context, CallbackInfo ci) {
        if(MeowClient.INSTANCE.getModules().isEnabled("no_toast"))
            ci.cancel();
    }
}
