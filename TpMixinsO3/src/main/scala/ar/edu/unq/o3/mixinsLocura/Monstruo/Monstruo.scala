package ar.edu.unq.o3.mixinsLocura.Monstruo

class Monstruo(vidaMonstruo: Double) {
  def recibirDanio(danio: Double) = {
    this._vidaActual -= danio
    if( this.vidaActual() <= 0) {
      this._vidaActual = 0
    }
  }


  var _vidaActual =  vidaMonstruo

 def vidaActual() : Double = {
    this._vidaActual
 }

}
