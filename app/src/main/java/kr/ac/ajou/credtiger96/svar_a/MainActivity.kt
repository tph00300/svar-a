package kr.ac.ajou.credtiger96.svar_a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import de.siegmar.fastcsv.reader.CsvReader
import io.reactivex.Observable
import kr.ac.ajou.credtiger96.svar_a.utils.Csv2VirtualAnchorHelper
import kr.ac.ajou.credtiger96.svar_a.utils.FTPHelper
import java.io.StringReader
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Observable.timer(2, TimeUnit.SECONDS)
            .subscribe{

                timeTest()
            }.let{}

    }

    fun timeTest(){

        val rawReply: String =
            FTPHelper.getFileFromServer("/home/hongbeom/Workspace/sensorVisualizer/TheNumberOfVa2/data/env1_2.csv")
        val csvReader = CsvReader()

        csvReader.setContainsHeader(true)

        val reply = csvReader.read(StringReader(rawReply))

        val vaList = Csv2VirtualAnchorHelper.convert(reply)

        var outputString = "["

        var elapsedTimeListForRandom = arrayListOf<Long>()

        for (i in 25..200 step 5){
            var selectedVaList = vaList.shuffled(Random(i)).subList(0,i)
            val startTimeStamp = Instant.now().toEpochMilli()
            LocalizationEngine.processRandom(selectedVaList, 25)
            val endTimeStamp = Instant.now().toEpochMilli()

            val elapsedTime = endTimeStamp - startTimeStamp

            elapsedTimeListForRandom.add(elapsedTime)
        }

        elapsedTimeListForRandom.forEach{
            outputString += "$it,"
        }
        outputString += "],["

        val elapsedTimeListForRaw = arrayListOf<Long>()

        for (i in 5..201 step 5){
            var selectedVaList = vaList.shuffled(Random(i)).subList(0,i)

            val startTimeStamp = Instant.now().toEpochMilli()
            LocalizationEngine.processRaw(selectedVaList)
            val endTimeStamp = Instant.now().toEpochMilli()

            val elapsedTime = endTimeStamp - startTimeStamp

            elapsedTimeListForRaw.add(elapsedTime)
        }


        elapsedTimeListForRaw.forEach{
            outputString += "$it,"
        }

        outputString += "],["


        val elapsedTimeListForScheme = arrayListOf<Long>()

        for (i in 25..200 step 5){

            var selectedVaList = vaList.shuffled(Random(i)).subList(0,i)

            val startTimeStamp = Instant.now().toEpochMilli()
            LocalizationEngine.processScheme(selectedVaList, 25)
            val endTimeStamp = Instant.now().toEpochMilli()

            val elapsedTime = endTimeStamp - startTimeStamp

            elapsedTimeListForScheme.add(elapsedTime)
        }

        elapsedTimeListForScheme.forEach{
            outputString += "$it,"
        }

        outputString += "],["


        elapsedTimeListForRandom = arrayListOf()

        for (i in 25..200 step 5){
            var selectedVaList = vaList.shuffled(Random(i)).subList(0,i)
            val startTimeStamp = Instant.now().toEpochMilli()
            LocalizationEngine.processRandom(selectedVaList, 25)
            val endTimeStamp = Instant.now().toEpochMilli()

            val elapsedTime = endTimeStamp - startTimeStamp

            elapsedTimeListForRandom.add(elapsedTime)
        }

        elapsedTimeListForRandom.forEach{
            outputString += "$it,"
        }
        outputString += "]"




        Log.d("experiment", outputString)
    }



}
