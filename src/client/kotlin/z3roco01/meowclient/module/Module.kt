package z3roco01.meowclient.module

import z3roco01.meowclient.MeowClient

open class Module(val name: String) {
    private var enabled = false

    fun getTransKey(): String {
        return MeowClient.modId + ".module." + name
    }

    fun toggle() {
        enabled = !enabled
        onToggle()
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
        if(enabled)
            onEnable()
        else
            onDisable()
    }

    fun isEnabled(): Boolean {
        return enabled
    }

    protected fun onEnable() {

    }

    protected fun onDisable() {

    }

    protected fun onToggle() {

    }
}

