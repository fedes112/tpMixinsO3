package ar.edu.unq.o3.mixinsLocura.Habitacion

import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween
import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo
import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje}

import scala.collection.mutable.ArrayBuffer

class Habitacion() {

  def personajeAleatorio() : Personaje = {
   personajesEnHabitacion(randomIntBetween(0, (personajesEnHabitacion.size)))
  }

  def monstruoAAtacarPorInvestigador() : Monstruo = {
    this.monstruos().maxBy(_.vidaActual())
  }


  val personajesEnHabitacion : ArrayBuffer[Personaje] = ArrayBuffer[Personaje]()
 // val monstruosEnHabitacion  : ArrayBuffer[Monstruo] = ArrayBuffer[Monstruo]()
  //val investigadoresEnHabitacion : ArrayBuffer[Investigador] = ArrayBuffer[Investigador]()


  def cantidadDeMonstruos(): Double = {
    return this.monstruos().size
  }

  def objetivoBestia(): Investigador = {
    return this.investigadores().minBy(_.vidaActual())
    //sortWith(_.vidaActual() < _.vidaActual())(0)
  }

  def investigadores(): ArrayBuffer[Investigador] = {
    personajesEnHabitacion collect { case x:Investigador => x }
  }

  def monstruos(): ArrayBuffer[Monstruo] = {
    personajesEnHabitacion collect { case x:Monstruo=> x }
  }

  def agregarInvestigador(investigador: Investigador) = {
    this.personajesEnHabitacion += investigador
    investigador.perderCordura(cantidadDeMonstruos())
  }

  def agregarMonstruo(monstruo: Monstruo) = {
     this.personajesEnHabitacion += monstruo
     this.investigadores().foreach(investigador => investigador.perderCordura(1))
  }


}
