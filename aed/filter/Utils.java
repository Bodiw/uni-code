package aed.filter;

import java.util.Iterator;
import java.util.function.Predicate;

import es.upm.aedlib.positionlist.NodePositionList;

public class Utils {

  public static <E> Iterable<E> filter(Iterable<E> d, Predicate<E> pred) {
    if (d == null)
      throw new IllegalArgumentException();
    Iterator<E> it = d.iterator();
    NodePositionList<E> list = new NodePositionList<>();
    while (it.hasNext()) {
      E elem = it.next();
      if (elem != null && pred.test(elem)) {
        list.addLast(elem);
      }
    }
    return list;
  }
}
