package aed.individual4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;

public class MultiSetListIterator<E> implements Iterator<E> {
  PositionList<Pair<E, Integer>> list;

  Position<Pair<E, Integer>> cursor;
  int counter;
  Position<Pair<E, Integer>> prevCursor;

  public MultiSetListIterator(PositionList<Pair<E, Integer>> list) {
    this.list = list;
    cursor = list.first();
    counter = 0;
  }

  public boolean hasNext() {
    return cursor != null;
  }

  public E next() {
    if (cursor == null)
      throw new NoSuchElementException();
    E elem = cursor.element().getLeft();
    prevCursor = cursor;
    counter++;
    if (counter >= cursor.element().getRight()) {
      cursor = list.next(cursor);
      counter = 0;
    }
    return elem;
  }

  public void remove() {
    if (prevCursor == null)
      throw new IllegalStateException();
    Pair<E, Integer> pair = prevCursor.element();
    pair.setRight(pair.getRight() - 1);
    counter--;
    if (pair.getRight() <= 0) {
      if (cursor != null && cursor.equals(prevCursor))
        cursor = list.next(cursor);
      list.remove(prevCursor);
    }
    prevCursor = null;
  }
}
