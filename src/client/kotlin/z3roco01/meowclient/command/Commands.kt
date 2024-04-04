package z3roco01.meowclient.command

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import z3roco01.meowclient.command.commands.ModulesCommand
import z3roco01.meowclient.command.commands.ToggleCommand
import java.util.ArrayList

class Commands {
    val commands = ArrayList<Command>()
    init {
        addCommand(ToggleCommand())
        addCommand(ModulesCommand())
    }

    fun register() {
        ClientCommandRegistrationCallback.EVENT.register{dispatcher, registryAccess ->
        for(command in commands)
            command.register(dispatcher, registryAccess)
        }
    }

    private fun addCommand(cmd: Command) {
        commands.add(cmd)
    }
}