import ar.edu.unq.o3.mixinsLocura.Armas._
import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.Monstruo.{Arcano, Bestia, Monstruo}
import ar.edu.unq.o3.mixinsLocura.investigador._
import org.scalatest.{BeforeAndAfter, FlatSpec}

class TesteoDominio extends FlatSpec with BeforeAndAfter {


  var investigador : Investigador = _
  var monstruo : Bestia =  _
  var habitacion : Habitacion = _

  before{
    investigador = new Investigador(10, 10)
    monstruo = new Bestia(20)
    habitacion = new Habitacion()
  }


  "el investigador tiene vida al maximo" should " tiene que tener 10 de vida actual" in {
    assert( investigador.vidaActual == 10.0)
  }

  "el investigador tiene 10 de vida actual, recibe 5 de daño " should " ahora su vida actual es de 5" in {
  investigador.recibirDanio(5.0)
  assert( investigador.vidaActual == 5.0)
  }

  "el investigador" should " tiene que tener 10 de cordura actual" in {
    assert( investigador.corduraActual == 10.0)
  }

  "el investigador tiene 10 de cordura actual, pierde 5 de cordura " should " ahora su cordura actual es de 5" in {
  investigador.perderCordura(5.0)
    assert( investigador.corduraActual() == 5.0)
  }

  "el investigador tiene 10 de cordura actual, pierde los 10 de cordura " should " ahora su cordura actual es de 0 y esta en estado ed locura" in {
    var investigador2 = new Investigador(1,2)
    assert( !investigador.estadoDeLocura() )
    investigador.perderCordura(10.0)
    assert( investigador.estadoDeLocura() )
    investigador2.entrarHabitacion(habitacion)
    investigador.entrarHabitacion(habitacion)
    investigador.atacar()
    assert(investigador2.vidaActual() == 0 || investigador.vidaActual() == 0)

  }

  "el monstruo esta con la vida al maximo" should " tiene 10 de salud actual" in {
    assert( monstruo.vidaActual() == 20.0)
  }

  "el monstruo tiene 20 de vida actual, recibe 5 de daño " should " ahora su vida actual es de 15" in {
    monstruo.recibirDanio(5.0)
    assert( monstruo.vidaActual == 15.0)
  }

  /*"el monstruo ataca a un investigador,el investigador recibe 5 de daño y " should " ahora la vida actual del investigador es de 10" in {
    val monstruo = new Bestia(20)
    val investigador = new Investigador(15,10)
    monstruo.atacar()
    assert(investigador.vidaActual() == 5)
  }*/

  " al entrar en la habitacion, el monstruo ocasiona 1 de  terror a un investigador con 10 de cordura," should " la cordura del investigador pasa a ser 9" in {
    investigador.entrarHabitacion(habitacion)
    monstruo.entrarHabitacion(habitacion)
    assert(investigador.corduraActual() == 9)
  }

  "el investigador ataca a un monstruo que esta en su misma habitacion" should " la vida del monstruo baja en 1 " in {
    monstruo.entrarHabitacion(habitacion)
    investigador.entrarHabitacion(habitacion)
    investigador.atacar()
    assert(monstruo.vidaActual() !== 20)
  }

  "el investigador ataca " should "  al no encontrarse en una habitacion se dispara una excepcion " in {
    monstruo.entrarHabitacion(habitacion)
    assertThrows[NullPointerException] {
      investigador.atacar()
    }
  }

  "una bestia ataca al investigador mas debil causando 1 de danio por cada monstruo presente en la habitacion" should "la vida del investigador baja en 2 " in {
    val bestia = new Bestia(10.0)
    val bestia2 = new Bestia(10.0)
    investigador.entrarHabitacion(habitacion)
    bestia.entrarHabitacion(habitacion)
    bestia2.entrarHabitacion(habitacion)
    bestia.atacar()
    assert(investigador.vidaActual() == 8.0)
  }

