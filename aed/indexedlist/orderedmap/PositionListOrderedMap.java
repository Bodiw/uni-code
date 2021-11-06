package aed.orderedmap;

import java.util.Comparator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.Position;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class PositionListOrderedMap<K, V> implements OrderedMap<K, V> {
    private Comparator<K> cmp;
    private PositionList<Entry<K, V>> elements;

    /**
     * Notas : 
     * Las excepciones de Illegal argument por key null son realizadas por el metodo
     * auxiliar findkeyplace
     *
     * Tras contactar a Guillermo Roman por el error del tester de Emtryimpl local y el de aedlib
     * Al ser los dos diferentes y no poder entregar por ello, ha mencionado que mi entrega 
     * seguira siendo valida. (Pese a poder entregarla solo ahora)
     */

    /* Acabar de codificar el constructor */
    public PositionListOrderedMap(Comparator<K> cmp) {
        this.cmp = cmp;
        elements = new NodePositionList<Entry<K, V>>();
    }

    /* Ejemplo de un posible método auxiliar: */

    /**
     * Returns the Position<> pointer with Key value "key"
     * 
     * @throws IllegalArgumentException if key is null
     * @param key
     * @return Position object with key "key" or the next in order. Null otherwise
     */
    private Position<Entry<K, V>> findKeyPlace(K key) {
        if (key == null)
            throw new IllegalArgumentException();
        Position<Entry<K, V>> pos = elements.first();
        while (pos != null && cmp.compare(key, pos.element().getKey()) > 0) {
            pos = elements.next(pos);
        }
        return pos;
    }

    /* Comprueba si la Entry en la posicion contiene la key */
    private boolean isTheEntry(Position<Entry<K, V>> pos, K key) {
        return pos != null && pos.element().getKey().equals(key);
    }

    /* Podéis añadir más métodos auxiliares */
    public boolean containsKey(K key) {
        Position<Entry<K, V>> pos = findKeyPlace(key);
        return isTheEntry(pos, key);
    }

    public V get(K key) {
        Position<Entry<K, V>> pos = findKeyPlace(key);
        return isTheEntry(pos, key) ? pos.element().getValue() : null;
    }

    public V put(K key, V value) {
        Position<Entry<K, V>> pos = findKeyPlace(key);
        V val = null;
        if (isTheEntry(pos, key))
            val = pos.element().getValue();
        Entry<K,V> entry = new EntryImpl<>(key, value);
        if (elements.isEmpty())
            elements.addFirst(entry);
        else if (pos == null)
            elements.addLast(entry);
        else
            elements.addBefore(pos, entry);
        if (isTheEntry(pos, key))
            elements.remove(pos);
        return val;
    }

    public V remove(K key) {
        Position<Entry<K, V>> pos = findKeyPlace(key);
        V val = null;
        if (isTheEntry(pos, key)) {
            val = pos.element().getValue();
            elements.remove(pos);
        }
        return val;
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public Entry<K, V> floorEntry(K key) {
        Position<Entry<K, V>> pos = findKeyPlace(key);
        if (isTheEntry(pos, key))
            pos.element();
        else if (elements.isEmpty())
            pos = null;
        else if (pos != null)
            pos = elements.prev(pos);
        else
            pos = elements.last();
        return pos == null ? null : pos.element();
    }

    public Entry<K, V> ceilingEntry(K key) {
        Position<Entry<K, V>> pos = findKeyPlace(key);
        if (isTheEntry(pos, key))
            return pos.element();
        return pos == null ? null : pos.element();
    }

    public Iterable<K> keys() {
        NodePositionList<K> list = new NodePositionList<>();
        Position<Entry<K, V>> pos = elements.first();
        while (pos != null) {
            list.addLast(pos.element().getKey());
            pos = elements.next(pos);
        }
        return list;
    }

    public String toString() {
        return elements.toString();
    }

}
