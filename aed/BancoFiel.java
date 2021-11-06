/**
 *  Autor : Bohdan Knyshoyid
 * 
 *  -Los metodos que ya estan comentados en sus respectivas interfaces no considero necesario comentarlos
 *    a excepcion de algun pequeno detalle que considere poco legible en su implementacion.
 * 
 *  -Los metodos que contengan el throw CuentaNoExisteExc obtienen la excepcion del auxiliar getCuentaByIdBinary()
 * 
 */
package aed.bancofiel;

import java.util.Comparator;

import es.upm.aedlib.indexedlist.IndexedList;
import es.upm.aedlib.indexedlist.ArrayIndexedList;

/**
 * Implements the code for the bank application. Implements the client and the
 * "gestor" interfaces.
 */
public class BancoFiel implements ClienteBanco, GestorBanco {

  // NOTAD. No se deberia cambiar esta declaracion.
  public IndexedList<Cuenta> cuentas;

  // NOTAD. No se deberia cambiar esta constructor.
  public BancoFiel() {
    this.cuentas = new ArrayIndexedList<Cuenta>();
  }

  // ----------------------------------------------------------------------
  // Anadir metodos aqui ...

  // NOTA : El while comprueba primero si el contador se sale del tamano de
  // la lista para reconocer un elemento mayor que todos o crear el
  // primer elemento. Compare comprueba si el elemento c es mayor al
  // de la posicion actual. Si no lo es, entra en la lista en su lugar.
  public IndexedList<Cuenta> getCuentasOrdenadas(Comparator<Cuenta> cmp) {
    IndexedList<Cuenta> ordenadas = new ArrayIndexedList<>();
    for (Cuenta c : cuentas) {
      int pos = 0;
      while (pos < ordenadas.size() && cmp.compare(c, ordenadas.get(pos)) > 0)
        pos++;
      ordenadas.add(pos, c);
    }
    return ordenadas;
  }

  // Al crear una cuenta es insertada en orden bajo criterio de comparacion de
  // strings para poder realizar una busqueda binaria con getCuentaByIdBinary()
  public String crearCuenta(String dni, int saldoIncial) {
    Cuenta cuenta = new Cuenta(dni, saldoIncial);
    int pos = 0;
    while (pos < cuentas.size() && cuenta.getId().compareTo(cuentas.get(pos).getId()) > 0)
      pos++;
    this.cuentas.add(pos, cuenta);
    return cuenta.getId();
  }

  public void borrarCuenta(String id) throws CuentaNoExisteExc, CuentaNoVaciaExc {
    Cuenta cuenta = getCuentaByIdBinary(id);
    if (!(cuenta.getSaldo() == 0))
      throw new CuentaNoVaciaExc();
    cuentas.remove(cuenta);
  }

  public int ingresarDinero(String id, int cantidad) throws CuentaNoExisteExc {
    Cuenta cuenta = getCuentaByIdBinary(id);
    cuenta.ingresar(cantidad);
    return cuenta.getSaldo();
  }

  public int retirarDinero(String id, int cantidad) throws CuentaNoExisteExc, InsuficienteSaldoExc {
    Cuenta cuenta = getCuentaByIdBinary(id);
    cuenta.retirar(cantidad);
    return cuenta.getSaldo();
  }

  public int consultarSaldo(String id) throws CuentaNoExisteExc {
    return getCuentaByIdBinary(id).getSaldo();
  }

  public void hacerTransferencia(String idFrom, String idTo, int cantidad)
      throws CuentaNoExisteExc, InsuficienteSaldoExc {
    Cuenta origen = getCuentaByIdBinary(idFrom); // Evita que si destino no existe,
    Cuenta destino = getCuentaByIdBinary(idTo); // getCuentaById(origen).retirar(cantidad) quite el dinero sin enviarlo
    origen.retirar(cantidad);
    destino.ingresar(cantidad);
  }

  // Buscar una cuenta de id "%dni%/1" era tentador pero en caso de cambiar la
  // sintaxis de los id este metodo dejaria de
  // Ser generico para cualquier sintaxis de dni. Por ello, ya que las cuentas
  // estan ordenadas en su creacion, elijo
  // Recorrer la lista hasta hallar la primera cuenta con igual dni, y ahi uso
  // otro while para recoger todos los id's del dni
  public IndexedList<String> getIdCuentas(String dni) {
    IndexedList<String> idCuentas = new ArrayIndexedList<>();
    int pos = 0;
    while (pos < cuentas.size() && cuentas.get(pos).getDNI().compareTo(dni) < 0)
      pos++;
    while (pos < cuentas.size() && cuentas.get(pos).getDNI().compareTo(dni) == 0) {
      idCuentas.add(idCuentas.size(), cuentas.get(pos).getId());
      pos++;
    }
    return idCuentas;
  }

  // Mismo planteamiento que el anterior, ya que solo dos metodos usan esta
  // formula no necesito aun realizar un metodo auxiliar para el "doble while"
  public int getSaldoCuentas(String dni) {
    int saldo = 0;
    int pos = 0;
    while (pos < cuentas.size() && cuentas.get(pos).getDNI().compareTo(dni) < 0)
      pos++;
    while (pos < cuentas.size() && cuentas.get(pos).getDNI().compareTo(dni) == 0) {
      saldo += cuentas.get(pos).getSaldo();
      pos++;
    }
    return saldo;
  }

  /**
   * Auxiliar method. Returns the account with Id "id" using binary search
   * 
   * @return "Cuenta" with Id "id"
   * @throws CuentaNoExisteExc if there is no Cuenta with Id "id"
   */
  private Cuenta getCuentaByIdBinary(String id) throws CuentaNoExisteExc {
    Cuenta cuenta = null;
    int first = 0;
    int last = cuentas.size() - 1;
    int mid = (first + last) / 2;
    while (first <= last) {
      if (cuentas.get(mid).getId().compareTo(id) < 0) {
        first = mid + 1;
      } else if (cuentas.get(mid).getId().compareTo(id) == 0) {
        cuenta = cuentas.get(mid);
        break;
      } else {
        last = mid - 1;
      }
      mid = (first + last) / 2;
    }
    if (cuenta == null)
      throw new CuentaNoExisteExc();
    return cuenta;
  }

  // ----------------------------------------------------------------------
  // NOTAD. No se deberia cambiar este metodo.
  public String toString() {
    return "banco";
  }
}
