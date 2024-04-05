package z3roco01.meowclient.command.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.text.Text
import z3roco01.meowclient.MeowClient
import z3roco01.meowclient.command.Command
import z3roco01.meowclient.util.TextUtils

class ToggleCommand : Command("toggle") {
    override fun buildCmd(builder: LiteralArgumentBuilder<FabricClientCommandSource>) {
        val bld = builder.executes{context ->
            context.source.sendError(TextUtils.translate(".command.toggle.no_arg"))
            0
        }

        for(module in MeowClient.modules.modules)
            bld.then(ClientCommandManager.literal(module.name).executes{context ->
                module.toggle()

                var feedbackKey = ""
                if(module.isEnabled())
                    feedbackKey = ".command.toggle.enabled"
                else
                    feedbackKey = ".command.toggle.disabled"

                context.source.sendFeedback(Text.literal(module.name + " ").append(TextUtils.translate(feedbackKey)))

                1
            })
            /*.then(ClientCommandManager.argument("module", StringArgumentType.word())
                .executes{context ->
                    val name = StringArgumentType.getString(context, "module")
                    val moduleOpt = MeowClient.modules.getModule(name)

                    if(moduleOpt.isEmpty){
                        context.source.sendError(Text.literal(name + " ").append(TextUtils.translate(".command.toggle.no_module")))
                        return@executes 0
                    }

                    val module = moduleOpt.get()
                    module.toggle()

                    var feedbackKey = ""
                    if(module.isEnabled())
                        feedbackKey = ".command.toggle.enabled"
                    else
                        feedbackKey = ".command.toggle.disabled"

                    context.source.sendFeedback(Text.literal(module.name + " ").append(TextUtils.translate(feedbackKey)))

                   1
            })*/
    }
}