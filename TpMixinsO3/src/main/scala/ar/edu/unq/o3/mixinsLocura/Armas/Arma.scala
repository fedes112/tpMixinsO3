package ar.edu.unq.o3.mixinsLocura.Armas

import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje}



trait Arma {

  def atacar(atacante: Investigador, defensor: Personaje): Unit = {
    atacante.estadoDeLocuraObjetivo().recibirDanio(this.danioDeArma(atacante, defensor))
  }

  def danioDeArma(atacante: Investigador, defensor: Personaje) : Double

}

class ArmaDeFuego extends  Arma {

  override def danioDeArma(atacante : Investigador, defensor : Personaje): Double = {
    5.0
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

  def daniototal(susceptibilidadeALosHechizosDefensor: Personaje):Double = {
    this.danioBase() * susceptibilidadeALosHechizosDefensor.calcularDanioMagico()
  }

  override def danioDeArma(atacante: Investigador, defensor: Personaje): Double = {
      daniototal(defensor)
  }
}
