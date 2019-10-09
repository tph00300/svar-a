package kr.ac.ajou.credtiger96.svar_a

import org.ejml.simple.SimpleMatrix

class LocalizationEngine {
    companion object{
        fun processScheme (vas : List<VirtualAnchor>, k : Int): Vector3 {
            return process(VirtualAnchorSelector.select(vas, k))
        }

        fun processRandom(vas : List<VirtualAnchor>, k : Int): Vector3 {
            return if (vas.size < k) process(vas)
                else process(vas.shuffled().subList(0, k))
        }

        fun processRaw(vas : List<VirtualAnchor>): Vector3 {
            return process(vas)
        }

        private fun process (vas: List<VirtualAnchor>): Vector3 {
            val toaArray = ArrayList<Float>()

            vas.forEach {
                toaArray.addAll(
                    arrayOf(
                        it.position.x,
                        it.position.y,
                        it.position.z,
                        it.distance.toFloat() / 1000
                    )
                )
            }

            val toaMatrix = SimpleMatrix(vas.size, 4,
                true, toaArray.toFloatArray())

            val result = HyperbolicEstimationChans.performForMany(toaMatrix)

            return Vector3(result.get(0,0).toFloat(),
                result.get(1, 0).toFloat(),
                result.get(2,0).toFloat())

        }
    }
}