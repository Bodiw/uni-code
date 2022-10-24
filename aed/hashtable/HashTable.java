package aed.hashtable;

import java.util.Iterator;

import es.upm.aedlib.Entry;
import es.upm.aedlib.EntryImpl;
import es.upm.aedlib.map.Map;
import es.upm.aedlib.InvalidKeyException;
import es.upm.aedlib.indexedlist.ArrayIndexedList;

/**
 * A hash table implementing using open addressing to handle key collisions.
 */
public class HashTable<K, V> implements Map<K, V> {
  Entry<K, V>[] buckets;
  int size;

  public HashTable(int initialSize) {
    this.buckets = createArray(initialSize);
    this.size = 0;
  }

  /**
   * Add here the method necessary to implement the Map api, and any auxilliary
   * methods you deem convient.
   */

  // Examples of auxilliary methods: IT IS NOT REQUIRED TO IMPLEMENT THEM

  @SuppressWarnings("unchecked")
  private Entry<K, V>[] createArray(int size) {
    Entry<K, V>[] buckets = (Entry<K, V>[]) new Entry[size];
    return buckets;
  }

  /**
   * 
   * @param obj     key to compare
   * @param contain checks if the desired bucket has to contain the key or not
   * @return index of the key, based on exact's value
   */
  private int index(Object obj, boolean contain) {
    if (obj == null)
      throw new InvalidKeyException();
    int pos = Math.abs(obj.hashCode()) % buckets.length;
    // in case the first hit lands, it prevents 2 for's iterations
    if (buckets[pos] != null && buckets[pos].getKey().equals(obj))
      return pos;
    // finding a new bucket
    int index = -1;
    boolean found = false;
    for (int i = 0; i < buckets.length && !found; i++) {
      if (pos == buckets.length)
        pos = 0;
      if (buckets[pos] == null || buckets[pos].getKey().equals(obj)) {
        index = buckets[pos] == null && contain ? -1 : pos;
        found = true;
      }
      pos++;
    }
    return index;
  }

  // Doubles the size of the bucket array, and inserts all entries present
  // in the old bucket array into the new bucket array, in their correct
  // places. Remember that the index of an entry will likely change in
  // the new array, as the size of the array changes.
  private void rehash() {
    Entry<K, V>[] old = buckets;
    buckets = createArray(2 * buckets.length);
    size = 0;
    for (Entry<K, V> entry : old)
      if (entry != null)
        // rehashing can have new optimal positions,need to re-add all
        put(entry.getKey(), entry.getValue());
  }

  public Iterator<Entry<K, V>> iterator() {
    return entries().iterator();
  }

  public boolean containsKey(Object arg0) throws InvalidKeyException {
    // busqueda exacta
    int index = index(arg0, true);
    return index != -1;
  }

  // repito algo de codigo a cambio de no hacer dos recorridos
  public Iterable<Entry<K, V>> entries() {
    ArrayIndexedList<Entry<K, V>> entries = new ArrayIndexedList<>();
    for (Entry<K, V> entry : buckets) {
      if (entry != null)
        entries.add(entries.size(), entry);
    }
    return entries;
  }

  public V get(K arg0) throws InvalidKeyException {
    // busqueda exacta
    int index = index(arg0, true);
    return index >= 0 ? buckets[index].getValue() : null;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public Iterable<K> keys() {
    ArrayIndexedList<K> keys = new ArrayIndexedList<>();
    for (Entry<K, V> entry : buckets) {
      if (entry != null)
        keys.add(keys.size(), entry.getKey());
    }
    return keys;
  }

  public V put(K arg0, V arg1) throws InvalidKeyException {
    // extraer valor
    int index = index(arg0, false);
    V val = index >= 0 && buckets[index] != null && buckets[index].getKey().equals(arg0) ? buckets[index].getValue()
        : null;
    // si la lista esta llena
    if (size == buckets.length && val == null) {
      rehash();
      index = index(arg0, false);
    }
    // tiene OOB protection ya que siempre habra un hueco valido
    buckets[index] = new EntryImpl<K, V>(arg0, arg1);
    if (val == null)
      size++;
    return val;
  }

  public V remove(K arg0) throws InvalidKeyException {
    // eliminacion
    int index = index(arg0, true);
    // no esta en el hashmap
    if (index == -1)
      return null;
    V val = buckets[index].getValue();
    buckets[index] = null;
    size--;
    // compactado
    int pos = index;
    for (int i = 0; i < buckets.length; i++) {
      if (pos >= buckets.length)
        pos = 0;
      if (buckets[pos] != null && index(buckets[pos].getKey(), false) == index) {
        buckets[index] = buckets[pos];
        buckets[pos] = null;
        index = pos;
      }
      pos++;
    }
    return val;
  }

  public int size() {
    return size;
  }

}
