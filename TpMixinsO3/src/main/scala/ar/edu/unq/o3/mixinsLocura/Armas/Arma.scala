package ar.edu.unq.o3.mixinsLocura.Armas

import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje}



trait  Arma {

  def atacar(atacante: Investigador, defensor: Personaje): Unit = {
    var objetivo = atacante.estadoDeLocuraObjetivo()
    var danio = this.danioDeArma(atacante, defensor)
    objetivo.recibirDanio(danio)
  }

  def danioDeArma(atacante: Investigador, defensor: Personaje) : Double = { //rompe si lo hago abstracto
    0
  }

}

class ArmaDeFuego(danioBase: Double) extends  Arma {

  var _danioBase = danioBase

  override def danioDeArma(atacante : Investigador, defensor : Personaje): Double = {
    this._danioBase
  }
}

class ArmaDeEsfuerzoFisico extends Arma {

  override def danioDeArma(atacante : Investigador, defensor : Personaje): Double = {
    atacante.vidaActual()*1.5
  }
}

class Hechizo(danioBaseHechizo : Double) extends Arma{

  val _danioBase : Double = danioBaseHechizo

  def danioBase(): Double = {
    this._danioBase
  }

  override def danioDeArma(atacante: Investigador, defensor: Personaje): Double = {
    this.danioBase() * defensor.calcularDanioMagico()
  }
}

trait DanioReducido extends Arma {

  override def danioDeArma(atacante: Investigador, defensor: Personaje): Double =  {
    super.danioDeArma(atacante, defensor) - 1.0
  }
}

trait InfligeDanioPropio extends Arma{

  override def atacar(atacante: Investigador, defensor: Personaje): Unit = {
    super.atacar(atacante, defensor)
    atacante.recibirDanio(1)
  }
}

trait DanioEnArea extends Arma{


  def danioEnAreaParaOtrosInvestigadores(atacante : Investigador, defensor :  Personaje) : Unit = {
    atacante.habitacion().investigadores().foreach(personaje => personaje.recibirDanio(this.danioDeArma(atacante,defensor) * 0.1))
  }

  override def atacar(atacante: Investigador, defensor: Personaje): Unit = {
    super.atacar(atacante, defensor)
    this.danioEnAreaParaOtrosInvestigadores(atacante,defensor)
  }
}
