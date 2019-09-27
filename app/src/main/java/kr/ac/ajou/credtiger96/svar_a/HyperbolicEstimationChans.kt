package kr.ac.ajou.credtiger96.svar_a
import org.ejml.simple.SimpleMatrix

class HyperbolicEstimationChans {
    companion object {

        private fun show(matrix: SimpleMatrix) {
            println(matrix.toString())
        }

        private fun duplicateRow(D: SimpleMatrix, num: Int): SimpleMatrix {
            val cols = D.numCols()
            val res = SimpleMatrix(num, cols)
            for (i in 0 until num) {
                res.insertIntoThis(i, 0, D)
            }
            return res
        }

        private fun squareEachElement(matrix: SimpleMatrix): SimpleMatrix {
            val res = SimpleMatrix(matrix)
            val rows = matrix.numRows()
            val cols = matrix.numCols()

            for (i in 0 until rows)
                for (j in 0 until cols) {
                    res.set(i, j, matrix.get(i, j) * matrix.get(i, j))
                }
            return res
        }

        private fun convertToAtoTDoA(ToAs: SimpleMatrix): SimpleMatrix {
            val rows = ToAs.numRows()
            val cols = ToAs.numCols()
            val firstToA = ToAs.get(0, cols - 1)

            val TDoAs = SimpleMatrix(ToAs)

            for (i in 0 until rows) {
                TDoAs.set(i, cols - 1, TDoAs.get(i, cols - 1) - firstToA)
            }

            return TDoAs
        }

        fun performForMany(ToAs: SimpleMatrix): SimpleMatrix {
            val rows = ToAs.numRows()
            val cols = ToAs.numCols()
            val dim = cols - 1
            //show(ToAs);
            //
            val TDoAs = convertToAtoTDoA(ToAs)

            val M = TDoAs.cols(0, dim)
            val Ga = TDoAs.rows(1, rows).minus(duplicateRow(TDoAs.rows(0, 1), rows - 1))
            //show(Ga);
            val Q = SimpleMatrix.identity(rows - 1).scale(0.5).plus(0.5)

            //show(Q);
            var E = Ga.transpose().scale(-1.0).mult(Q.invert())
            //show(E);
            var Fi = E.mult(Ga.scale(-1.0)).invert()
            //show(Fi);
            val R = Ga.cols(cols - 1, cols)
            //show(R);
            //SimpleMatrix RSquared
            val R_squared = squareEachElement(R)
            //show(R_squared);
            val K = SimpleMatrix(rows, 1)

            for (i in 0 until rows) {
                K.set(i, 0, M.rows(i, i + 1).mult(M.rows(i, i + 1).transpose()).get(0, 0))
            }
            //show(K);
            val h = R_squared.minus(K.rows(1, rows)).plus(K.get(0, 0)).scale(0.5)
            //show(r);
            //show(Fi.mult(E).mult(h))
            val R0 = Fi.mult(E).mult(h).get(3, 0)

            //show(SimpleMatrix.identity(rows - 1));
            //show(R.plus(R0));
            //show(SimpleMatrix.identity(rows - 1).mult(R.plus(R0)));
            //show(R.plus(R0).mult(SimpleMatrix.identity(rows - 1)));

            val B = SimpleMatrix.identity(rows - 1)

            for (i in 0 until rows - 1) {
                B.insertIntoThis(i, 0, B.rows(i, i + 1).scale(R.plus(R0).get(i, 0)))
            }
            //show(B);

            val Y = B.mult(Q).mult(B)
            //show(Y);

            val netc = 1
            E = Ga.transpose().scale(-1.0).mult(Y.scale((netc * netc).toDouble()).invert())
            Fi = E.mult(Ga.scale(-1.0)).invert()

            //second_est = (Fi * E * h).A.squeeze() ;
            val second_est = Fi.mult(E).mult(h)
            // show(second_est);

            return second_est.rows(0, cols - 1)
        }
    }
}