  "un investigador entra a una habitacion en la que habia 2 monstruos dentro" should "la cordura del investigador baja en 2 " in {
    val bestia = new Bestia(10.0)
    val bestia2 = new Bestia(10.0)
    bestia.entrarHabitacion(habitacion)
    bestia2.entrarHabitacion(habitacion)
    investigador.entrarHabitacion(habitacion)
    assert(investigador.corduraActual()==8.00)
  }
  "un invesigador con 10 de cordura pierde 10 de cordura " should "la cordura del investigador baja a 0 y entra en estado de locura" in {
    investigador.perderCordura(10.0)
    assert(investigador.estadoDeLocura())
  }

  "un invesigador maton con 8 de cordura ataca y mata a una bestia" should "la cordura del investigador  se reestablece a su maximo " in {
    var investigadorMaton = new Investigador(10.0, 10.0) with Maton
    investigadorMaton.perderCordura(2.0)
    investigadorMaton.entrarHabitacion(habitacion)
    monstruo.entrarHabitacion(habitacion)
    monstruo.recibirDanio(19.0)
    investigadorMaton.atacar()
    assert(investigadorMaton.corduraActual() == investigadorMaton.corduraMaxima())
}

  "un invesigador artista Marcial ataca a una bestia con 1.5 de vida y un investigador normal ataca a otra con 1.5  de vida" should "la vida de la bestia que fue atacada por el investigador marcial es 0, la otra bestia sigue viva " in {
    var investigadorMarcial= new Investigador(2.0, 10.0) with ArtistaMarcial
    var bestia2 = new Bestia(1.5)
    var habitacion2 = new Habitacion()
    var investigador2 = new Investigador(2.0,10.0)
    investigadorMarcial.entrarHabitacion(habitacion)
    investigador2.entrarHabitacion(habitacion2)
    bestia2.entrarHabitacion(habitacion2)
    monstruo.entrarHabitacion(habitacion)
    monstruo.recibirDanio(18.5)
    investigadorMarcial.atacar()
    investigador2.atacar()
    assert(bestia2.vidaActual() > 0)
    assert(monstruo.vidaActual() == 0)
  }

  "un invesigador berserker loco ataca a una bestia y un investigador berserker con cordura ataca a otra bestia " should "la vida de la bestia que fue atacada por el investigador loco muere, la otra sigue con vida" in {
    var investigadorMarcial= new Investigador(3.0, 1.0) with Berserker
    var bestia2 = new Bestia(2.5)
    var habitacion2 = new Habitacion()
    var investigador2 = new Investigador(3.0,10.0) with Berserker
    investigadorMarcial.entrarHabitacion(habitacion)
    investigador2.entrarHabitacion(habitacion2)
    bestia2.entrarHabitacion(habitacion2)
    monstruo.entrarHabitacion(habitacion)
    monstruo.recibirDanio(17.5)
    investigadorMarcial.atacar()
    investigador2.atacar()
    assert(bestia2.vidaActual() > 0)
    assert(monstruo.vidaActual() == 0)
  }

  "un invesigador curandero cura a un investigador que tiene 8 puntos de vida actual" should "la vida del investigador normal deberia pasar a ser 10" in {
    var investigadorCurandero= new Investigador(2.0, 1.0) with Curandero
    investigadorCurandero.entrarHabitacion(habitacion)
    investigador.entrarHabitacion(habitacion)
    investigador.recibirDanio(2.0)
    assert(investigador.vidaActual() == 8.0)
    investigadorCurandero.curar()
    assert(investigador.vidaActual() == 10.0)
  }

  "un investigador Cobarde entra en una habitacion, un monstruo entra en la misma habitacion y el investigador ataca a la bestia " should "la cordura del investigador pasa a ser 8 " in {
    var investigadorCobarde= new Investigador(2.0, 10.0) with Cobarde
    investigadorCobarde.entrarHabitacion(habitacion)
    monstruo.entrarHabitacion(habitacion)
    investigadorCobarde.atacar()
    assert(investigadorCobarde.corduraActual() == 8.0)
  }

