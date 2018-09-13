package ar.edu.unq.o3.mixinsLocura.investigador

import scala.annotation.meta.{getter, setter}

class Investigador(vidaMax: Double, corduraMax: Double) {

  var _saludActual : Double = vidaMax
  val _saludMaxima : Double = vidaMax
  val _corduraMaxima : Double = corduraMax
  var _corduraActual : Double = corduraMax
  var _estadoDeLocura: Boolean = false


  def vidaActual(): Double = {
    this._saludActual
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
