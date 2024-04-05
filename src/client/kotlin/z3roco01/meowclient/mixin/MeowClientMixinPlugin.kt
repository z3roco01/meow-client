package z3roco01.meowclient.mixin

import org.objectweb.asm.tree.ClassNode
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin
import org.spongepowered.asm.mixin.extensibility.IMixinInfo
import z3roco01.meowclient.MeowClient
import z3roco01.meowclient.config.MeowClientConfig
import z3roco01.meowclient.util.LoggerUtil
import java.io.File

class MeowClientMixinPlugin : IMixinConfigPlugin {
    private val MIXIN_PACKAGE_ROOT = "z3roco01.meowclient.mixin"

    override fun onLoad(mixinPackage: String?) {
        MeowClient.config.load(File(MeowClientConfig.MODULE_CONFIG_PATH))
        MeowClient.modules.loadFromConfig(MeowClient.config)
    }

    override fun shouldApplyMixin(targetClassName: String, mixinClassName: String): Boolean {
        return mixinClassName.startsWith(MIXIN_PACKAGE_ROOT)
    }

    override fun acceptTargets(myTargets: MutableSet<String>, otherTargets: MutableSet<String>) {
    }

    override fun getRefMapperConfig(): String? {
        return null
    }

    override fun getMixins(): MutableList<String>? {
        return null
    }

    override fun preApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {
    }

    override fun postApply(targetClassName: String, targetClass: ClassNode, mixinClassName: String, mixinInfo: IMixinInfo) {
    }
}