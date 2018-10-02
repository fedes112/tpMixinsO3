package ar.edu.unq.o3.mixinsLocura.Objetos

import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo
import ar.edu.unq.o3.mixinsLocura.investigador.Investigador

abstract class Objeto {

 def descubrir(investigador: Investigador) : Unit  = {}

}

class Nada extends  Objeto {

  override def descubrir(investigador: Investigador): Unit = {}
}

class Pista extends Objeto {
  override def descubrir(investigador: Investigador): Unit = {
    println("El universo esta en el cinturon de orion")
  }
}

trait Terrorifico extends Objeto{

  var valorADisminuirCordura : Double
  override def descubrir(investigador: Investigador): Unit = {
    super.descubrir(investigador)
    investigador.perderCordura(valorADisminuirCordura)
  }

}

trait Calmante extends Objeto{

  var valorADisminuirCordura : Double

  override def descubrir(investigador: Investigador): Unit = {
    super.descubrir(investigador)
    investigador.recuperarCordura(valorADisminuirCordura)
  }

}

trait ConvocaMonstruo extends Objeto {

  var monstruoAConvocar : Monstruo

  override def descubrir(investigador: Investigador): Unit = {
    super.descubrir(investigador)
    monstruoAConvocar.entrarHabitacion(investigador.habitacion())
  }
}

trait Perjudicial extends Objeto {

  var vidaAPerder : Double

  override def descubrir(investigador: Investigador): Unit = {
    super.descubrir(investigador)
    investigador.recibirDanio(vidaAPerder)
  }
}

trait Saludable extends  Objeto {

  var vidaARecuperar: Double

  override def descubrir(investigador: Investigador): Unit = {
    super.descubrir(investigador)
    investigador.aumentarVidaActual(vidaARecuperar)
  }
}