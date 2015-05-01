package xpertss.json.desc;

import org.junit.Test;

import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

import static org.junit.Assert.*;
import static xpertss.json.desc.MapType.*;

public class MapTypeTest {

   @Test
   public void testDefaultMaps()
   {
      assertSame(LinkedHashMap, MapType.fromClass(Map.class));
      assertSame(TreeMap, MapType.fromClass(SortedMap.class));
      assertSame(TreeMap, MapType.fromClass(NavigableMap.class));
      assertSame(ConcurrentHashMap, MapType.fromClass(ConcurrentMap.class));
      assertSame(ConcurrentSkipListMap, MapType.fromClass(ConcurrentNavigableMap.class));
   }

   @Test
   public void testSetDefaultMaps()
   {
      MapType.setDefaultMapType(MapType.HashMap);
      assertSame(HashMap, MapType.fromClass(Map.class));

      MapType.setDefaultMapType(MapType.ConcurrentHashMap);
      assertSame(ConcurrentHashMap, MapType.fromClass(Map.class));

      MapType.setDefaultMapType(MapType.WeakHashMap);
      assertSame(WeakHashMap, MapType.fromClass(Map.class));

      MapType.setDefaultMapType(MapType.IdentityHashMap);
      assertSame(IdentityHashMap, MapType.fromClass(Map.class));

      MapType.setDefaultNavigableMapType(MapType.ConcurrentSkipListMap);
      assertSame(ConcurrentSkipListMap, MapType.fromClass(NavigableMap.class));
      assertSame(TreeMap, MapType.fromClass(SortedMap.class));

      MapType.setDefaultSortedMapType(MapType.ConcurrentSkipListMap);
      assertSame(ConcurrentSkipListMap, MapType.fromClass(NavigableMap.class));
      assertSame(ConcurrentSkipListMap, MapType.fromClass(SortedMap.class));


      MapType.setDefaultConcurrentMapType(MapType.ConcurrentSkipListMap);
      assertSame(ConcurrentSkipListMap, MapType.fromClass(ConcurrentMap.class));
      assertSame(ConcurrentSkipListMap, MapType.fromClass(ConcurrentNavigableMap.class));


      // Reset
      MapType.setDefaultMapType(MapType.LinkedHashMap);
      MapType.setDefaultNavigableMapType(MapType.TreeMap);
      MapType.setDefaultSortedMapType(MapType.TreeMap);
      MapType.setDefaultConcurrentMapType(MapType.ConcurrentHashMap);
   }



   @Test(expected = NullPointerException.class)
   public void testSetDefaultMapNullType()
   {
      MapType.setDefaultMapType(null);
   }




   @Test(expected = IllegalArgumentException.class)
   public void testSetDefaultSortedMapToHashMap()
   {
      MapType.setDefaultSortedMapType(MapType.HashMap);
   }

   @Test(expected = NullPointerException.class)
   public void testSetDefaultSortedMapToNull()
   {
      MapType.setDefaultSortedMapType(null);
   }



   @Test(expected = IllegalArgumentException.class)
   public void testSetDefaultNavigableMapToHashMap()
   {
      MapType.setDefaultNavigableMapType(MapType.HashMap);
   }

   @Test(expected = NullPointerException.class)
   public void testSetDefaultNavigableMapToNull()
   {
      MapType.setDefaultNavigableMapType(null);
   }



   @Test(expected = IllegalArgumentException.class)
   public void testSetDefaultConcurrentMapToHashMap()
   {
      MapType.setDefaultConcurrentMapType(MapType.HashMap);
   }

   @Test(expected = NullPointerException.class)
   public void testSetDefaultConcurrentMapToNull()
   {
      MapType.setDefaultConcurrentMapType(null);
   }




   @Test(expected = IllegalArgumentException.class)
   public void testSetDefaultConcurrentNavigableMapToHashMap()
   {
      MapType.setDefaultConcurrentNavigableMapType(MapType.HashMap);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testSetDefaultConcurrentNavigableMapToConcurrentHashMap()
   {
      MapType.setDefaultConcurrentNavigableMapType(MapType.ConcurrentHashMap);
   }

   @Test(expected = NullPointerException.class)
   public void testSetDefaultConcurrentNavigableMapToNull()
   {
      MapType.setDefaultConcurrentNavigableMapType(null);
   }

}