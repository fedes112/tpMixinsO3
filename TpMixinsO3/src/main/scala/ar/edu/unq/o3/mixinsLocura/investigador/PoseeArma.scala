package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Armas.{Arma, Punios}

trait PoseeArma  {

  var _armaEquipada : Arma = null

  def armaEquipada() : Arma = {
    this._armaEquipada
  }

  def equiparArma(arma: Arma, personajeAEquiparArma: Personaje) = {
    this._armaEquipada = arma
  }

  def danioARealizar(objetivo: Personaje, atacante: Personaje): Double ={
    armaEquipada().danioDeArma(atacante, objetivo)
  }
}

