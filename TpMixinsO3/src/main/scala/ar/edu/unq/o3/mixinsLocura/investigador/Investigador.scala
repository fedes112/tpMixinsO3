package ar.edu.unq.o3.mixinsLocura.investigador


import ar.edu.unq.o3.mixinsLocura.Armas.Arma
import ar.edu.unq.o3.mixinsLocura.Cordura.PersonajeConCordura
import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.{randomElement, randomIntBetween, roundInt}
import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo



class Investigador(vidaMax: Double, corduraMax: Double) extends Personaje(vidaMax) with PersonajeConCordura with PoseeArma {

  override var _corduraMaxima = corduraMax
  override var _corduraActual = corduraMax

  //Tendria que hacer cambiarArma y que eso le cambie el duenio al arma anterior que tenia y a la actual


  def equiparArma(armaDeFuego: Arma) = {

    this._armaEquipada = armaDeFuego
    armaDeFuego.personajeQueMeEstaUsando(this)
  }

  override def objetivoSegunEstadoDeLocuraObjetivo() : Personaje = {
    if(this.estadoDeLocura()){
      return personajeParaAtacarInvestigador()
    }
    else {
      return  monstruoParaAtacarInvestigador()
    }
  }



  @throws(classOf[NullPointerException])
  def atacar() : Personaje = {
    var objetivo = objetivoSegunEstadoDeLocuraObjetivo()
    var danio = danioARealizar(objetivo)
    this.armaEquipada().atacarA(objetivo, danio)
    return objetivo
  }


  def monstruoParaAtacarInvestigador(): Monstruo ={
    return habitacion().monstruoAAtacarPorInvestigador()
  }

  def personajeParaAtacarInvestigador(): Personaje = {
    return habitacion().personajeAleatorio()
  }

  def entrarHabitacion(habitacion: Habitacion) = {
    super.entrarHabitacion(habitacion, this)
    habitacion.efectoAgregarInvestigador(this)
  }

  override def calcularDanioMagico(): Double = {
    1/ this.corduraActual()+1
  }

}

abstract class Personaje(vidaMax : Double) {

  val saludMaxima: Double = vidaMax
  var saludActual: Double = vidaMax
  var habitacionActual : Habitacion =  null

  def calcularDanioMagico() : Double

  def aumentarVidaActual(i: Int) = {
    if(this.vidaMaxima() < this.vidaActual() + i){
      this.saludActual =  this.vidaMaxima()
    }
    else{
      this.saludActual += i
    }
  }

  def entrarHabitacion(habitacion: Habitacion, personajeQueEntra: Personaje) = {
    habitacion.agregarPersonaje(personajeQueEntra)
    this.habitacionActual = habitacion
  }

  def estaMuerto(): Boolean = {
    vidaActual() == 0
  }

  def habitacion() :Habitacion = {
    this.habitacionActual
  }

  def diferenciaDeVida() : Double = {
    return this.vidaMaxima() - this.vidaActual()
  }

  def vidaMaxima() : Double = {
    this.saludMaxima
  }

  def vidaActual(): Double = {
    this.saludActual
  }

  def recibirDanio(danio: Double) = {
    this.saludActual -= danio
    if (this.vidaActual() <= 0) {
      this.saludActual = 0
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

trait ArtistaMarcial extends PoseeArma {

  override def danioARealizar(objetivo: Personaje): Double = super.danioARealizar(objetivo) * 1.5

//  override def danioDeArma(atacante: Personaje, defensor: Personaje): Double = {
//    super.danioDeArma(atacante, defensor) * 1.5
// }

}

trait Berserker extends PoseeArma {

  override def danioARealizar(objetivo: Personaje) : Double = {
    if (this.personajeQueTieneEquipadaEstaArma.estaMuerto()) {
      return super.danioARealizar(objetivo) * 2.0
    }
    super.danioARealizar(objetivo)

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