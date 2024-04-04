package z3roco01.meowclient.module

import z3roco01.meowclient.module.modules.BetterHudModule
import z3roco01.meowclient.module.modules.FullBrightModule
import z3roco01.meowclient.module.modules.LeftStatusEffectsModule
import z3roco01.meowclient.module.modules.NoToastModule
import java.util.Optional
import kotlin.reflect.KClass

class Modules {
    val modules = ArrayList<Module>()

    init{
        modules.add(FullBrightModule())
        modules.add(BetterHudModule())
        modules.add(NoToastModule())
        modules.add(LeftStatusEffectsModule())
    }

    fun isEnabled(name: String): Boolean {
        val module = moduleByName(name)
        if(module.isEmpty)
            return false
        return module.get().isEnabled()
    }

    fun isEnabled(clazz: KClass<out Module>): Boolean {
        val module = moduleByClass(clazz)
        if(module.isEmpty)
            return false
        return module.get().isEnabled()
    }

    fun isEnabled(clazz: Class<out Module>): Boolean {
        val module = moduleByClass(clazz)
        if(module.isEmpty)
            return false
        return module.get().isEnabled()
    }

    fun moduleByName(name: String) : Optional<Module> {
        for(module in modules)
            if(module.name == name)
                return Optional.of(module)
        return Optional.empty<Module>()
    }

    fun moduleByClass(clazz: Class<out Module>): Optional<Module> {
        for(module in modules)
            if(module::class == clazz)
                return Optional.of(module)
        return Optional.empty<Module>()
    }

    fun moduleByClass(clazz: KClass<out Module>): Optional<Module> {
        for(module in modules)
            if(module::class == clazz)
                return Optional.of(module)
        return Optional.empty<Module>()
    }
}