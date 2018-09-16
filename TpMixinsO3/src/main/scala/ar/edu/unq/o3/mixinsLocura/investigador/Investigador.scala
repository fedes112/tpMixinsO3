package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.{randomElement, randomIntBetween, roundInt}
import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo

import scala.annotation.meta.{getter, setter}


class Investigador(vidaMax: Double, corduraMax: Double) extends Personaje(vidaMax)  {


  def corduraMaxima(): Double = {
    return this._corduraMaxima
  }


  var _habitacionActual : Habitacion =  null
  val _corduraMaxima : Double = corduraMax
  var _corduraActual : Double = corduraMax
  var _estadoDeLocura: Boolean = false


  @throws(classOf[NullPointerException])
  def atacar() : Personaje = {
    if( ! estadoDeLocura()) {
      var monstruoAAtacar = habitacion().monstruoAAtacarPorInvestigador()
      monstruoAAtacar.recibirDanio(randomIntBetween(1, _saludMaxima.toInt))
      return monstruoAAtacar
    }
    else{
      var personajeAleatorio : Personaje = habitacion().personajeAleatorio()
      personajeAleatorio.recibirDanio(randomIntBetween(1, _saludMaxima.toInt))
      return personajeAleatorio
    }
  }

  def habitacion() :Habitacion = {
    this._habitacionActual
  }

  def entrarHabitacion(habitacion: Habitacion) = {
    habitacion.agregarInvestigador(this)
    this._habitacionActual = habitacion
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

  def estadoDeLocura(): Boolean = _estadoDeLocura

}

class Personaje(vidaMax : Double) {

  def aumentarVidaActual(i: Int) = {
    if(this.vidaMaxima() < this.vidaActual() + i){
      this._saludActual =  this.vidaMaxima()
    }
    else{
      this._saludActual += i
    }
  }

  val _saludMaxima: Double = vidaMax

  var _saludActual: Double = vidaMax


  def diferenciaDeVida() : Double = {
    return this.vidaMaxima() - this.vidaActual()
  }

  def vidaMaxima() : Double = {
    this._saludMaxima
  }

  def vidaActual(): Double = {
    this._saludActual
  }

  def recibirDanio(danio: Double) = {
    this._saludActual -= danio
    if (this.vidaActual() <= 0) {
      this._saludActual = 0
    }
  }
}



trait Maton extends Investigador{

  override def atacar(): Personaje = {
    var personajeAtacado = super.atacar()
    if(personajeAtacado.vidaActual() == 0) {
      super.recuperarCordura(super.corduraMaxima())
    }
    return personajeAtacado
  }
}

trait ArtistaMarcial extends Investigador{

  override def atacar(): Personaje = {
  var danioParaMonstruo = (randomIntBetween(1, _saludMaxima.toInt).toDouble)* 1.5
    if( ! estadoDeLocura()) {
    var monstruoAAtacar = habitacion().monstruoAAtacarPorInvestigador()
    monstruoAAtacar.recibirDanio( danioParaMonstruo)
    return monstruoAAtacar
    }
    else{
    var personajeAleatorio : Personaje = habitacion().personajeAleatorio()
    personajeAleatorio.recibirDanio(danioParaMonstruo)
    return personajeAleatorio
    }
  }
}

trait Berserker extends Investigador{

  override def atacar(): Personaje = {
    var danioParaMonstruo = (randomIntBetween(1, _saludMaxima.toInt).toDouble)
    if( ! estadoDeLocura()) {
      var monstruoAAtacar = habitacion().monstruoAAtacarPorInvestigador()
      monstruoAAtacar.recibirDanio( danioParaMonstruo)
      return monstruoAAtacar
    }
    else{
      var personajeAleatorio : Personaje = habitacion().personajeAleatorio()
      personajeAleatorio.recibirDanio(danioParaMonstruo * 2)
      return personajeAleatorio
    }
  }
}

trait Curandero extends  Investigador {
  def curar() = {
    super.habitacion().personajeParaCurar().aumentarVidaActual(2)
  }
}
