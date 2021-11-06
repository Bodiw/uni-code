package aed.recursion;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.indexedlist.*;
import es.upm.aedlib.positionlist.*;

public class Utils {

  private static int multiplyAux(int a, int b) {
    if (a != 0 && a % 2 != 0)
      return b + multiplyAux(a / 2, b * 2);
    else if (a != 0)
      return multiplyAux(a / 2, b * 2);
    return 0;
  }

  private static <E extends Comparable<E>> int isHole(IndexedList<E> l, int pos) {
    // Is on the border
    if (pos == 0 || pos == l.size() - 1)
      return 0;
    // lesser than right
    if (l.get(pos).compareTo(l.get(pos + 1)) <= 0) {
      // lesser than left too
      if (l.get(pos).compareTo(l.get(pos - 1)) <= 0)
        return 0;
      // if lesser than right and greater than left, we inspect left
      return -1;
    }
    // greater than right
    return 1;
  }

  public static <E extends Comparable<E>> int findBottomAux(IndexedList<E> l, int bottom, int top) {
    int mid = bottom + (top - bottom) / 2;
    int whereGo = isHole(l, mid);
    if (whereGo == 0)
      return mid;
    // hotfix for issue where hole is 2nd or penultimate element and it loops into
    // stack overflow
    else if (mid == 1 || mid == l.size() - 2)
      return whereGo == 1 ? mid + 1 : mid - 1;
    else if (whereGo == 1)
      return findBottomAux(l, mid, top);
    else if (whereGo == -1)
      return findBottomAux(l, bottom, mid);
    return 0;
  }

  public static int multiply(int a, int b) {
    int sign = 1;
    if ((a < 0 || b < 0) && !(a < 0 && b < 0))
      sign = -1;
    // sign neutering for a and b
    a = a < 0 ? -a : a;
    b = b < 0 ? -b : b;

    return sign * multiplyAux(a, b);
  }

  public static <E extends Comparable<E>> int findBottom(IndexedList<E> l) {
    return findBottomAux(l, 0, l.size() - 1);
  }

  public static <E extends Comparable<E>> NodePositionList<Pair<E, Integer>> joinMultiSets(
      NodePositionList<Pair<E, Integer>> l1, NodePositionList<Pair<E, Integer>> l2) {

    NodePositionList<Pair<E, Integer>> list = new NodePositionList<>(l1);
    Position<Pair<E, Integer>> pos1 = list.first();
    Position<Pair<E, Integer>> pos2 = l2.first();
    joinMultiSetsAux(list, l2, pos1, pos2);
    return list;
  }

  private static <E extends Comparable<E>> void joinMultiSetsAux(NodePositionList<Pair<E, Integer>> l1,
      NodePositionList<Pair<E, Integer>> l2, Position<Pair<E, Integer>> pos1, Position<Pair<E, Integer>> pos2) {
    // i know its a break...but come on, should I let it reach the end?
    if (pos2 == null)
      return;
    // end of l1, handles all elements in l2 greater than l1's greatest
    else if (pos1 == null && pos2 != null) {
      l1.addLast(pos2.element());
      pos2 = l2.next(pos2);
      // is the elemement on l2 bigger than l1? (to add before itself)
    } else if (pos1.element().getLeft().compareTo(pos2.element().getLeft()) >= 0) {
      // if they're the same element
      if (pos1.element().getLeft().equals(pos2.element().getLeft()))
        pos1.element().setRight(pos1.element().getRight() + pos2.element().getRight());
      else {
        l1.addBefore(pos1, pos2.element());
        // in case l2 contains more small elements, we moving the pointer back
        pos1 = l1.prev(pos1);
      }
      // next element time
      pos2 = l2.next(pos2);
    } else
      // element in l2 >> element in l1 pos
      pos1 = l1.next(pos1);
    // next iteration
    joinMultiSetsAux(l1, l2, pos1, pos2);
  }
}
