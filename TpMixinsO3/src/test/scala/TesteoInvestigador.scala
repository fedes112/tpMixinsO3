import ar.edu.unq.o3.mixinsLocura.Habitacion.Habitacion
import ar.edu.unq.o3.mixinsLocura.Monstruo.{Bestia, Monstruo}
import ar.edu.unq.o3.mixinsLocura.investigador.Investigador
import org.scalatest.FlatSpec

class TesteoInvestigador extends FlatSpec {

  "el investigador tiene vida al maximo" should " tiene que tener 10 de vida actual" in {
    val investigador = new Investigador(10, 10)
    assert( investigador.vidaActual == 10.0)
  }

  "el investigador tiene 10 de vida actual, recibe 5 de daño " should " ahora su vida actual es de 5" in {
    val investigador = new Investigador(10, 10)
  investigador.recibirDanio(5.0)
  assert( investigador.vidaActual == 5.0)
  }

  "el investigador" should " tiene que tener 10 de cordura actual" in {
    val investigador = new Investigador(10, 10)
    assert( investigador.corduraActual == 10.0)
  }

  "el investigador tiene 10 de cordura actual, pierde 5 de cordura " should " ahora su cordura actual es de 5" in {
  val investigador = new Investigador(10, 10)
  investigador.perderCordura(5.0)
    assert( investigador.corduraActual() == 5.0)
  }

  "el investigador tiene 10 de cordura actual, pierde los 10 de cordura " should " ahora su cordura actual es de 0 y esta en estado ed locura" in {
    val investigador = new Investigador(10, 10)
    assert( !investigador.estadoDeLocura() )
    investigador.perderCordura(10.0)
    assert( investigador.estadoDeLocura() )
  }

  "el monstruo esta con la vida al maximo" should " tiene 10 de salud actual" in {
    val monstruo = new Bestia(20)
    assert( monstruo.vidaActual() == 20.0)
  }

  "el monstruo tiene 20 de vida actual, recibe 5 de daño " should " ahora su vida actual es de 15" in {
    val monstruo = new Bestia(20)
    monstruo.recibirDanio(5.0)
    assert( monstruo.vidaActual == 15.0)
  }

  /*"el monstruo ataca a un investigador,el investigador recibe 5 de daño y " should " ahora la vida actual del investigador es de 10" in {
    val monstruo = new Bestia(20)
    val investigador = new Investigador(10,10)
    monstruo.atacar()
    assert(investigador.vidaActual() == 5)
  }*/

  " al entrar en la habitacion, el monstruo ocasiona 5 de  terror a un investigador con 10 de cordura," should " la cordura del investigador pasa a ser 5" in {
    val monstruo = new Bestia(20)
    val investigador = new Investigador(10,10)
    val habitacion = new Habitacion()
    investigador.entrarHabitacion(habitacion)
    monstruo.entrarHabitacion(habitacion)
    assert(investigador.corduraActual() == 5)
  }

  "el investigador ataca a un monstruo que esta en su misma habitacion" should " la vida del monstruo baja en 1 " in {
    val monstruo = new Bestia(20)
    val investigador = new Investigador(10,10)
    val habitacion = new Habitacion()
    monstruo.entrarHabitacion(habitacion)
    investigador.entrarHabitacion(habitacion)
    investigador.atacar()
    assert(monstruo.vidaActual() == 19)
  }

  "el investigador ataca " should "  al no encontrarse en una habitacion se dispara una excepcion " in {
    val monstruo = new Bestia(20)
    val investigador = new Investigador(10,10)
    val habitacion = new Habitacion()
    monstruo.entrarHabitacion(habitacion)
    assertThrows[NullPointerException] {
      investigador.atacar()
    }
  }

  "una bestia ataca al investigador mas debil causando 1 de danio por cada monstruo presente en la habitacion" should "la vida del investigador baja en 2 " in {
    val investigador = new Investigador(10.0,10.0)
    val habitacion = new Habitacion()
    val bestia = new Bestia(10.0)
    val bestia2 = new Bestia(10.0)
    investigador.entrarHabitacion(habitacion)
    bestia.entrarHabitacion(habitacion)
    bestia2.entrarHabitacion(habitacion)
    bestia.atacar()
    assert(investigador.vidaActual() == 8.0)
  }
}
