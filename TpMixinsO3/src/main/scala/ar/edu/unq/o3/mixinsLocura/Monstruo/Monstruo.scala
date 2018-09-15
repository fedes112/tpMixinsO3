package ar.edu.unq.o3.mixinsLocura.Monstruo

import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.investigador.{ Personaje}

abstract class Monstruo(vidaMonstruo: Double) extends Personaje(vidaMonstruo) {

  var habitacionActual: Habitacion = null

  def entrarHabitacion(habitacion: Habitacion) = {
    habitacion.agregarMonstruo(this)
    this.habitacionActual = habitacion
  }

  def atacar() : Unit



  var _vidaActual =  vidaMonstruo



}

class Bestia(vidaMaximaBestia: Double) extends Monstruo(vidaMaximaBestia){


  override def atacar(): Unit = {
    this.habitacionActual.objetivoBestia().recibirDanio(this.habitacionActual.cantidadDeMonstruos())
  }

}
