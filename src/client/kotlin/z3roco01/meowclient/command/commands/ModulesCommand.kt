package z3roco01.meowclient.command.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import z3roco01.meowclient.MeowClient
import z3roco01.meowclient.command.Command
import z3roco01.meowclient.util.TextUtils

class ModulesCommand: Command("modules") {
    override fun buildCmd(builder: LiteralArgumentBuilder<FabricClientCommandSource>) {
        builder.executes{context ->
            for(module in MeowClient.modules.modules)
                context.source.sendFeedback(Text.literal(module.name).append(" ")
                    .append(TextUtils.translate(if(module.isEnabled()) ".command.commands.enabled" else ".command.commands.disabled")))
            1
        }
    }
}