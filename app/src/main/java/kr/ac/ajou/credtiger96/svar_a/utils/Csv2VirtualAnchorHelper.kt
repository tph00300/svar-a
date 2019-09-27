package kr.ac.ajou.credtiger96.svar_a.utils

import de.siegmar.fastcsv.reader.CsvContainer
import kr.ac.ajou.credtiger96.svar_a.Vector3
import kr.ac.ajou.credtiger96.svar_a.VirtualAnchor

class Csv2VirtualAnchorHelper {
    companion object {
        fun convert(reply: CsvContainer): MutableList<VirtualAnchor> {
            val VirtualAnchorList = mutableListOf<VirtualAnchor>()

            reply.rows.forEach {
                val ts = it.getField(0)
                val x = it.getField(1)
                val y = it.getField(2)
                val z = it.getField(3)
                val distanceList = it.getField(4)

                distanceList.split(";")
                    .filter { it.isNotBlank() }
                    .map {
                        it.split(":").apply {
                            val id = get(0)
                            val distance = get(1)
                            VirtualAnchorList.add(
                                VirtualAnchor(
                                    Vector3(
                                        x.toFloat(),
                                        y.toFloat(),
                                        z.toFloat()
                                    ),
                                    distance.toFloat(),
                                    id.toInt(),
                                    ts.toLong()
                                )
                            )
                        }
                    }
            }
            return VirtualAnchorList
        }
    }
}