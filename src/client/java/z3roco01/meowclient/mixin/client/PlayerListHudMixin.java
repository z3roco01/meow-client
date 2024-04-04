package z3roco01.meowclient.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.network.ClientConnection;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z3roco01.meowclient.MeowClient;
import z3roco01.meowclient.module.modules.BetterHudModule;

@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Inject(method = "applyGameModeFormatting", at = @At("HEAD"), cancellable = true)
    private void applyGameModeFormatting(PlayerListEntry entry, MutableText name, CallbackInfoReturnable<Text> cir) {
        if(!MeowClient.INSTANCE.getModules().isEnabled("b_hud"))
            return;
        cir.cancel();
        String gmStr = switch(entry.getGameMode()) {
            case SURVIVAL -> "SRV";
            case CREATIVE -> "CRV";
            case ADVENTURE -> "ADV";
            case SPECTATOR -> "SPC";
        };

        // .copy() required to get a MutableText object so we can then .appened()
        cir.setReturnValue(Text.of("[" + gmStr + "] ").copy().append(name));
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private int modifyWidthArg(int a, int b) {
        if(MeowClient.INSTANCE.getModules().isEnabled("b_hud"))
            return Math.min(a, b) + MinecraftClient.getInstance().textRenderer.getWidth("9999ms");
        else
            return Math.min(a, b);
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;isEncrypted()Z"))
    private boolean isEncryptedRedirect(ClientConnection instance) {
        return true;
    }

    @Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
    private void renderLatencyIcon(DrawContext context, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci) {
        if(!MeowClient.INSTANCE.getModules().isEnabled("b_hud"))
            return;

        ci.cancel();

        context.getMatrices().push();
        context.getMatrices().translate(0.0f, 0.0f, 100.0f);

        int lat = entry.getLatency();

        String latStr = MathHelper.clamp(lat, 0, 9999) + "";
        String msStr = "ms";
        int colour = (lat < 150 ? 0xFF00FF21 : ( lat < 300 ? 0xFFFFA500 : 0xFFA90003));
        TextRenderer tr = MinecraftClient.getInstance().textRenderer;
        tr.draw(latStr, x + width - tr.getWidth(latStr) - tr.getWidth(msStr), y, colour, true, context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 0xF000F0);
        tr.draw("ms", x + width - tr.getWidth(msStr), y, 0xFFFFFFFF, true, context.getMatrices().peek().getPositionMatrix(), context.getVertexConsumers(), TextRenderer.TextLayerType.NORMAL, 0, 0xF000F0);

        context.getMatrices().pop();
    }
}
