package z3roco01.meowclient

import net.fabricmc.api.ClientModInitializer
import z3roco01.meowclient.command.Commands
import z3roco01.meowclient.config.MeowClientConfig
import z3roco01.meowclient.module.Modules
import z3roco01.meowclient.util.LoggerUtil

object MeowClient : ClientModInitializer {
	val modId = "meow_client"
	val commands = Commands()
	val modules = Modules()
	val config = MeowClientConfig()

	override fun onInitializeClient() {
		LoggerUtil.info("starting meownit :3 ")

		commands.register()

		LoggerUtil.info("finished meownit :p ")
	}
}