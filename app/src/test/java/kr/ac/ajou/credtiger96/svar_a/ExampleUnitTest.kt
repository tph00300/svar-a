package kr.ac.ajou.credtiger96.svar_a

import org.junit.Test

import android.R.attr.port
import android.R.attr.host
import com.jcraft.jsch.JSch
import java.lang.Exception
import com.jcraft.jsch.ChannelSftp




/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private fun getFileFromServer(path : String){
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
                println(it)
            }

        }catch (e : Exception){
            println(e.printStackTrace())
        }

    }
    @Test
    fun addition_isCorrect() {
        getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/log.txt")
    }
}
