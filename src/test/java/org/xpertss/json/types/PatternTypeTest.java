package org.xpertss.json.types;

import org.junit.Before;
import org.junit.Test;
import xpertss.json.JSONObject;
import xpertss.json.JSONObjectBuilder;
import xpertss.json.MarshallingException;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class PatternTypeTest {

   private Pattern pattern;
   private PatternType objectUnderTest;

   @Before
   public void setUp() {
      pattern = Pattern.compile("\\s+");
      objectUnderTest = new PatternType();
   }


   @Test
   public void testSimplePattern() {
      JSONObject encoded = objectUnderTest.marshall(pattern);
      Pattern decoded = objectUnderTest.unmarshall(encoded);
      assertEquals(pattern.pattern(), decoded.pattern());
      assertEquals(pattern.flags(), decoded.flags());
   }

   @Test
   public void testDecodeWithNoFlags() {
      JSONObjectBuilder builder = new JSONObjectBuilder();
      builder.add("pattern", "\\s+");

      JSONObject encoded = builder.build();
      Pattern decoded = objectUnderTest.unmarshall(encoded);
      assertEquals(pattern.pattern(), decoded.pattern());
      assertEquals(pattern.flags(), decoded.flags());
   }

   @Test(expected = MarshallingException.class)
   public void testDecodeWithNoPattern() {
      JSONObjectBuilder builder = new JSONObjectBuilder();
      builder.add("flags", 0);

      JSONObject encoded = builder.build();
      objectUnderTest.unmarshall(encoded);
   }

}