package kr.ac.ajou.credtiger96.svar_a

import org.junit.Test

import de.siegmar.fastcsv.reader.CsvReader
import kr.ac.ajou.credtiger96.svar_a.utils.Csv2VirtualAnchorHelper.Companion.convert
import kr.ac.ajou.credtiger96.svar_a.utils.FTPHelper
import org.ejml.simple.SimpleMatrix
import java.io.StringReader
import java.time.Instant
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    fun calcuateError(point : Vector3, ref : Vector3, is3D : Boolean) : Float{
        return if (!is3D){
            sqrt((point.x - ref.x).pow(2) + (point.y - ref.y).pow(2))
        } else {
            //3d
            0f
        }
    }

    fun randomExperiment(vaList : List<VirtualAnchor>, k : Int) : String {

        val selectedVaList = mutableListOf<Float>()
        for (i in 1..50)
        {
            val est = LocalizationEngine.processRandom(vaList, k)
            selectedVaList.add(calcuateError(est, Vector3(), false))
        }

        var outputString = "["
        selectedVaList.forEach{
            outputString += " $it,"
        }
        outputString += "]"

        return outputString
    }

    @Test
    fun addition_isCorrect() {
        val rawReply: String =
            FTPHelper.getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/TheAngleOfVA/data/env1-2-90-b.csv")
        val csvReader = CsvReader()

        csvReader.setContainsHeader(true)

        val reply = csvReader.read(StringReader(rawReply))

        val vaList = convert(reply)

        /*
        println(LocalizationEngine.processRaw(vaList))
        println(LocalizationEngine.processScheme(vaList, 20))
        println(LocalizationEngine.processRandom(vaList, 20))
*/

        println(randomExperiment(vaList, 20))


    }
}
