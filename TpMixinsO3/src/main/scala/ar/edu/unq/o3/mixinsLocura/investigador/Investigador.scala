package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion

import scala.annotation.meta.{getter, setter}

class Investigador(vidaMax: Double, corduraMax: Double) {

  var _saludActual : Double = vidaMax

  var _habitacionActual : Habitacion =  null
  val _saludMaxima : Double = vidaMax
  val _corduraMaxima : Double = corduraMax
  var _corduraActual : Double = corduraMax
  var _estadoDeLocura: Boolean = false
  def vidaActual(): Double = {
    this._saludActual
  }

  @throws(classOf[NullPointerException])
  def atacar() = {
    try {
      habitacion().atacarMonstruo(1.0)
    } catch {
        case e: NullPointerException =>
          throw  new NullPointerException("Debes entrar a una habitacion antes de poder atacar!!");
    }
  }

  def habitacion() :Habitacion = {
    this._habitacionActual
  }

  def entrarHabitacion(habitacion: Habitacion) = {
    habitacion.agregarInvestigador(this)
    this._habitacionActual = habitacion
  }

  def recibirDanio(danio: Double) = {
    this._saludActual -= danio
    if( this.vidaActual() <= 0) {
      this._saludActual = 0
    }

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
