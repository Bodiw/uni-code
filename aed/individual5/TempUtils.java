package aed.individual5;

import es.upm.aedlib.Pair;
import es.upm.aedlib.map.*;

public class TempUtils {
  public static Map<String, Integer> maxTemperatures(long startTime, long endTime, TempData[] tempData) {
    HashTableMap<String, Integer> map = new HashTableMap<>();
    for (TempData t : tempData) {
      Integer max = map.get(t.getLocation());
      if ((startTime <= t.getTime() && t.getTime() <= endTime) && (max == null || max < t.getTemperature()))
        map.put(t.getLocation(), t.getTemperature());
    }
    return map;
  }
  // he hecho varios tests de velocidad, y esta es la iteracion mas rapida
  public static Pair<String, Integer> maxTemperatureInComunidad(long startTime, long endTime, String region,
      TempData[] tempData, Map<String, String> comunidadMap) {
    Pair<String, Integer> res = null;
    for (TempData t : tempData)
      if (comunidadMap.get(t.getLocation()).equals(region))
        if ((startTime <= t.getTime() && t.getTime() <= endTime)
            && (res == null || res.getRight() <= t.getTemperature()))
          res = new Pair<>(t.getLocation(), t.getTemperature());
    return res;
  }
}
