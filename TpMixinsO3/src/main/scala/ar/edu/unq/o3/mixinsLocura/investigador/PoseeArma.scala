package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Armas.{Arma, Punios}
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween

trait PoseeArma extends Arma{

  var _armaEquipada : Arma = new Punios()

  def armaEquipada() : Arma = {
  this._armaEquipada
}

}

