package ar.edu.unq.o3.mixinsLocura.Monstruo

import ar.edu.unq.o3.mixinsLocura.Armas.Arma
import ar.edu.unq.o3.mixinsLocura.Cordura.PersonajeConCordura
import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween
import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje, PoseeArma}

abstract class Monstruo(vidaMonstruo: Double) extends Personaje(vidaMonstruo) {

  var _vidaActual =  vidaMonstruo

  override def calcularDanioMagico(): Double = {
    1/ this.vidaActual()
  }

  def causarHorror(investigador: Investigador): Unit = {
    investigador.perderCordura(1.0)
  }

  def danioParaEnemigos(personaje:Personaje): Double

  def objetivoMonstruo(): Personaje

  override def atacar() : Personaje = {
    var  objetivo = this.objetivoMonstruo
    this.monstruoAtacarA(objetivo , this.danioParaEnemigos(this.objetivoMonstruo()))
    return objetivo
  }

  def entrarHabitacion(habitacion: Habitacion) = {
    super.entrarHabitacion(habitacion, this)
    habitacion.efectoAgregarMonstruo(this)
  }

  def monstruoAtacarA(defensor: Personaje, danio :Double): Unit = {
     defensor.recibirDanio(danio)
  }

}

class Bestia(vidaMaximaBestia: Double) extends Monstruo(vidaMaximaBestia){

  override def objetivoMonstruo(): Personaje= {
    this.habitacionActual.objetivoBestia()
  }

  override def danioParaEnemigos(personaje:Personaje): Double = {
    this.habitacionActual.cantidadDeMonstruos()
  }
}

class Arcano(vidaMaximaBestia: Double) extends Monstruo(vidaMaximaBestia){

  override def objetivoMonstruo(): Personaje= {
    this.habitacionActual.objetivoArcano()
  }

  override def danioParaEnemigos(personaje:Personaje): Double = {
    this.habitacionActual.corduraPerdidaGlobal()
  }

  override def causarHorror(investigador: Investigador): Unit = {
    super.causarHorror(investigador)
    if((randomIntBetween(0,4)) == 2){
      investigador.perderCorduraMaxima()
    }
  }
}

class Humanoide(vidaMaximaHumanoide: Double, corduraMaxima:Double) extends Monstruo(vidaMaximaHumanoide) with PersonajeConCordura with Arma{

  override var _corduraMaxima: Double = corduraMaxima
  override var _corduraActual: Double = corduraMaxima

  override def recibirDanio(danio: Double): Unit ={
    super.recibirDanio(danio)
  }

  override def danioParaEnemigos(personaje:Personaje): Double = {
    armaEquipada().danioDeArma(this, personaje)
  }

  def personajeParaAtacarHumanoide(): Personaje = {
    this.habitacion().personajeAleatorio()
  }

  def investigadorParaAtacarHumanoide(): Personaje = {
    this.habitacion().investigadorAAtacarPorHumanoide()
  }


  override def objetivoSegunEstadoDeLocuraObjetivo(): Personaje = {
    if(this.estadoDeLocura()){
      return personajeParaAtacarHumanoide()
    }
    else {
      return  investigadorParaAtacarHumanoide()
    }
  }

  override def atacar() : Personaje = {
    var objetivo = this.objetivoMonstruo()
    var danio = this.danioARealizar(objetivo, this)
    this.armaEquipada().atacarA(objetivo, danio, this)
    return objetivo
  }

  override def objetivoMonstruo(): Personaje = {
    this.objetivoSegunEstadoDeLocuraObjetivo()
  }
}