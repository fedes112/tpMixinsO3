package ar.edu.unq.o3.mixinsLocura.Habitacion

import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo
import ar.edu.unq.o3.mixinsLocura.investigador.Investigador

import scala.collection.mutable.ArrayBuffer

class Habitacion() {
  def cantidadDeMonstruos(): Double = {
    return this.monstruosEnHabitacion.size
  }

  def objetivoBestia(): Investigador = {
   return this.investigadoresEnHabitacion.minBy(_.vidaActual())
    //sortWith(_.vidaActual() < _.vidaActual())(0)
  }


  def atacarMonstruo(ataqueDelInvestigador: Double) = {
    this.monstruosEnHabitacion(0).recibirDanio(ataqueDelInvestigador)
  }

  def agregarInvestigador(investigador: Investigador) = {
    this.investigadoresEnHabitacion += investigador
  }


  val monstruosEnHabitacion  : ArrayBuffer[Monstruo] = ArrayBuffer[Monstruo]()
  val investigadoresEnHabitacion : ArrayBuffer[Investigador] = ArrayBuffer[Investigador]()

  def agregarMonstruo(monstruo: Monstruo) = {
     this.monstruosEnHabitacion += monstruo
     this.investigadoresEnHabitacion.foreach(investigador => investigador.perderCordura(5))
  }


}
