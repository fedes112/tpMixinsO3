package ar.edu.unq.o3.mixinsLocura.Habitacion

import ar.edu.unq.o3.mixinsLocura.MansionesUtils.{randomElement, randomIntBetween, roundInt}
import ar.edu.unq.o3.mixinsLocura.Monstruo.{Arcano, Monstruo}
import ar.edu.unq.o3.mixinsLocura.Objetos.{Nada, Objeto}
import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje}

import scala.collection.mutable.ArrayBuffer

class Habitacion() {


  def agregarObjeto(objetoNuevo: Objeto) = {
    this.objetosEnHabitacion += objetoNuevo
  }

  def conseguirUnObjetoRandom() = {
    randomElement(objetosEnHabitacion.toList)
  }


  def agregarPersonaje(personajeQueEntra: Personaje) = {
    this.personajesEnHabitacion += personajeQueEntra
  }

  def personajeParaCurar(): Investigador = {
     this.investigadores().maxBy(_.diferenciaDeVida())
  }

  def personajeAleatorio() : Personaje = {
   personajesEnHabitacion(randomIntBetween(0, (personajesEnHabitacion.size) - 1 ))
  }

  def monstruoAAtacarPorInvestigador() : Monstruo = {
    this.monstruos().maxBy(_.vidaActual())
  }

  def investigadorAAtacarPorHumanoide(): Investigador = {
    this.investigadores().maxBy(_.vidaActual())
  }

//  def personajeAAtacarSiNoEstoyEnEstadoDeLocura( listaDePersonaje : ArrayBuffer[Personaje]) : Personaje = {
//    listaDePersonaje.maxBy(_.vidaActual())
//  }


  val personajesEnHabitacion : ArrayBuffer[Personaje] = ArrayBuffer[Personaje]()
  val objetosEnHabitacion: ArrayBuffer[Objeto] = ArrayBuffer[Objeto]()

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

  def efectoAgregarInvestigador(investigador: Investigador) = {
    this.monstruos().foreach(monstruo => monstruo.causarHorror(investigador))
  }

  def efectoAgregarMonstruo(monstruo: Monstruo) = {
      this.investigadores().foreach(investigador => monstruo.causarHorror(investigador))
  }

  def corduraPerdidaGlobal():Double = {
    var cordura = 0.0
    this.investigadores().foreach(investigador => cordura += investigador.corduraPerdida())
    return cordura
  }

  def objetivoArcano(): Investigador = {
    return this.investigadores().minBy(_.corduraActual())
  }

}
