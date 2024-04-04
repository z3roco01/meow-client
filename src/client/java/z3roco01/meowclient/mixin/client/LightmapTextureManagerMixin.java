package z3roco01.meowclient.mixin.client;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.resource.SynchronousResourceReloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import z3roco01.meowclient.MeowClient;
import z3roco01.meowclient.module.Module;

import java.util.Optional;

@Mixin(LightmapTextureManager.class)
public abstract class LightmapTextureManagerMixin implements SynchronousResourceReloader,  AutoCloseable {
    @ModifyArg(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/NativeImage;setColor(III)V"), index = 2)
    private int updateSetColor(int x, int y, int color) {
        Optional<Module> fullBrightOpt = MeowClient.INSTANCE.getModules().moduleByName("full_bright");
        if(fullBrightOpt.isPresent() && fullBrightOpt.get().isEnabled())
            return 0xFFFFFFFF;
        else
            return color;
    }
}
