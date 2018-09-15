package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.{randomIntBetween, randomElement, roundInt}

import scala.annotation.meta.{getter, setter}


class Investigador(vidaMax: Double, corduraMax: Double) extends Personaje(vidaMax) {


  var _habitacionActual : Habitacion =  null
  val _corduraMaxima : Double = corduraMax
  var _corduraActual : Double = corduraMax
  var _estadoDeLocura: Boolean = false


  @throws(classOf[NullPointerException])
  def atacar() = {
    if( ! estadoDeLocura()) {
      habitacion().monstruoAAtacarPorInvestigador().recibirDanio(randomIntBetween(1, _saludMaxima.toInt))
    }
    else{
      habitacion().personajeAleatorio().recibirDanio(randomIntBetween(1, _saludMaxima.toInt))
    }
  }

  def habitacion() :Habitacion = {
    this._habitacionActual
  }

  def entrarHabitacion(habitacion: Habitacion) = {
    habitacion.agregarInvestigador(this)
    this._habitacionActual = habitacion
  }




  def corduraActual(): Double = {
    this._corduraActual
  }

  def perderCordura(cordura: Double) = {
    this._corduraActual -= cordura
    if( this.corduraActual <= 0) {
      this._estadoDeLocura = true
      this._corduraActual = 0
    }
  }

  def estadoDeLocura(): Boolean = _estadoDeLocura

}

class Personaje(vidaMax : Double) {
  val _saludMaxima: Double = vidaMax

  var _saludActual: Double = vidaMax


  def vidaActual(): Double = {
    this._saludActual
  }

  def recibirDanio(danio: Double) = {
    this._saludActual -= danio
    if (this.vidaActual() <= 0) {
      this._saludActual = 0
    }
  }
}
