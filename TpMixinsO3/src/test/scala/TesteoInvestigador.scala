import ar.edu.unq.o3.mixinsLocura.Monstruo.Monstruo
import ar.edu.unq.o3.mixinsLocura.investigador.Investigador
import org.scalatest.FlatSpec

class TesteoInvestigador extends FlatSpec {

  "el investigador" should " tiene que tener 10 de vida actual" in {
    val investigador = new Investigador(10, 10)
    assert( investigador.vidaActual == 10.0)
  }

  "el investigador" should " tiene 10 de vida actual, recibe 5 de daño y ahora su vida actual es de 5" in {
    val investigador = new Investigador(10, 10)
  investigador.recibirDanio(5.0)
  assert( investigador.vidaActual == 5.0)
  }

  "el investigador" should " tiene que tener 10 de cordura actual" in {
    val investigador = new Investigador(10, 10)
    assert( investigador.corduraActual == 10.0)
  }

  "el investigador" should " tiene 10 de cordura actual, pierde 5 de cordura y ahora su cordura actual es de 5" in {
  val investigador = new Investigador(10, 10)
  investigador.perderCordura(5.0)
    assert( investigador.corduraActual() == 5.0)
  }

  "el investigador" should " tiene 10 de cordura actual, pierde los 10 de cordura y ahora su cordura actual es de 0 y esta en estado ed locura" in {
    val investigador = new Investigador(10, 10)
    assert( !investigador.estadoDeLocura() )
    investigador.perderCordura(10.0)
    assert( investigador.estadoDeLocura() )
  }

  "el monstruo" should " tiene 10 de salud actual" in {
    val monstruo = new Monstruo(10)
    assert( monstruo.vidaActual() == 10.0)
  }

  "el monstruo" should " tiene 20 de vida actual, recibe 5 de daño y ahora su vida actual es de 15" in {
    val monstruo = new Monstruo(20)
    monstruo.recibirDanio(5.0)
    assert( monstruo.vidaActual == 15.0)
  }
}
