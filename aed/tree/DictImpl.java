package aed.tree;

import java.util.Iterator;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.tree.GeneralTree;
import es.upm.aedlib.tree.LinkedGeneralTree;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class DictImpl implements Dictionary {
  // A boolean because we need to know if a word ends in a node or not
  GeneralTree<Pair<Character, Boolean>> tree;

  public DictImpl() {
    tree = new LinkedGeneralTree<>();
    tree.addRoot(new Pair<Character, Boolean>(null, false));
  }

  public void add(String word) {
    if (word == null || word.isEmpty())
      throw new IllegalArgumentException();
    Position<Pair<Character, Boolean>> pos = tree.root();
    for (int i = 0; i < word.length(); i++) {
      char c = word.charAt(i);
      Position<Pair<Character, Boolean>> newPos = search(c, pos, false);
      // adding first child or at last pos
      if (newPos == null)
        pos = tree.addChildLast(pos, new Pair<Character, Boolean>(c, false));
      // existing child
      else if (newPos.element().getLeft().charValue() == c)
        pos = newPos;
      // inserting child
      else
        pos = tree.insertSiblingBefore(newPos, new Pair<Character, Boolean>(c, false));
    }
    // once its done adding the word, set it to true
    pos.element().setRight(true);
  }

  public void delete(String word) {
    if (word == null || word.isEmpty())
      throw new IllegalArgumentException();
    Position<Pair<Character, Boolean>> pos = wordLastCharPos(word);
    if (pos != null)
      pos.element().setRight(false);
  }

  public boolean isIncluded(String word) {
    if (word == null || word.isEmpty())
      throw new IllegalArgumentException();
    Position<Pair<Character, Boolean>> pos = wordLastCharPos(word);
    return pos != null && pos.element().getRight();
  }

  public PositionList<String> wordsBeginningWithPrefix(String prefix) {
    NodePositionList<String> list = new NodePositionList<>();
    Position<Pair<Character, Boolean>> pos = wordLastCharPos(prefix);
    if (pos != null) {
      // yup, this is a bugfix for the prefix not being added
      if (pos.element().getRight())
        list.addLast(prefix);
      prefixRecAux(prefix, list, pos);
    }
    return list;
  }

  private Position<Pair<Character, Boolean>> search(char c, Position<Pair<Character, Boolean>> pos, boolean exact) {
    Iterator<Position<Pair<Character, Boolean>>> it = tree.children(pos).iterator();
    while (it.hasNext()) {
      Position<Pair<Character, Boolean>> child = it.next();
      char charAtPos = child.element().getLeft().charValue();
      // node containing the char
      if (charAtPos == c)
        return child;
      // next node, for insert before sibling
      else if (c < charAtPos && !exact)
        return child;
    }
    // if no child is returned, it means it has to be included last(or be the frist)
    return null;
  }

  private Position<Pair<Character, Boolean>> wordLastCharPos(String word) {
    NodePositionList<String> list = new NodePositionList<>();
    Position<Pair<Character, Boolean>> pos = tree.root();
    for (int i = 0; i < word.length() && pos != null; i++) {
      pos = search(word.charAt(i), pos, true);
    }
    return pos;
  }

  public void prefixRecAux(String prefix, NodePositionList<String> list, Position<Pair<Character, Boolean>> pos) {
    for (Position<Pair<Character, Boolean>> p : tree.children(pos)) {
      String word = prefix + p.element().getLeft();
      if (p.element().getRight())
        list.addLast(word);
      // check if there's even children to explore, for performance
      if (tree.children(pos).iterator().hasNext())
        prefixRecAux(word, list, p);
    }
  }
}
