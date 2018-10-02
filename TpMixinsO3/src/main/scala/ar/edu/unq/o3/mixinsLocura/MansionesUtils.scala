package ar.edu.unq.o3.mixinsLocura

import scala.util.Random




  object MansionesUtils {
    /** retorna un entero aleatorio entre el rango dado [low-high] */
    //def randomIntBetween(low: Int, high: Int) = new Random().nextInt(high - low) + low
    def randomIntBetween(low: Int, high: Int) = 1//new Random().nextInt(high - low) + low
    /** redondea un numero decimal a entero */
    def roundInt(d: Double) = Math.round(d).asInstanceOf[Int]

    /** retorna un elemento aleatorio de la lista data */
    def randomElement[T](lista: List[T]) : T = lista(randomIntBetween(0, lista.length))

}
