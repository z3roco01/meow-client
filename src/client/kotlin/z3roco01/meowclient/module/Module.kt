package z3roco01.meowclient.module

import z3roco01.meowclient.MeowClient
import z3roco01.meowclient.config.MeowClientConfig
import java.io.File

open class Module(val name: String) {
    private var enabled = false

    fun getTransKey(): String {
        return MeowClient.modId + ".module." + name
    }

    fun toggle() {
        enabled = !enabled
        onToggle()

        saveOnChange()
    }

    fun setEnabled(enabled: Boolean) {
        setEnabledLoading(enabled, false)
    }

    fun setEnabledLoading(enabled: Boolean, loading: Boolean) {
        this.enabled = enabled
        if(enabled)
            onEnable()
        else
            onDisable()

        if(!loading)
            saveOnChange()
    }

    fun isEnabled(): Boolean {
        return enabled
    }

    fun saveOnChange() {
        MeowClient.config.putProperty(name, enabled)
        MeowClient.config.save(File(MeowClientConfig.MODULE_CONFIG_PATH))
    }

    protected fun onEnable() {

    }

    protected fun onDisable() {

    }

    protected fun onToggle() {

    }
}