  "un invesigador Inestable entra en una habitacion y pierde 1 punto de cordura" should "la vida pasa a ser 9 " in {
    var investigadorInestable= new Investigador(10.0, 1.0) with Inestable
    monstruo.entrarHabitacion(habitacion)
    investigadorInestable.entrarHabitacion(habitacion)
    assert(investigadorInestable.vidaActual() == 9.0)
  }

  "un invesigador martir entra en una habitacion y cura la cordura de otro investigador" should "la cordura termina en 10 y la vida en 9" in {
    var investigadorMartir= new Investigador(10.0, 1.0) with Martir
    monstruo.entrarHabitacion(habitacion)
    investigador.entrarHabitacion(habitacion)
    investigadorMartir.entrarHabitacion(habitacion)
    investigadorMartir.curarCordura(2)
    assert(investigadorMartir.vidaActual() == 9.0)
    assert(investigador.corduraActual() == 10.0)
  }

"un mounstro arcano entra a la sala y ataca al investigador" should "investigador2 queda en 1 de cordura" in {
  var investigador2 = new Investigador(7.0,3.0)
  var arcano = new Arcano(15.0)
  investigador.entrarHabitacion(habitacion)
  investigador2.entrarHabitacion(habitacion)
  investigador.perderCordura(3)
  arcano.entrarHabitacion(habitacion)
  arcano.atacar()
  assert(investigador2.vidaActual()  == 2)
  }

  "un investigador posee un arma de fuego y ataca con ella a una bestia con 5 de vida inflingiendo 5 de danio" should "la bestia pasa a tener 0 de vida" in {
    var armaDeFuego = new ArmaDeFuego()
    var monstruo2 = new Bestia(5)
    investigador.entrarHabitacion(habitacion)
    monstruo2.entrarHabitacion(habitacion)
    investigador.equiparArma(armaDeFuego)
    investigador.atacarConArma()//deberia pasar defensor y atacante
    assert(monstruo2.vidaActual() == 0 )
  }

  "un investigador con 10 de vida posee un arma de esfuerzo fisico y ataca con ella a una bestia con 15 de vida inflingiendo 15 de danio" should "la bestia pasa a tener 0 de vida" in {
    var armaDeEsfuerzoFisico= new ArmaDeEsfuerzoFisico()
    var monstruo2 = new Bestia(15)
    investigador.entrarHabitacion(habitacion)
    monstruo2.entrarHabitacion(habitacion)
    investigador.equiparArma(armaDeEsfuerzoFisico)
    investigador.atacarConArma()//deberia pasar defensor y atacante
    assert(monstruo2.vidaActual() == 0 )
  }


  "un investigador con 10 de vida posee un hechizo de 8 de danio base y ataca con el a una bestia con 4 de vida inflingiendo 2 de danio" should "la bestia pasa a tener 2 de vida" in {
    var hechizo = new Hechizo(8)
    var monstruo2 = new Bestia(4)
    investigador.entrarHabitacion(habitacion)
    monstruo2.entrarHabitacion(habitacion)
    investigador.equiparArma(hechizo)
    investigador.atacarConArma()
    assert(monstruo2.vidaActual() == 2 )
  }

  "un investigador posee un arma de fuego con 5 de danio base con danio reducido y ataca con ella a una bestia con 5 de vida inflingiendo 4 de danio" should "la bestia pasa a tener 0 de vida" in {
    var armaDeFuego = new ArmaDeFuego() with DanioReducido
    var monstruo2 = new Bestia(5)
    investigador.entrarHabitacion(habitacion)
    monstruo2.entrarHabitacion(habitacion)
    investigador.equiparArma(armaDeFuego)
    investigador.atacarConArma()//deberia pasar defensor y atacante
    assert(monstruo2.vidaActual() == 1 )
  }
}
