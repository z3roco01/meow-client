package z3roco01.meowclient.util

import org.slf4j.LoggerFactory
import z3roco01.meowclient.MeowClient

class LoggerUtil {
    companion object {
        private val logger = LoggerFactory.getLogger(MeowClient.modId)

        fun info(msg: String) {
            logger.info("[" + MeowClient.modId + "/INFO] " + msg)
        }

        fun warn(msg: String) {
            logger.warn("[" + MeowClient.modId + "/WARN] " + msg)
        }

        fun debug(msg: String) {
            logger.debug("[" + MeowClient.modId + "/DEBUG] " + msg)
        }

        fun error(msg: String) {
            logger.error("[" + MeowClient.modId + "/ERROR] " + msg)
        }

    }
}