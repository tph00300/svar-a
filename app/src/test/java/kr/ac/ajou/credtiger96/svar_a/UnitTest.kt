package kr.ac.ajou.credtiger96.svar_a

import org.junit.Test

import de.siegmar.fastcsv.reader.CsvReader
import kr.ac.ajou.credtiger96.svar_a.utils.Csv2VirtualAnchorHelper.Companion.convert
import kr.ac.ajou.credtiger96.svar_a.utils.FTPHelper
import java.io.StringReader
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random


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

        val errorList = mutableListOf<Float>()
        for (i in 1..100)
        {
            val est = LocalizationEngine.processRandom(vaList, k)
            errorList.add(calcuateError(est, Vector3(), false))
        }

        var outputString = "["
        errorList.forEach{
            outputString += " $it,"
        }
        outputString += "]"

        return outputString
    }

    @Test
    fun inTermsOfAngle() {
        val rawReply: String =
            FTPHelper.getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/TheAngleOfVA/data/env1-2-180.csv")
        val csvReader = CsvReader()

        csvReader.setContainsHeader(true)

        val reply = csvReader.read(StringReader(rawReply))

        val vaList = convert(reply)

        println(randomExperiment(vaList, 30))

    }

    @Test
    fun inTermsOfK(){

        val rawReply: String =
            FTPHelper.getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/TheNumberOfVa2/data/env1_2.csv")
        val csvReader = CsvReader()

        csvReader.setContainsHeader(true)

        val reply = csvReader.read(StringReader(rawReply))

        val vaList = convert(reply)


        arrayOf(5, 10, 25, 50, 75, 100).forEach {k ->
            val errorList = mutableListOf<Float>()
            for (i in 0..200) {
                val selectedVas = vaList.shuffled(Random(i)).subList(0, 100)
                errorList.add(calcuateError(LocalizationEngine.processScheme(selectedVas, k), Vector3(), false))
            }

            var outputString = "["
            errorList.forEach{
                outputString += " $it,"
            }
            outputString += "],"

            println(outputString)
        }
    }


    @Test
    fun inTermsOfK_random(){

        val rawReply: String =
            FTPHelper.getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/TheNumberOfVa2/data/env1_2.csv")
        val csvReader = CsvReader()

        csvReader.setContainsHeader(true)

        val reply = csvReader.read(StringReader(rawReply))

        val vaList = convert(reply)


        arrayOf(5, 10, 25, 50, 75, 100).forEach {k ->
            val errorList = mutableListOf<Float>()
            for (i in 0..200) {
                val selectedVas = vaList.shuffled(Random(i)).subList(0, 100)
                errorList.add(calcuateError(LocalizationEngine.processRandom(selectedVas, k), Vector3(), false))
            }

            var outputString = "["
            errorList.forEach{
                outputString += " $it,"
            }
            outputString += "],"

            println(outputString)
        }
    }

    @Test
    fun theNumberOfVa() {
        val rawReply: String =
            FTPHelper.getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/TheNumberOfVa2/data/env2_1.csv")
        val csvReader = CsvReader()

        csvReader.setContainsHeader(true)

        val reply = csvReader.read(StringReader(rawReply))

        val vaList = convert(reply)

        arrayOf(5, 10, 25, 50, 75, 100).forEach {number ->
            println(randomExperiment(vaList, number) + ",")
        }

    }


}
