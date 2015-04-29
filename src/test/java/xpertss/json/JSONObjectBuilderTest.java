package xpertss.json;

import org.junit.Test;

import static org.junit.Assert.*;
import static xpertss.json.JSON.*;

public class JSONObjectBuilderTest {


   @Test
   public void testBasicObject()
   {
      JSONObjectBuilder builder = objectBuilder();
      builder.add("address", "joe@joe.com")
            .add("type", "Work");
      JSONObject built = builder.build();

      JSONObject object = object(string("address"), string("joe@joe.com"),
                                 string("type"), string("Work"));

      assertEquals(object, built);
   }


   @Test
   public void testSubObject()
   {
      JSONObjectBuilder builder = objectBuilder();
      builder.add("email", objectBuilder()
            .add("address", "joe@joe.com")
            .add("type", "Work"));
      JSONObject built = builder.build();

      JSONObject object = object(string("email"), object(string("address"), string("joe@joe.com"),
                                                         string("type"), string("Work")));

      assertEquals(object, built);
   }


   @Test
   public void testMultipleTypes()
   {
      JSONObjectBuilder builder = objectBuilder();
      builder.add("address", "joe@joe.com")
            .add("age", 22)
            .add("employed", true);

      JSONObject built = builder.build();

      JSONObject object = object(string("address"), string("joe@joe.com"),
                                 string("age"), number(22),
                                 string("employed"), TRUE);

      assertEquals(object, built);
   }


}