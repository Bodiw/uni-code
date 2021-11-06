package aed.recursion;

import java.util.Iterator;

import es.upm.aedlib.Pair;
import es.upm.aedlib.positionlist.*;

public class Explorador {

  public static Pair<Object, PositionList<Lugar>> explora(Lugar inicialLugar) {
    PositionList<Lugar> road = new NodePositionList<>();
    exploreAux(inicialLugar, road, PuntoCardinal.NORTE);
    return new Pair<>(road.last().element().getTesoro(), road);
  }

  private static boolean exploreAux(Lugar start, PositionList<Lugar> road, PuntoCardinal pc) {
    // guardar la posicion actual e iniciar el iterador
    road.addLast(start);
    Iterator<Lugar> it = road.last().element().caminos().iterator();
    // caso en el cual continuo en linea recta
    while (it.hasNext()) {
      Lugar l = it.next();
      if (getDirection(road.last().element(), l) == pc && !l.sueloMarcadoConTiza()) {
        road.last().element().marcaSueloConTiza();
        if (exploreAux(l, road, pc))
          break;
      }
    }
    // buscar otros caminos al chocar con una pared/tiza
    it = road.last().element().caminos().iterator();
    while (it.hasNext()) {
      Lugar l = it.next();
      if (!l.sueloMarcadoConTiza()) {
        road.last().element().marcaSueloConTiza();
        if (exploreAux(l, road, getDirection(start, l)))
          break;
      }
    }

    if (road.last().element().tieneTesoro())
      return true;
    // si no hay tesoro ni otros caminos, vuelvo a una rama anterior
    road.remove(road.last());
    return false;
  }

  private static PuntoCardinal getDirection(Lugar start, Lugar end) {
    int x = end.x - start.x;
    int y = end.y - start.y;
    if (x == 1)
      return PuntoCardinal.ESTE;
    else if (x == -1)
      return PuntoCardinal.OESTE;
    else if (y == 1)
      return PuntoCardinal.NORTE;
    else
      return PuntoCardinal.SUR;
  }
}
