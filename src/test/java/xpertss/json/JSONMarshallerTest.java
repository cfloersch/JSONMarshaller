/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 2:55 PM
 * Copyright Manheim online
 */
package xpertss.json;


import org.junit.Test;
import xpertss.json.book.Author;
import xpertss.json.book.Book;
import xpertss.json.book.Email;
import xpertss.json.contact.Contact;
import xpertss.json.garage.*;
import xpertss.json.generic.BookSupplier;
import xpertss.json.generic.Business;
import xpertss.json.generic.Pair;
import xpertss.json.generic.Supplier;
import xpertss.json.literal.BooleanLiteral;
import xpertss.json.literal.ByteLiteral;
import xpertss.json.literal.CharLiteral;
import xpertss.json.literal.DoubleLiteral;
import xpertss.json.literal.FloatLiteral;
import xpertss.json.literal.IntLiteral;
import xpertss.json.literal.LongLiteral;
import xpertss.json.literal.ShortLiteral;
import xpertss.json.msg.UserType;
import xpertss.json.object.*;
import xpertss.json.poly.FailureWrapper;
import xpertss.json.poly.GenericProducer;
import xpertss.json.poly.Producer;
import xpertss.json.poly.StringProducer;
import xpertss.json.poly.SuccessWrapper;
import xpertss.json.roll.*;
import xpertss.json.user.UserTypes;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Currency;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import static xpertss.json.JSON.*;
import static org.junit.Assert.*;

public class JSONMarshallerTest {



   @Test
   public void testSimpleEntity()
   {
      JSONMarshaller<Author> marshaller = JSONMarshaller.create(Author.class);
      JSONObject obj = object(string("firstName"), string("Chris"),
                                    string("lastName"), string("Singer"));
      Author author = marshaller.unmarshall(obj);
      assertEquals("Chris", author.getFirstName());
      assertEquals("Singer", author.getLastName());
      assertEquals(obj, marshaller.marshall(author));
   }


    @Test
    public void testSimpleEntityNullLastName()
    {
        JSONMarshaller<Author> marshaller = JSONMarshaller.create(Author.class);
        Author author = new Author("Chris", null);

        JSONObject encoded = marshaller.marshall(author);
        assertEquals(string("Chris"), encoded.get(string("firstName")));
        assertEquals(NULL, encoded.get(string("lastName")));

        assertEquals(author, marshaller.unmarshall(encoded));
    }


