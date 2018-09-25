package ar.edu.unq.o3.mixinsLocura.investigador

import ar.edu.unq.o3.mixinsLocura.Armas.Arma
import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.{randomElement, randomIntBetween, roundInt}
import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo



class Investigador(vidaMax: Double, corduraMax: Double) extends Personaje(vidaMax)  {


  val _corduraMaxima: Double = corduraMax
   var _corduraActual = this.corduraMaxima()

  def atacarConArma() = {
    this.armaEquipada().atacar(this, this.estadoDeLocuraObjetivo())
  }


  def equiparArma(armaDeFuego: Arma) = {
    this._armaEquipada = armaDeFuego
  }

  def armaEquipada() : Arma = {
    this._armaEquipada
  }

  var _armaEquipada : Arma = null
  var _estadoDeLocura: Boolean = false

  def recuperarCorduraAlMaximo() = {
    this._corduraActual = corduraMaxima()
  }

  def estadoDeLocuraObjetivo() : Personaje = {
    if(this.estadoDeLocura()){
      return personajeParaAtacarInvestigador()
    }
    else {
      return  monstruoParaAtacarInvestigador()
    }
  }

  @throws(classOf[NullPointerException])
  def atacar() : Personaje = {
    var objetivo = estadoDeLocuraObjetivo()
    objetivo.recibirDanio(danioParaEnemigos()) //partirlo a quien atacar, cuanto daño y locura
    return objetivo
  }

  def perderCorduraMaxima() = {
    _corduraActual = 0
  }

  def corduraMaxima(): Double = {
    return this._corduraMaxima
  }

  def danioParaEnemigos() : Double = {
    return (randomIntBetween(1, _saludMaxima.toInt).toDouble)
  }

  def monstruoParaAtacarInvestigador(): Monstruo ={
    return habitacion().monstruoAAtacarPorInvestigador()
  }

  def personajeParaAtacarInvestigador(): Personaje = {
    return habitacion().personajeAleatorio()
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

  def corduraPerdida(): Double ={ corduraMax - corduraActual()}

  override def calcularDanioMagico(): Double = {
    1/ this.corduraActual()+1
  }
}

abstract class Personaje(vidaMax : Double) {

  def calcularDanioMagico() : Double


  val _saludMaxima: Double = vidaMax
  var _habitacionActual : Habitacion =  null
  var _saludActual: Double = vidaMax


  def aumentarVidaActual(i: Int) = {
    if(this.vidaMaxima() < this.vidaActual() + i){
      this._saludActual =  this.vidaMaxima()
    }
    else{
      this._saludActual += i
    }
  }


  def estaMuerto(): Boolean = {
    vidaActual() == 0
  }

  def habitacion() :Habitacion = {
    this._habitacionActual
  }

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
    if(personajeAtacado.estaMuerto()) {
      super.recuperarCorduraAlMaximo()
    }
    return personajeAtacado
  }
}

trait ArtistaMarcial extends Investigador{
  override def danioParaEnemigos(): Double = {super.danioParaEnemigos()*1.5}
}

trait Berserker extends Investigador {


  override def danioParaEnemigos(): Double = {
    if (this.estadoDeLocura()) {
      return super.danioParaEnemigos() * 2.0
    }
    super.danioParaEnemigos()
  }

}

trait Cobarde extends Investigador{

  override def atacar(): Personaje = {
    this.perderCordura(1)
    super.atacar()
  }
}

trait Inestable extends Investigador{

  override def perderCordura(cordura: Double) = {
    this.recibirDanio(1)
    super.perderCordura(cordura)
  }

}

trait Curandero extends  Investigador {
  def curar() = {
    this.habitacion().personajeParaCurar().aumentarVidaActual(2)
  }
}

trait Martir extends  Investigador {
  def curarCordura(corduraARecuperar: Double) = {
    if(this.vidaActual()-corduraARecuperar >0){
      this.recibirDanio(roundInt(corduraARecuperar*0.5))
      this.habitacion().investigadores().foreach(investigador => investigador.recuperarCordura(corduraARecuperar))
    }
  else{ println("No tienes vida suficiente para curar esa cantidad")} }
}