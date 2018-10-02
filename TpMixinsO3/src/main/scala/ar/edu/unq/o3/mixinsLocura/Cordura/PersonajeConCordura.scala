package ar.edu.unq.o3.mixinsLocura.Cordura

import ar.edu.unq.o3.mixinsLocura.investigador.Personaje

trait PersonajeConCordura {

  var _corduraMaxima: Double
  var _corduraActual :Double
  var _estadoDeLocura: Boolean = false

  def recuperarCorduraAlMaximo() = {
    this._corduraActual = corduraMaxima()
  }

  def perderCorduraMaxima() = {
    _corduraActual = 0
  }

  def objetivoSegunEstadoDeLocuraObjetivo() : Personaje

  def corduraMaxima(): Double = {
    return this._corduraMaxima
  }
  def corduraActual(): Double = {
    this._corduraActual
  }

  def recuperarCordura(cordura: Double) = {
    if(this._corduraMaxima < (this._corduraActual + cordura) ){
      this._corduraActual = this.corduraMaxima()
    }else{
      this._corduraActual += cordura}
  }

  def perderCordura(cordura: Double) = {
    this._corduraActual -= cordura
    if( this.corduraActual <= 0) {
      this._estadoDeLocura = true
      this._corduraActual = 0
    }
  }

  def corduraPerdida(): Double ={ _corduraMaxima - corduraActual()}

  def estadoDeLocura(): Boolean = _estadoDeLocura
}
