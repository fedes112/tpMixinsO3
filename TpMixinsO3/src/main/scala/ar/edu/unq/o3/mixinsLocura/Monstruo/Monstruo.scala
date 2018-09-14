package ar.edu.unq.o3.mixinsLocura.Monstruo

import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.investigador.Investigador

abstract class Monstruo(vidaMonstruo: Double) {

  var habitacionActual: Habitacion = null

  def entrarHabitacion(habitacion: Habitacion) = {
    habitacion.agregarMonstruo(this)
    this.habitacionActual = habitacion
  }

  def atacar() : Unit

  def recibirDanio(danio: Double) = {
    this._vidaActual -= danio
    if( this.vidaActual() <= 0) {
      this._vidaActual = 0
    }
  }


  var _vidaActual =  vidaMonstruo

 def vidaActual() : Double = {
    this._vidaActual
 }

}

class Bestia(vidaMaximaBestia: Double) extends Monstruo(vidaMaximaBestia){


  override def atacar(): Unit = {
    this.habitacionActual.objetivoBestia().recibirDanio(this.habitacionActual.cantidadDeMonstruos())
  }

}
