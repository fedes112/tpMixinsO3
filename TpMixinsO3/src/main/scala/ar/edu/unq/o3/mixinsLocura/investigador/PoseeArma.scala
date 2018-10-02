package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Armas.{Arma, Punios}
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween

trait PoseeArma {

  var _armaEquipada : Arma = null

  def armaEquipada() : Arma = {
  this._armaEquipada
  }

  def equiparArma(armaDeFuego: Arma, personajeAEquiparArma: Personaje) = {
    this._armaEquipada = armaDeFuego
  }

  def danioARealizar(objetivo: Personaje, atacante: Personaje): Double ={
    armaEquipada().danioDeArma(atacante, objetivo)
  }

}

