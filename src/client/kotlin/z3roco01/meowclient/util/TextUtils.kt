package z3roco01.meowclient.util

import net.minecraft.text.MutableText
import net.minecraft.text.Text
import z3roco01.meowclient.MeowClient

class TextUtils {
    companion object {
        fun translate(key: String): MutableText {
            return Text.translatable(MeowClient.modId + key)
        }
    }
}