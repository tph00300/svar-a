package kr.ac.ajou.credtiger96.svar_a

class Vector3 (
    var x : Float = 0f,
    var y : Float = 0f,
    var z : Float = 0f
) {
    override fun toString(): String {
        val var1 = this.x
        val var2 = this.y
        val var3 = this.z
        return StringBuilder(57).append("[x=").append(var1).append(", y=").append(var2)
            .append(", z=").append(var3).append("]").toString()
    }
}