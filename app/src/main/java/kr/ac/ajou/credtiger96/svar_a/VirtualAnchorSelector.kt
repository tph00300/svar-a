package kr.ac.ajou.credtiger96.svar_a

import kotlin.math.floor
import kotlin.math.sqrt

class VirtualAnchorSelector {
    companion object {
        fun select(_vas: List<VirtualAnchor>, k: Int): List<VirtualAnchor> {
            val vas = _vas.toMutableList()

            val size = vas.size
            if (size <= k) return vas

            val xCoords = FloatArray(size)
            val yCoords = FloatArray(size)

            val _k = floor(sqrt(k.toFloat()))

            vas.forEachIndexed { index, virtualAnchor ->
                xCoords[index] = virtualAnchor.position.x
                yCoords[index] = virtualAnchor.position.y
            }

            // to prevent VAs
            val xMin = xCoords.min()!! - 0.00001
            val yMin = xCoords.min()!! - 0.00001
            val xMax = xCoords.max()!! + 0.00001
            val yMax = xCoords.max()!! + 0.00001

            val xGridLen = (xMax - xMin) / _k
            val yGridLen = (yMax - yMin) / _k

            val grid = Array(k) { mutableListOf<VirtualAnchor>() }

            vas.map {
                val _x = it.position.x - xMin
                val _y = it.position.y - yMin

                val i = floor(_x / xGridLen)
                val j = floor(_y / yGridLen)

                grid[((i * _k) + j).toInt()].add(it)
            }

            val selectedVAs = mutableListOf<VirtualAnchor>()

            grid.map { vasInGrid ->
                var selectedVA: VirtualAnchor? = null
                var minR = Float.MAX_VALUE

                vasInGrid.map { va ->
                    if (va.distance < minR) {
                        selectedVA = va
                        minR = va.distance
                    }
                }

                if (selectedVA != null) {
                    vas.remove(selectedVA!!)
                    selectedVAs.add(selectedVA!!)
                }
            }

            if (selectedVAs.size < k) {
                selectedVAs.addAll(select(vas, k - selectedVAs.size))
            }
            return selectedVAs
        }
    }
}