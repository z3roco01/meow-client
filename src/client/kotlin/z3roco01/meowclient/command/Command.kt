package z3roco01.meowclient.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.command.CommandRegistryAccess

open class Command(val name: String){

    fun register(dispatcher: CommandDispatcher<FabricClientCommandSource>, registryAccess: CommandRegistryAccess) {
        val builder = ClientCommandManager.literal(name)
        this.buildCmd(builder)
        dispatcher.register(builder)
    }

    // Builder method, called after the name for the command is set, that builder is passed
    protected open fun buildCmd(builder: LiteralArgumentBuilder<FabricClientCommandSource>) {}
}