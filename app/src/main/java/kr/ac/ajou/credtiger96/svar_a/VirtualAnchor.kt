package kr.ac.ajou.credtiger96.svar_a

import java.time.Instant
import java.time.ZoneId
import java.util.*

class VirtualAnchor (
    var position : Vector3,
    var distance : Float,
    var targetId : Int,
    var ts : Long = Instant.now().epochSecond,
    var uuid : UUID = UUID.randomUUID()
){

    override fun toString(): String {
        val dt = Instant.ofEpochSecond(ts).atZone(ZoneId.systemDefault()).toLocalDateTime()
        var str = "virtual anchor $dt\nposition : $position\n"
        return str
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VirtualAnchor

        if (uuid != other.uuid) return false

        return true
    }

}