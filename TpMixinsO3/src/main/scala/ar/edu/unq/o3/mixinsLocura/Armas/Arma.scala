package ar.edu.unq.o3.mixinsLocura.Armas

import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween
import ar.edu.unq.o3.mixinsLocura.Objetos.Objeto
import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje, PoseeArma}

trait  Arma extends  Objeto{

  override def descubrir(investigador: Investigador): Unit = {
    investigador.equiparArma(this, investigador)
  }


  def atacarA(personajeAAtacar: Personaje, danio: Double, personajeQueAtaca:Personaje): Unit = {
    personajeAAtacar.recibirDanio(danio)
  }

  def danioDeArma(atacante: Personaje, defensor: Personaje): Double = { //rompe si lo hago abstracto dbeeria ser abstract override
    0
  }
}


class ArmaDeFuego(danioBase: Double) extends  Arma {

  var _danioBase = danioBase

  override def danioDeArma(atacante : Personaje, defensor : Personaje): Double = {
    this._danioBase
  }

}

class ArmaDeEsfuerzoFisico extends Arma {

  override def danioDeArma(atacante : Personaje, defensor : Personaje): Double = {
    atacante.vidaActual()*1.5
  }
}

class Hechizo(danioBaseHechizo : Double) extends Arma{

  val _danioBase : Double = danioBaseHechizo

  def danioBase(): Double = {
    this._danioBase
  }

  override def danioDeArma(atacante: Personaje, defensor: Personaje): Double = {
    this.danioBase() * defensor.calcularDanioMagico()
  }
}

class Punios() extends Arma {

  override def danioDeArma(atacante: Personaje, defensor: Personaje): Double = {
    randomIntBetween(1, atacante.saludMaxima.toInt).toDouble
  }

}

trait DanioReducido extends Arma {

  override def danioDeArma(atacante: Personaje, defensor: Personaje): Double =  {
    super.danioDeArma(atacante, defensor) - 1.0
  }
}

trait InfligeDanioPropio extends Arma{

  override def atacarA(personajeAAtacar: Personaje, danio: Double, personajeQueAtaca:Personaje): Unit ={
    personajeAAtacar.recibirDanio(danio) //partirlo a quien atacar, cuanto daño y locura
    personajeQueAtaca.recibirDanio(1)
  }
}

trait DanioEnArea extends Arma{


  def danioEnAreaParaOtrosInvestigadores(atacante : Personaje, defensor :  Personaje) : Unit = {
    atacante.habitacion().investigadores().foreach(personaje => personaje.recibirDanio(this.danioDeArma(atacante,defensor) * 0.1))
  }

  override def atacarA(personajeAAtacar: Personaje, danio: Double,personajeQueAtaca:Personaje): Unit =  {
    personajeAAtacar.recibirDanio(danio)
    this.danioEnAreaParaOtrosInvestigadores(personajeQueAtaca , personajeAAtacar)//sumar ataque a los para
  }
}

trait NUsos extends Arma{

  var cantidadDeUsos : Double
  def armaEstaGastada() = cantidadDeUsos == 0
  def disminuirUso(): Unit = {
    if(this.cantidadDeUsos - 1 > 0){
      this.cantidadDeUsos -= 1
    }
  }
  override def atacarA(defensor: Personaje, danio: Double, personajeQueAtaca:Personaje): Unit = {
    if (! this.armaEstaGastada()){
      super.atacarA(defensor, danio, personajeQueAtaca)
      this.disminuirUso()
    }
  }
}

trait Paulatino extends Arma {

  var desgaste = 100

  def desgastarArma(): Unit = {
    if(this.desgaste - 10 > 50){
      this.desgaste -= 10
    }
  }

  override def danioDeArma(atacante: Personaje, defensor: Personaje): Double = {
    (super.danioDeArma(atacante, defensor)) * (desgaste * 0.01)
  }

  override def atacarA(personajeAAtacar: Personaje, danio: Double, personajeQueAtaca: Personaje): Unit = {
    super.atacarA(personajeAAtacar, danio, personajeQueAtaca)
    this.desgastarArma()
  }
}

trait Fragil extends Arma {

  def probarFragilidad(danioNormal: Double): Double = {
    if((randomIntBetween(1,20)) == 2){
      return 0
    }
    return danioNormal
  }

  override def danioDeArma(atacante: Personaje, defensor: Personaje): Double =
    probarFragilidad(super.danioDeArma(atacante, defensor))

//  override def atacarA(defensor: Personaje, danio: Double, personajeQueAtaca:Personaje): Unit = {
//
//    super.atacarA(defensor, this.probarFragilidad(danio), personajeQueAtaca)
//  }

}