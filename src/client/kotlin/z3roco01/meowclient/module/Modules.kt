package z3roco01.meowclient.module

import z3roco01.meowclient.config.MeowClientConfig
import z3roco01.meowclient.module.modules.*
import java.util.Optional
import kotlin.reflect.KClass

class Modules {
    val modules = ArrayList<Module>()

    init{
        modules.add(FullBrightModule())
        modules.add(BetterHudModule())
        modules.add(NoToastModule())
        modules.add(LeftStatusEffectsModule())
        modules.add(NoFogModule())
    }

    fun isEnabled(name: String): Boolean {
        val module = getModule(name)
        if(module.isEmpty)
            return false
        return module.get().isEnabled()
    }

    fun isEnabled(clazz: KClass<out Module>): Boolean {
        val module = getModule(clazz)
        if(module.isEmpty)
            return false
        return module.get().isEnabled()
    }

    fun isEnabled(clazz: Class<out Module>): Boolean {
        val module = getModule(clazz)
        if(module.isEmpty)
            return false
        return module.get().isEnabled()
    }

    fun getModule(name: String) : Optional<Module> {
        for(module in modules)
            if(module.name == name)
                return Optional.of(module)
        return Optional.empty<Module>()
    }

    fun getModule(clazz: Class<out Module>): Optional<Module> {
        for(module in modules)
            if(module::class == clazz)
                return Optional.of(module)
        return Optional.empty<Module>()
    }

    fun getModule(clazz: KClass<out Module>): Optional<Module> {
        for(module in modules)
            if(module::class == clazz)
                return Optional.of(module)
        return Optional.empty<Module>()
    }

    fun loadFromConfig(config: MeowClientConfig) {
        for(prop in config.getPropertiesIter()) {
            val moduleOpt = getModule(prop.key)
            if(moduleOpt.isEmpty)
                continue

            val module = moduleOpt.get()
            module.setEnabledLoading(prop.value, true)
        }
    }
}