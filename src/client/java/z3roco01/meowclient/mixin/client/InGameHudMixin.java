package z3roco01.meowclient.mixin.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z3roco01.meowclient.MeowClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow private int scaledWidth;

    @Shadow @Final private static Identifier EFFECT_BACKGROUND_TEXTURE;

    @Shadow @Final private static Identifier EFFECT_BACKGROUND_AMBIENT_TEXTURE;

    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void renderStatusEffectOverlay(DrawContext context, CallbackInfo ci) {
        if(!MeowClient.INSTANCE.getModules().isEnabled("l_effect"))
            return;

        ci.cancel();
        MinecraftClient client = MinecraftClient.getInstance();
        AbstractInventoryScreen abstractInventoryScreen;
        Screen screen;
        Collection<StatusEffectInstance> collection = Objects.requireNonNull(client.player).getStatusEffects();
        if (collection.isEmpty() || (screen = client.currentScreen) instanceof AbstractInventoryScreen && (abstractInventoryScreen = (AbstractInventoryScreen)screen).hideStatusEffectHud()) {
            return;
        }
        RenderSystem.enableBlend();
        int i = 0;
        int j = 0;
        StatusEffectSpriteManager statusEffectSpriteManager = client.getStatusEffectSpriteManager();
        ArrayList<Runnable> list = Lists.newArrayListWithExpectedSize(collection.size());
        for (StatusEffectInstance statusEffectInstance : Ordering.natural().reverse().sortedCopy(collection)) {
            int n;
            StatusEffect statusEffect = statusEffectInstance.getEffectType();
            if (!statusEffectInstance.shouldShowIcon()) continue;
            int k = 1;
            int l = 1;
            if (client.isDemo()) {
                l += 15;
            }
            if (statusEffect.isBeneficial()) {
                k += 25 * i++;
            } else {
                l -= 26;
            }
            float f = 1.0f;
            if (statusEffectInstance.isAmbient()) {
                context.drawGuiTexture(EFFECT_BACKGROUND_AMBIENT_TEXTURE, k, l, 24, 24);
            } else {
                context.drawGuiTexture(EFFECT_BACKGROUND_TEXTURE, k, l, 24, 24);
                if (statusEffectInstance.isDurationBelow(200)) {
                    int m = statusEffectInstance.getDuration();
                    n = 10 - m / 20;
                    f = MathHelper.clamp(((float)m / 10.0f / 5.0f * 0.5f), 0.0f, 0.5f) + MathHelper.cos(((float)m * (float)Math.PI / 5.0f)) * MathHelper.clamp(((float)n / 10.0f * 0.25f), 0.0f, 0.25f);
                }
            }
            Sprite sprite = statusEffectSpriteManager.getSprite(statusEffect);
            n = k;
            int o = l;
            float g = f;
            int finalN = n;
            list.add(() -> {
                context.setShaderColor(1.0f, 1.0f, 1.0f, g);
                context.drawSprite(finalN + 3, o + 3, 0, 18, 18, sprite);
                context.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            });
        }
        list.forEach(Runnable::run);
    }
}
