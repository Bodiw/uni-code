package aed.indexedlist;
import es.upm.aedlib.indexedlist.*;

  /**
   *  Lo he intentado realizar con "For Each" pero me ha soltado unos errores de 
   *  concurrent modification muy feos, me gustaría incluso a lo mejor verlo en
   *  clase o una tutoría.
   * 
   *  Ya que no puedo modificar los datos que me proporcionan, mi unico comparador
   *  para saber si dos objetos son el mismo es equals.
   *  Con una "lista" auxiliar donde guardar los objetos no iterados y comprobar
   *  si para todo objeto de "l" este esta contenido en "lista" y proceder a 
   *  incorporarlo o no en funcion de ello.
   * 
   *  El return es la lista auxiliar con los elementos únicos, en orden de "l".
   *  De verdad que me hubiese gustado poder entregarlo con for each's. 
   * 
   */
public class Utils {
  public static <E> IndexedList<E> deleteRepeated(IndexedList<E> l) {
    IndexedList<E> list = new ArrayIndexedList<>() ;
    if (!l.isEmpty()) list.add(0, l.get(0));
    for (int i=0 ; i< l.size() ; i++){
      boolean isRepeated = false ;
      for (int j =0 ; j<list.size() && !isRepeated ; j++)
        if (l.get(i).equals(list.get(j))){
          isRepeated = true;
        }
      if (!isRepeated) list.add(list.size(), l.get(i));
    }
    return list;
  }
}
