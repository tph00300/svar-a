package kr.ac.ajou.credtiger96.svar_a.utils

import com.jcraft.jsch.ChannelSftp
import com.jcraft.jsch.JSch
import java.lang.Exception


class FTPHelper {
    companion object {
        fun getFileFromServer(path : String) : String{
            var str = ""
            try {
                val jsch = JSch()
                val session = jsch.getSession("hongbeom", "202.30.29.168", 22)
                session.setPassword("170905")
                val config = java.util.Properties()
                config["StrictHostKeyChecking"] = "no"
                session.setConfig(config)

                session.connect()

                val channel = session.openChannel("sftp")
                channel.connect()
                val channelSftp = channel as ChannelSftp

                val inputStreamReader = channelSftp.get(path).reader()
                inputStreamReader.forEachLine {
                    str += it
                    str += "\n"
                }

            }catch (e : Exception){
                println(e.printStackTrace())
            }

            return str
        }
    }
}