package z3roco01.meowclient.config

import z3roco01.meowclient.MeowClient
import z3roco01.meowclient.util.LoggerUtil
import java.io.File
import java.util.Properties

class MeowClientConfig {
    private val properties = HashMap<String, Boolean>()

    init {
    }

    fun putProperty(key: String, value: Boolean) {
        properties.put(key, value)
    }

    fun getPropertiesIter(): MutableIterator<MutableMap.MutableEntry<String, Boolean>> {
        return properties.iterator()
    }

    fun getPropertyValue(key: String): Boolean? {
        return properties.get(key)
    }

    fun save(file: File) {
        val props = Properties()

        for(prop in getPropertiesIter())
            props.put(prop.key, bool2Str(prop.value))

        LoggerUtil.info("saving")
        for(prop in props)
            LoggerUtil.info("${prop.key} : ${prop.value}")

        props.store(file.outputStream(), null)
    }

    fun load(file: File) {

        if(!file.exists())
            create(file, this)

        val props = Properties()
        props.load(file.reader())

        for(prop in props)
            this.putProperty(prop.key.toString(), str2Bool(prop.value.toString()))

        for(prop in this.getPropertiesIter())
            LoggerUtil.info("${prop.key} : ${prop.value}")
    }

    companion object {
        val MODULE_CONFIG_PATH = "./config/meowclient_modules.properties"

        fun load(file: File): MeowClientConfig {
            val config = MeowClientConfig()

            if(!file.exists())
                create(file, config)

            val props = Properties()
            props.load(file.reader())

            for(prop in props)
                config.putProperty(prop.key.toString(), str2Bool(prop.value.toString()))

            for(prop in config.getPropertiesIter())
                LoggerUtil.info("${prop.key} : ${prop.value}")

            return config
        }

        private fun create(file: File, config: MeowClientConfig) {
            file.createNewFile()

            val initProps = Properties()
            for(module in MeowClient.modules.modules) {
                initProps.setProperty(module.name, bool2Str(module.isEnabled()))
                //config.addOption(module.name, module.isEnabled())
            }

            initProps.store(file.outputStream(), null)
        }

        private fun bool2Str(bool: Boolean): String {
            if(bool)
                return "true"
            else
                return "false"
        }

        private fun str2Bool(str: String): Boolean {
            val lowerStr = str.lowercase()
            if(lowerStr == "true")
                return true
            else if(lowerStr == "false")
                return false
            throw InvalidPropteryValue("invalid value: $str for a property")
        }
    }
}