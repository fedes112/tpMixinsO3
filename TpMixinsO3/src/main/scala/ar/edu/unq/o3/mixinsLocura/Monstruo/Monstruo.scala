package ar.edu.unq.o3.mixinsLocura.Monstruo

import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.MansionesUtils.randomIntBetween
import ar.edu.unq.o3.mixinsLocura.investigador.{Investigador, Personaje}

abstract class Monstruo(vidaMonstruo: Double) extends Personaje(vidaMonstruo) {

  override def calcularDanioMagico(): Double = {
    1/ this.vidaActual()
  }

  def causarHorror(investigador: Investigador): Unit = {
    investigador.perderCordura(1.0)
  }


  var _vidaActual =  vidaMonstruo
  var habitacionActual: Habitacion = null //subir a pj

  def entrarHabitacion(habitacion: Habitacion) = {
    habitacion.agregarMonstruo(this)
    this.habitacionActual = habitacion
  }

  def danioParaEnemigos(): Double

  def objetivoMonstruo(): Personaje

  def atacar() : Unit = {
   this.atacarA(this.objetivoMonstruo, this.danioParaEnemigos)
  }

  def atacarA(defensor: Personaje, danio :Double): Unit = {
     defensor.recibirDanio(danio)
  }

}

class Bestia(vidaMaximaBestia: Double) extends Monstruo(vidaMaximaBestia){

  override def objetivoMonstruo(): Personaje= {
    this.habitacionActual.objetivoBestia()
  }

  override def danioParaEnemigos(): Double = {
    this.habitacionActual.cantidadDeMonstruos()
  }
}

class Arcano(vidaMaximaBestia: Double) extends Monstruo(vidaMaximaBestia){



  override def objetivoMonstruo(): Personaje= {
    this.habitacionActual.objetivoArcano()
  }

  override def danioParaEnemigos(): Double = {
    this.habitacionActual.corduraPerdidaGlobal()
  }

  override def causarHorror(investigador: Investigador): Unit = {
    super.causarHorror(investigador)
    if((randomIntBetween(0,4)) == 2){
      investigador.perderCorduraMaxima()
    }
  }
}