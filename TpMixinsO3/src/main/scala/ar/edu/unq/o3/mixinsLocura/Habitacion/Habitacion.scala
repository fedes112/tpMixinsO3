package ar.edu.unq.o3.mixinsLocura.Habitacion

import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo
import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje}

import scala.collection.mutable.ArrayBuffer

class Habitacion() {

  val personajesEnHabitacion : ArrayBuffer[Personaje] = ArrayBuffer[Personaje]()
  val monstruosEnHabitacion  : ArrayBuffer[Monstruo] = ArrayBuffer[Monstruo]()
  val investigadoresEnHabitacion : ArrayBuffer[Investigador] = ArrayBuffer[Investigador]()


  def cantidadDeMonstruos(): Double = {
    return this.monstruosEnHabitacion.size
  }

  def objetivoBestia(): Investigador = {
    return this.investigadoresEnHabitacion.minBy(_.vidaActual())
    //sortWith(_.vidaActual() < _.vidaActual())(0)
  }

  def investigadores(): ArrayBuffer[Investigador] = {
    personajesEnHabitacion collect { case x:Investigador => x }
  }

  def monstruos(): ArrayBuffer[Monstruo] = {
    personajesEnHabitacion collect { case x:Monstruo=> x }
  }

  def atacarMonstruo(ataqueDelInvestigador: Double) = {
    this.monstruosEnHabitacion.maxBy(_.vidaActual()).recibirDanio(ataqueDelInvestigador)
  }

  def agregarInvestigador(investigador: Investigador) = {
    this.investigadoresEnHabitacion += investigador
    investigador.perderCordura(cantidadDeMonstruos())
  }

  def agregarMonstruo(monstruo: Monstruo) = {
     this.monstruosEnHabitacion += monstruo
     this.investigadoresEnHabitacion.foreach(investigador => investigador.perderCordura(1))
  }


}
