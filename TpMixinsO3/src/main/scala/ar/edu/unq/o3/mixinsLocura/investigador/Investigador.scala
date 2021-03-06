package ar.edu.unq.o3.mixinsLocura.investigador


import ar.edu.unq.o3.mixinsLocura.Cordura.PersonajeConCordura
import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.{randomElement, randomIntBetween, roundInt}
import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo



class Investigador(vidaMax: Double, corduraMax: Double) extends Personaje(vidaMax) with PersonajeConCordura with PoseeArma {
  override var _corduraMaxima = corduraMax
  override var _corduraActual = corduraMax

  def explorar() = {
    habitacion().conseguirUnObjetoRandom().descubrir(this)
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
  override def atacar() : Personaje = {
    var objetivo = objetivoSegunEstadoDeLocuraObjetivo()
    var danio = danioARealizar(objetivo, this)
    this.armaEquipada().atacarA(objetivo, danio, this)
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

abstract class Personaje(vidaMax : Double) extends EstarEnHabitacion {

  val saludMaxima: Double = vidaMax
  var saludActual: Double = vidaMax

  def calcularDanioMagico() : Double //poner el de la vida

  def atacar(): Personaje = new Investigador(10,10)

  def aumentarVidaActual(i: Double) = {
    if(this.vidaMaxima() < this.vidaActual() + i){
      this.saludActual =  this.vidaMaxima()
    }
    else{
      this.saludActual += i
    }
  }

  def estaMuerto(): Boolean = {
    vidaActual() == 0
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


trait Maton extends Personaje with PersonajeConCordura  {

  override def atacar(): Personaje = {
    var personajeAtacado = super.atacar()
    if(personajeAtacado.estaMuerto()) {
      super.recuperarCorduraAlMaximo()
    }
    return personajeAtacado
  }
}

trait ArtistaMarcial extends PoseeArma {

  override def danioARealizar(objetivo: Personaje, atacante: Personaje): Double = super.danioARealizar(objetivo, atacante) * 1.5


}

trait Berserker extends PersonajeConCordura with PoseeArma {

  override def danioARealizar(objetivo: Personaje, atacante: Personaje) : Double = {
    if (this.estadoDeLocura()) {
      return super.danioARealizar(objetivo, atacante) * 2.0
    }
    super.danioARealizar(objetivo,atacante)

  }
}

trait Cobarde extends Personaje with PersonajeConCordura {

  override def atacar(): Personaje = {
    this.perderCordura(1)
    super.atacar()
  }
}

trait Inestable extends Personaje with PersonajeConCordura{

  override def perderCordura(cordura: Double) = {
    this.recibirDanio(1)
    super.perderCordura(cordura)
  }

}

trait Curandero extends Personaje  {
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

trait EstarEnHabitacion{
  var habitacionActual : Habitacion =  null

  def entrarHabitacion(habitacion: Habitacion, personajeQueEntra: Personaje) = {
    habitacion.agregarPersonaje(personajeQueEntra)
    this.habitacionActual = habitacion
  }

  def habitacion() :Habitacion = {
    this.habitacionActual
  }
}
