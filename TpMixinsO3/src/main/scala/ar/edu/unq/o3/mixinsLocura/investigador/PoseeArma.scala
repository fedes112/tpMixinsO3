package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Armas.{Arma, Punios}
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween

trait PoseeArma extends Arma{

  var armaBase = new Punios()
  var _armaEquipada : Arma = null


  def armaEquipada() : Arma = {
  this._armaEquipada
  }

  def equiparArma(armaDeFuego: Arma, personajeAEquiparArma: Personaje) = {
    this._armaEquipada = armaDeFuego
    armaDeFuego.personajeQueMeEstaUsando(personajeAEquiparArma)
  }

  def danioARealizar(objetivo: Personaje): Double ={
    armaEquipada().danioDeArma(this.personajeQueTieneEquipadaEstaArma, objetivo)
  }

}