    @Test(expected = MarshallingException.class)
    public void testSimpleEntityNoLastName()
    {
        JSONMarshaller<Author> marshaller = JSONMarshaller.create(Author.class);
        // Object missing lastName
        JSONObject obj = object(string("firstName"), string("Chris"));
        marshaller.unmarshall(obj);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonEntity()
    {
        JSONMarshaller.create(String.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGenericTopLevelEntity()
    {
        JSONMarshaller.create(Supplier.class);
    }




    @Test
    public void testComplexEntity()
    {
        JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

        Book object = createBook();
        JSONObject encoded = marshaller.marshall(object);

        assertEquals(string("Effective Communication"), encoded.get(string("title")));
        assertEquals(string("978-0-7382-0287-7"), encoded.get(string("isbn")));
        assertEquals(string("http://www.google.com/"), encoded.get(string("uri")));

       assertNotNull(encoded.get(string("email")));

        Book decoded = marshaller.unmarshall(encoded);
        assertEquals(object, decoded);
    }


    @Test
    public void testGenericSuperEntity()
    {
        JSONMarshaller<BookSupplier> marshaller = JSONMarshaller.create(BookSupplier.class);

        BookSupplier object = new BookSupplier(createBook());
        JSONObject encoded = marshaller.marshall(object);
        assertEquals(1,encoded.size());
        BookSupplier decoded = marshaller.unmarshall(encoded);
        assertEquals(object, decoded);
    }



    @Test
    public void testGenericConcreteEntity()
    {
        JSONMarshaller<Business> marshaller = JSONMarshaller.create(Business.class);

        Business object = new Business("Borders", createBook());
        JSONObject encoded = marshaller.marshall(object);
        assertEquals(2,encoded.size());
        Business decoded = marshaller.unmarshall(encoded);
        assertEquals(object, decoded);
    }



   @Test
   public void testGenericSuperClass()
   {
      JSONMarshaller<Pair> marshaller = JSONMarshaller.create(Pair.class);

      Pair object = new Pair("John", "Jane");
      JSONObject encoded = marshaller.marshall(object);
      assertEquals(2,encoded.size());
      Pair decoded = marshaller.unmarshall(encoded);
      assertEquals(object, decoded);
   }





    @Test
    public void testSimplePolymorphicEntityInterfaceBased()
    {
        JSONMarshaller<Garage> marshaller = JSONMarshaller.create(Garage.class);

        Garage object = new Garage();
        object.add(new Motorcycle());
        object.add(new Car());

        JSONObject encoded = marshaller.marshall(object);
        assertEquals(1, encoded.size());
        JSONArray array = (JSONArray) encoded.get(string("vehicles"));
        assertEquals(2, array.size());

        Garage decoded = marshaller.unmarshall(encoded);
        assertEquals(object, decoded);
    }

    @Test
    public void testTopLevelPolymorphicEntityInterfaceBased()
    {
        JSONMarshaller<Vehicle> marshaller = JSONMarshaller.create(Vehicle.class);

        Car object = new Car();
        JSONObject encoded = marshaller.marshall(object);
        assertEquals(2, encoded.size());
        assertEquals(string("Car"), encoded.get(string("vehicletype")));

        Vehicle decoded = marshaller.unmarshall(encoded);
        assertTrue(decoded instanceof Car);
        assertEquals(object, decoded);
    }

    @Test
    public void testSimplePolymorphicEntityAbsractClassBased()
    {
        JSONMarshaller<Rollcall> marshaller = JSONMarshaller.create(Rollcall.class);

        Rollcall object = new Rollcall();
        object.add(new Student("Chris Singer", "csinger@hot.com", Grade.Senior));
        object.add(new Student("John Doe", "jdoe@hot.com", Grade.Freshman));
        object.add(new Student("Frank Lanza", "flanza@hot.com", Grade.Junior));
        object.add(new Teacher("Bill McKenzie", "bmckenzie@gnu.edu", 15));
        object.add(new Teacher("Lisa Gwine", "lgwine@gnu.edu", 8));

        JSONObject encoded = marshaller.marshall(object);
        assertEquals(1, encoded.size());
        JSONArray array = (JSONArray) encoded.get(string("people"));
        assertEquals(5, array.size());

        Rollcall decoded = marshaller.unmarshall(encoded);
        assertEquals(object, decoded);
    }

    @Test
    public void testTopLevelPolymorphicEntityAbstractClassBased()
    {
        JSONMarshaller<Person> marshaller = JSONMarshaller.create(Person.class);

        Teacher object = new Teacher("Julie Hill", "jhill@cherokee.edu", 33);
        JSONObject encoded = marshaller.marshall(object);
        assertEquals(4, encoded.size());
        assertEquals(string("Teacher"), encoded.get(string("person")));

        Person decoded = marshaller.unmarshall(encoded);
        assertTrue(decoded instanceof Teacher);
        assertEquals(object, decoded);
    }


   @Test
   public void testGenericPolymorphicEntity()
   {
      JSONMarshaller<SuccessWrapper> marshaller = JSONMarshaller.create(SuccessWrapper.class);

      SuccessWrapper object = new SuccessWrapper();
      object.addProducer(new StringProducer("Hello"));
      object.addProducer(new GenericProducer<String>("Goodbye"));

      JSONObject encoded = marshaller.marshall(object);
      assertEquals(1, encoded.size());
      assertTrue(encoded.get(string("producers")) instanceof JSONArray);
      JSONArray array = (JSONArray) encoded.get(string("producers"));
      assertEquals(2, array.size());

      SuccessWrapper decoded = marshaller.unmarshall(encoded);
      assertEquals(object, decoded);
   }

   @Test(expected = MarshallingException.class)
   public void testPolymorphicWithMismatchedGenerics()
   {
      JSONMarshaller<FailureWrapper> marshaller = JSONMarshaller.create(FailureWrapper.class);

      JSONArray array = array(object(string("item"),string("Hello"),string("type"),string("string")),
                              object(string("item"),string("Goodbye"),string("type"),string("generic")));
      JSONObject encoded = object(string("producers"), array);

      FailureWrapper failure = marshaller.unmarshall(encoded);
   }


   @Test
   public void testPolymorphicWithDefinedGenerics()
   {
      JSONMarshaller<StringProducer> marshaller = JSONMarshaller.create(StringProducer.class);

      StringProducer object = new StringProducer("Hello");
      JSONObject encoded = marshaller.marshall(object);
      assertEquals(1, encoded.size());
      assertEquals(string("Hello"), encoded.get(string("item")));

      Producer<String> decoded = marshaller.unmarshall(encoded);
      assertEquals(object, decoded);
   }



   @Test(expected = IllegalArgumentException.class)
   public void testPolymorphicWithGenericVariables()
   {
      JSONMarshaller<Producer> marshaller = JSONMarshaller.create(Producer.class);
   }










   @Test
   public void testBooleanLiteral()
   {
      JSONMarshaller<BooleanLiteral> marshaller = JSONMarshaller.create(BooleanLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      BooleanLiteral literal = marshaller.unmarshall(encoded);
      assertTrue(literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testBooleanLiteralNull()
   {
      JSONMarshaller<BooleanLiteral> marshaller = JSONMarshaller.create(BooleanLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testBooleanLiteralNumber()
   {
      JSONMarshaller<BooleanLiteral> marshaller = JSONMarshaller.create(BooleanLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testBooleanLiteralString()
   {
      JSONMarshaller<BooleanLiteral> marshaller = JSONMarshaller.create(BooleanLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testBooleanObject()
   {
      JSONMarshaller<BooleanObject> marshaller = JSONMarshaller.create(BooleanObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      BooleanObject object = marshaller.unmarshall(encoded);
      assertTrue(object.value());

   }

   @Test
   public void testBooleanObjectNull()
   {
      JSONMarshaller<BooleanObject> marshaller = JSONMarshaller.create(BooleanObject.class);

      JSONObject encoded = object(string("value"), NULL);
      BooleanObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testBooleanObjectNumber()
   {
      JSONMarshaller<BooleanObject> marshaller = JSONMarshaller.create(BooleanObject.class);

      JSONObject encoded = object(string("value"), number(2));
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testBooleanObjectString()
   {
      JSONMarshaller<BooleanObject> marshaller = JSONMarshaller.create(BooleanObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }






   @Test
   public void testByteLiteral()
   {
      JSONMarshaller<ByteLiteral> marshaller = JSONMarshaller.create(ByteLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      ByteLiteral literal = marshaller.unmarshall(encoded);
      assertEquals((byte) (2 & 0xff), literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testByteLiteralNull()
   {
      JSONMarshaller<ByteLiteral> marshaller = JSONMarshaller.create(ByteLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testByteLiteralBoolean()
   {
      JSONMarshaller<ByteLiteral> marshaller = JSONMarshaller.create(ByteLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testByteLiteralString()
   {
      JSONMarshaller<ByteLiteral> marshaller = JSONMarshaller.create(ByteLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testByteObject()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);

      JSONObject encoded = object(string("value"), number(2));
      ByteObject object = marshaller.unmarshall(encoded);
      assertEquals(Byte.valueOf((byte) (2 & 0xff)), object.value());

   }

   @Test
   public void testByteObjectNull()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);

      JSONObject encoded = object(string("value"), NULL);
      ByteObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testByteObjectBoolean()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testByteObjectString()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }








   @Test
   public void testShortLiteral()
   {
      JSONMarshaller<ShortLiteral> marshaller = JSONMarshaller.create(ShortLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      ShortLiteral literal = marshaller.unmarshall(encoded);
      assertEquals((short) (2 & 0xffff), literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testShortLiteralNull()
   {
      JSONMarshaller<ShortLiteral> marshaller = JSONMarshaller.create(ShortLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testShortLiteralBoolean()
   {
      JSONMarshaller<ShortLiteral> marshaller = JSONMarshaller.create(ShortLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testShortLiteralString()
   {
      JSONMarshaller<ShortLiteral> marshaller = JSONMarshaller.create(ShortLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testShortObject()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);

      JSONObject encoded = object(string("value"), number(2));
      ShortObject object = marshaller.unmarshall(encoded);
      assertEquals(Short.valueOf((short) (2 & 0xffff)), object.value());

   }

   @Test
   public void testShortObjectNull()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);

      JSONObject encoded = object(string("value"), NULL);
      ShortObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testShortObjectBoolean()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testShortObjectString()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }





   @Test
   public void testIntLiteral()
   {
      JSONMarshaller<IntLiteral> marshaller = JSONMarshaller.create(IntLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      IntLiteral literal = marshaller.unmarshall(encoded);
      assertEquals(2, literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testIntLiteralNull()
   {
      JSONMarshaller<IntLiteral> marshaller = JSONMarshaller.create(IntLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testIntLiteralBoolean()
   {
      JSONMarshaller<IntLiteral> marshaller = JSONMarshaller.create(IntLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testIntLiteralString()
   {
      JSONMarshaller<IntLiteral> marshaller = JSONMarshaller.create(IntLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testIntegerObject()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);

      JSONObject encoded = object(string("value"), number(2));
      IntegerObject object = marshaller.unmarshall(encoded);
      assertEquals(Integer.valueOf(2), object.value());

   }

   @Test
   public void testIntegerObjectNull()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);

      JSONObject encoded = object(string("value"), NULL);
      IntegerObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testIntegerObjectBoolean()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testIntegerObjectString()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }





   @Test
   public void testLongLiteral()
   {
      JSONMarshaller<LongLiteral> marshaller = JSONMarshaller.create(LongLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      LongLiteral literal = marshaller.unmarshall(encoded);
      assertEquals(2L, literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testLongLiteralNull()
   {
      JSONMarshaller<LongLiteral> marshaller = JSONMarshaller.create(LongLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testLongLiteralBoolean()
   {
      JSONMarshaller<LongLiteral> marshaller = JSONMarshaller.create(LongLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testLongLiteralString()
   {
      JSONMarshaller<LongLiteral> marshaller = JSONMarshaller.create(LongLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testLongObject()
   {
      JSONMarshaller<LongObject> marshaller = JSONMarshaller.create(LongObject.class);

      JSONObject encoded = object(string("value"), number(2));
      LongObject object = marshaller.unmarshall(encoded);
      assertEquals(Long.valueOf(2L), object.value());

   }

   @Test
   public void testLongObjectNull()
   {
      JSONMarshaller<LongObject> marshaller = JSONMarshaller.create(LongObject.class);

      JSONObject encoded = object(string("value"), NULL);
      LongObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testLongObjectBoolean()
   {
      JSONMarshaller<LongObject> marshaller = JSONMarshaller.create(LongObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testLongObjectString()
   {
      JSONMarshaller<LongObject> marshaller = JSONMarshaller.create(LongObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }





   @Test
   public void testBigIntegerObject()
   {
      JSONMarshaller<BigIntegerObject> marshaller = JSONMarshaller.create(BigIntegerObject.class);

      JSONObject encoded = object(string("value"), number(2L));
      BigIntegerObject object = marshaller.unmarshall(encoded);
      assertEquals(BigInteger.valueOf(2L), object.value());

   }

   @Test
   public void testBigIntegerObjectNull()
   {
      JSONMarshaller<BigIntegerObject> marshaller = JSONMarshaller.create(BigIntegerObject.class);

      JSONObject encoded = object(string("value"), NULL);
      BigIntegerObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testBigIntegerObjectBoolean()
   {
      JSONMarshaller<BigIntegerObject> marshaller = JSONMarshaller.create(BigIntegerObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testBigIntegerObjectString()
   {
      JSONMarshaller<BigIntegerObject> marshaller = JSONMarshaller.create(BigIntegerObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }




   @Test
   public void testFloatLiteral()
   {
      JSONMarshaller<FloatLiteral> marshaller = JSONMarshaller.create(FloatLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      FloatLiteral literal = marshaller.unmarshall(encoded);
      assertTrue(2F == literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testFloatLiteralNull()
   {
      JSONMarshaller<FloatLiteral> marshaller = JSONMarshaller.create(FloatLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testFloatLiteralBoolean()
   {
      JSONMarshaller<FloatLiteral> marshaller = JSONMarshaller.create(FloatLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testFloatLiteralString()
   {
      JSONMarshaller<FloatLiteral> marshaller = JSONMarshaller.create(FloatLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testFloatObject()
   {
      JSONMarshaller<FloatObject> marshaller = JSONMarshaller.create(FloatObject.class);

      JSONObject encoded = object(string("value"), number(2));
      FloatObject object = marshaller.unmarshall(encoded);
      assertTrue(Float.valueOf(2F).equals(object.value()));

   }

   @Test
   public void testFloatObjectNull()
   {
      JSONMarshaller<FloatObject> marshaller = JSONMarshaller.create(FloatObject.class);

      JSONObject encoded = object(string("value"), NULL);
      FloatObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testFloatObjectBoolean()
   {
      JSONMarshaller<FloatObject> marshaller = JSONMarshaller.create(FloatObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testFloatObjectString()
   {
      JSONMarshaller<FloatObject> marshaller = JSONMarshaller.create(FloatObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }





   @Test
   public void testDoubleLiteral()
   {
      JSONMarshaller<DoubleLiteral> marshaller = JSONMarshaller.create(DoubleLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      DoubleLiteral literal = marshaller.unmarshall(encoded);
      assertTrue(2D == literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testDoubleLiteralNull()
   {
      JSONMarshaller<DoubleLiteral> marshaller = JSONMarshaller.create(DoubleLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testDoubleLiteralBoolean()
   {
      JSONMarshaller<DoubleLiteral> marshaller = JSONMarshaller.create(DoubleLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testDoubleLiteralString()
   {
      JSONMarshaller<DoubleLiteral> marshaller = JSONMarshaller.create(DoubleLiteral.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testDoubleObject()
   {
      JSONMarshaller<DoubleObject> marshaller = JSONMarshaller.create(DoubleObject.class);

      JSONObject encoded = object(string("value"), number(2));
      DoubleObject object = marshaller.unmarshall(encoded);
      assertTrue(Double.valueOf(2D).equals(object.value()));

   }

   @Test
   public void testDoubleObjectNull()
   {
      JSONMarshaller<DoubleObject> marshaller = JSONMarshaller.create(DoubleObject.class);

      JSONObject encoded = object(string("value"), NULL);
      DoubleObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testDoubleObjectBoolean()
   {
      JSONMarshaller<DoubleObject> marshaller = JSONMarshaller.create(DoubleObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testDoubleObjectString()
   {
      JSONMarshaller<DoubleObject> marshaller = JSONMarshaller.create(DoubleObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }



   @Test
   public void testBigDecimalObject()
   {
      JSONMarshaller<BigDecimalObject> marshaller = JSONMarshaller.create(BigDecimalObject.class);

      JSONObject encoded = object(string("value"), number(2D));
      BigDecimalObject object = marshaller.unmarshall(encoded);
      assertTrue(BigDecimal.valueOf(2D).equals(object.value()));

   }

   @Test
   public void testBigDecimalObjectNull()
   {
      JSONMarshaller<BigDecimalObject> marshaller = JSONMarshaller.create(BigDecimalObject.class);

      JSONObject encoded = object(string("value"), NULL);
      BigDecimalObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testBigDecimalObjectBoolean()
   {
      JSONMarshaller<BigDecimalObject> marshaller = JSONMarshaller.create(BigDecimalObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testBigDecimalObjectString()
   {
      JSONMarshaller<BigDecimalObject> marshaller = JSONMarshaller.create(BigDecimalObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }




   @Test
   public void testCharLiteral()
   {
      JSONMarshaller<CharLiteral> marshaller = JSONMarshaller.create(CharLiteral.class);

      JSONObject encoded = object(string("value"), string("c"));
      CharLiteral literal = marshaller.unmarshall(encoded);
      assertEquals('c', literal.value());

   }

   @Test(expected = MarshallingException.class)
   public void testCharLiteralNull()
   {
      JSONMarshaller<CharLiteral> marshaller = JSONMarshaller.create(CharLiteral.class);

      JSONObject encoded = object(string("value"), NULL);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testCharLiteralNumber()
   {
      JSONMarshaller<CharLiteral> marshaller = JSONMarshaller.create(CharLiteral.class);

      JSONObject encoded = object(string("value"), number(2));
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testCharLiteralBoolean()
   {
      JSONMarshaller<CharLiteral> marshaller = JSONMarshaller.create(CharLiteral.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testCharLiteralString()
   {
      JSONMarshaller<CharLiteral> marshaller = JSONMarshaller.create(CharLiteral.class);
      // More than one character is an error
      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }


   @Test
   public void testCharacterObject()
   {
      JSONMarshaller<CharacterObject> marshaller = JSONMarshaller.create(CharacterObject.class);

      JSONObject encoded = object(string("value"), string("c"));
      CharacterObject object = marshaller.unmarshall(encoded);
      assertEquals(Character.valueOf('c'), object.value());

   }

   @Test
   public void testCharacterObjectNull()
   {
      JSONMarshaller<CharacterObject> marshaller = JSONMarshaller.create(CharacterObject.class);

      JSONObject encoded = object(string("value"), NULL);
      CharacterObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testCharacterObjectNumber()
   {
      JSONMarshaller<CharacterObject> marshaller = JSONMarshaller.create(CharacterObject.class);

      JSONObject encoded = object(string("value"), number(2));
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testCharacterObjectBoolean()
   {
      JSONMarshaller<CharacterObject> marshaller = JSONMarshaller.create(CharacterObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testCharacterObjectString()
   {
      JSONMarshaller<CharacterObject> marshaller = JSONMarshaller.create(CharacterObject.class);
      // More than one character is an error
      JSONObject encoded = object(string("value"), string("hello"));
      marshaller.unmarshall(encoded);
   }







   @Test
   public void testStringObject()
   {
      JSONMarshaller<StringObject> marshaller = JSONMarshaller.create(StringObject.class);

      JSONObject encoded = object(string("value"), string("hello"));
      StringObject object = marshaller.unmarshall(encoded);
      assertEquals("hello", object.value());

   }

   @Test
   public void testStringObjectNull()
   {
      JSONMarshaller<StringObject> marshaller = JSONMarshaller.create(StringObject.class);

      JSONObject encoded = object(string("value"), NULL);
      StringObject object = marshaller.unmarshall(encoded);
      assertNull(object.value());
   }

   @Test(expected = MarshallingException.class)
   public void testStringObjectNumber()
   {
      JSONMarshaller<StringObject> marshaller = JSONMarshaller.create(StringObject.class);

      JSONObject encoded = object(string("value"), number(2));
      marshaller.unmarshall(encoded);
   }

   @Test(expected = MarshallingException.class)
   public void testStringObjectBoolean()
   {
      JSONMarshaller<StringObject> marshaller = JSONMarshaller.create(StringObject.class);

      JSONObject encoded = object(string("value"), TRUE);
      marshaller.unmarshall(encoded);
   }



   @Test
   public void testBasicEntityWithNullSubEntity()
   {
      JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

      JSONObject encoded = object(string("title"), string("None"),
            string("isbn"), string("12-545435-HFG-7575"),
            string("uri"), string("http://wwww.joe.com/"),
            string("email"), NULL,
            string("authors"), array()
            );

      Book decoded = marshaller.unmarshall(encoded);
      assertEquals(URI.create("http://wwww.joe.com/"), decoded.getUri());
      assertEquals("None", decoded.getTitle());
      assertEquals(0, decoded.getAuthors().size());
      assertNull(decoded.getEmail());
   }





   @Test(expected = MarshallingException.class)
   public void testBasicEntityWithInvalidSubEntity()
   {
      JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

      JSONObject encoded = object(string("title"), string("None"),
            string("isbn"), string("12-545435-HFG-7575"),
            string("uri"), string("http://wwww.joe.com/"),
            string("address"), string("joe@joe.com"),
            string("authors"), array()
      );

      marshaller.unmarshall(encoded);
   }




   @Test
   public void testBasicEntityWithNullArray()
   {
      JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

      JSONObject encoded = object(string("title"), string("None"),
            string("isbn"), string("12-545435-HFG-7575"),
            string("uri"), string("http://wwww.joe.com/"),
            string("email"), object(string("address"), string("joe@go.com"),
                                    string("type"), string("Work")),
            string("authors"), NULL
      );

      Book decoded = marshaller.unmarshall(encoded);
      assertEquals(URI.create("http://wwww.joe.com/"), decoded.getUri());
      assertEquals("None", decoded.getTitle());
      assertNull(decoded.getAuthors());
   }

   @Test(expected = MarshallingException.class)
   public void testBasicEntityWithInvalidArray()
   {
      JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

      JSONObject encoded = object(string("title"), string("None"),
            string("isbn"), string("12-545435-HFG-7575"),
            string("uri"), string("http://wwww.joe.com/"),
            string("email"), object(string("address"), string("joe@go.com"),
                  string("type"), string("Work")),
            string("authors"), number(2)
      );

      marshaller.unmarshall(encoded);
   }




   @Test
   public void testBasicEntityWithNullUserType()
   {
      JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

      JSONObject encoded = object(string("title"), string("None"),
            string("isbn"), string("12-545435-HFG-7575"),
            string("uri"), NULL,
            string("email"), object(string("address"), string("joe@go.com"),
                  string("type"), string("Work")),
            string("authors"), array()
      );

      Book decoded = marshaller.unmarshall(encoded);
      assertNull(decoded.getUri());
   }


   @Test(expected = MarshallingException.class)
   public void testBasicEntityWithInvalidUserType()
   {
      JSONMarshaller<Book> marshaller = JSONMarshaller.create(Book.class);

      JSONObject encoded = object(string("title"), string("None"),
            string("isbn"), string("12-545435-HFG-7575"),
            string("uri"), number(2),
            string("email"), object(string("address"), string("joe@go.com"),
                  string("type"), string("Work")),
            string("authors"), array()
      );

      marshaller.unmarshall(encoded);
   }








   @Test
   public void testOptionalEntityDecode()
   {
      JSONMarshaller<Contact> marshaller = JSONMarshaller.create(Contact.class);

      JSONObject encoded = object(string("name"), string("Chris Singer"));
      Contact decoded = marshaller.unmarshall(encoded);
      assertEquals("Chris Singer", decoded.name());
      assertNull(decoded.email());
   }

   @Test
   public void testOptionalEntityEncode()
   {
      JSONMarshaller<Contact> marshaller = JSONMarshaller.create(Contact.class);

      Contact object = new Contact("Chris Singer", null);
      JSONObject encoded = marshaller.marshall(object);
      assertEquals(object(string("name"), string("Chris Singer")).toString(), encoded.toString());

      Contact decoded = marshaller.unmarshall(encoded);
      assertEquals(object, decoded);
      assertNull(decoded.email());
   }



   @Test
   public void testOptionalIntLiteral()
   {
      JSONMarshaller<IntLiteral> marshaller = JSONMarshaller.create(IntLiteral.class);

      JSONObject encoded = object();
      IntLiteral literal = marshaller.unmarshall(encoded);
      assertEquals(0, literal.value());
   }

   @Test
   public void testOptionalBooleanLiteral()
   {
      JSONMarshaller<BooleanLiteral> marshaller = JSONMarshaller.create(BooleanLiteral.class);

      JSONObject encoded = object();
      BooleanLiteral literal = marshaller.unmarshall(encoded);
      assertFalse(literal.value());
   }

   @Test
   public void testOptionalDoubleLiteral()
   {
      JSONMarshaller<DoubleLiteral> marshaller = JSONMarshaller.create(DoubleLiteral.class);

      JSONObject encoded = object();
      DoubleLiteral literal = marshaller.unmarshall(encoded);
      assertTrue(0D == literal.value());
   }


   @Test(expected = MarshallingException.class)
   public void testNonOptionalLongLiteral()
   {
      JSONMarshaller<LongLiteral> marshaller = JSONMarshaller.create(LongLiteral.class);

      JSONObject encoded = object();
      marshaller.unmarshall(encoded);
   }





   @Test(expected = ArithmeticException.class)
   public void testBigIntegerOverflow()
   {
      JSONMarshaller<BigIntegerObject> marshaller = JSONMarshaller.create(BigIntegerObject.class);
      BigIntegerObject object = new BigIntegerObject(BigInteger.probablePrime(128, new Random()));
      marshaller.marshall(object);  // integer too big for JSON number
   }

   @Test(expected = ArithmeticException.class)
   public void testBigDecimalOverflow()
   {
      JSONMarshaller<BigDecimalObject> marshaller = JSONMarshaller.create(BigDecimalObject.class);
      BigDecimalObject object = new BigDecimalObject(BigDecimal.valueOf(Long.MAX_VALUE));
      marshaller.marshall(object);  // integer too big for JSON number
   }

   @Test(expected = ArithmeticException.class)
   public void testLongOverflow()
   {
      JSONMarshaller<LongObject> marshaller = JSONMarshaller.create(LongObject.class);
      LongObject object = new LongObject(Long.MAX_VALUE);
      marshaller.marshall(object);  // integer too big for JSON number
   }





   @Test
   public void testByteOverflow()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);

      JSONObject max = object(string("value"), number(Byte.MAX_VALUE));
      marshaller.unmarshall(max);  // JSON number will fit a byte (No Exception)

      JSONObject min = object(string("value"), number(Byte.MIN_VALUE));
      marshaller.unmarshall(min);  // JSON number will fit a byte (No Exception)
   }

   @Test(expected = ArithmeticException.class)
   public void testByteOverflowPositive()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);
      JSONObject encoded = object(string("value"), number(Byte.MAX_VALUE + 1));
      marshaller.unmarshall(encoded);  // JSON number too big for byte
   }

   @Test(expected = ArithmeticException.class)
   public void testByteOverflowNegative()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);
      JSONObject encoded = object(string("value"), number(Byte.MIN_VALUE - 1));
      marshaller.unmarshall(encoded);  // JSON number too small for byte
   }

   @Test(expected = ArithmeticException.class)
   public void testByteNarrowing()
   {
      JSONMarshaller<ByteObject> marshaller = JSONMarshaller.create(ByteObject.class);
      JSONObject encoded = object(string("value"), number(64.5D));
      marshaller.unmarshall(encoded);  // JSON number will lose decimal precision if cast to byte
   }





   @Test
   public void testShortOverflow()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);
      JSONObject min = object(string("value"), number(Short.MIN_VALUE));
      marshaller.unmarshall(min);  // JSON number will fit a short (No Exception)

      JSONObject max = object(string("value"), number(Short.MAX_VALUE));
      marshaller.unmarshall(max);  // JSON number will fit a short (No Exception)

   }

   @Test(expected = ArithmeticException.class)
   public void testShortOverflowPositive()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);
      JSONObject encoded = object(string("value"), number(Short.MAX_VALUE + 1));
      marshaller.unmarshall(encoded);  // JSON number too big for short
   }

   @Test(expected = ArithmeticException.class)
   public void testShortOverflowNegative()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);
      JSONObject encoded = object(string("value"), number(Short.MIN_VALUE - 1));
      marshaller.unmarshall(encoded);  // JSON number too small for short
   }

   @Test(expected = ArithmeticException.class)
   public void testShortNarrowing()
   {
      JSONMarshaller<ShortObject> marshaller = JSONMarshaller.create(ShortObject.class);
      JSONObject encoded = object(string("value"), number(64.5D));
      marshaller.unmarshall(encoded);  // JSON number will lose decimal precision if cast to short
   }




   @Test
   public void testIntegerOverflow()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);
      JSONObject min = object(string("value"), number(Integer.MIN_VALUE));
      marshaller.unmarshall(min);  // JSON number will fit an integer (No Exception)

      JSONObject max = object(string("value"), number(Integer.MAX_VALUE));
      marshaller.unmarshall(max);  // JSON number will fit an integer (No Exception)

   }

   @Test(expected = ArithmeticException.class)
   public void testIntegerOverflowPositive()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);
      JSONObject encoded = object(string("value"), number((long) Integer.MAX_VALUE + 1L));
      marshaller.unmarshall(encoded);  // JSON number too big for integer
   }

   @Test(expected = ArithmeticException.class)
   public void testIntegerOverflowNegative()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);
      JSONObject encoded = object(string("value"), number((long) Integer.MIN_VALUE - 1L));
      marshaller.unmarshall(encoded);  // JSON number too small for integer
   }

   @Test(expected = ArithmeticException.class)
   public void testIntegerNarrowing()
   {
      JSONMarshaller<IntegerObject> marshaller = JSONMarshaller.create(IntegerObject.class);
      JSONObject encoded = object(string("value"), number(64.5D));
      marshaller.unmarshall(encoded);  // JSON number will lose decimal precision if cast to integer
   }





   @Test
   public void testPolymorphicNullEntity()
   {
      JSONMarshaller<House> marshaller = JSONMarshaller.create(House.class);

      House object = new House();
      JSONObject encoded = marshaller.marshall(object);

      House decoded = marshaller.unmarshall(encoded);
      assertEquals(object, decoded);
   }

   @Test(expected = MarshallingException.class)
   public void testPolymorphicEntityString()
   {
      JSONMarshaller<House> marshaller = JSONMarshaller.create(House.class);
      JSONObject encoded = object(string("vehicle"),string("car"));
      marshaller.unmarshall(encoded);
   }





   @Test
   public void testUnmarshallWithExtraProperties()
   {
      JSONObjectBuilder builder = new JSONObjectBuilder();
      builder.add("firstName", "Chris").add("lastName", "Singer").add("age", 32);
      JSONMarshaller<Author> marshaller = JSONMarshaller.create(Author.class);
      Author decoded = marshaller.unmarshall(builder.build());
      assertEquals("Chris", decoded.getFirstName());
      assertEquals("Singer", decoded.getLastName());
   }

   @Test
   public void testUserTypes()
   {
      Random rand = new Random();
      Pattern pattern = Pattern.compile("\\s+");
      UUID uuid = new UUID(rand.nextLong(), rand.nextLong());
      Currency currency = Currency.getInstance("USD");

      UserTypes base = new UserTypes(pattern, uuid, currency);
      JSONMarshaller<UserTypes> marshaller = JSONMarshaller.create(UserTypes.class);
      JSONObject encoded = marshaller.marshall(base);
      UserTypes decoded = marshaller.unmarshall(encoded);

      assertEquals(pattern.pattern(), decoded.getPattern().pattern());
      assertEquals(pattern.flags(), decoded.getPattern().flags());
      assertEquals(uuid.toString(), decoded.getUuid().toString());
      assertEquals(currency.getCurrencyCode(), decoded.getCurrency().getCurrencyCode());
   }




   private static Book createBook()
   {
      Book book = new Book("Effective Communication","978-0-7382-0287-7",
              URI.create("http://www.google.com/"));

      Set<Author> authors = new LinkedHashSet<>();
      authors.add(new Author("Harry","Chambers"));
      authors.add(new Author("Rick","Reid"));

      book.setAuthors(authors);

      book.setEmail(new Email("joe@nowhere.com", Email.Type.Work));
      return book;
   }
}
