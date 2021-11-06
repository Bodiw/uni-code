package aed.loops;

public class Utils {

  /**
   *  El metodo funciona como un contador, iniciando a 0 ante su ejecucion.
   *  Luego el for() procede a mirar el primer elemento e inicia un contador local para
   *  ver cuantos consecutivos son == a elem.
   *  Si a[0] no es == elem, el for() hace i++ y pasa al siguiente elemento del array,
   *  hasta llegar a un Integer que sea "elem". Si no encuentra el return es el valor inicial 0.
   * 
   *  Si la posicion comparada tiene valor de elem, inicia el while y por cada posicion que 
   *  tenga valor elem , aumenta el contador de esta iteracion e i++ para ir a la siguiente
   *  posicion y volver a ejecutar el while. 
   * 
   *  Terminado el while, o saltado, se compara el local con el absoluto y si es mayor se le 
   *  asigna su valor, que será transmitido en el return.
   * 
   */
  public static int maxNumRepeated(Integer[] a, Integer elem) {
    int counterMax = 0;
    for (int i = 0; i < a.length; i++) {
      int localCounter = 0;
      while (i < a.length && a[i].compareTo(elem) == 0) {
        localCounter++;
        i++;
      }
      if (localCounter > counterMax )
        counterMax = localCounter ;
    }
    return counterMax;
  }
}
