# JSONMarshaller
JSONMarshaller is a Java 1.7 library that allows marshalling and unmarshalling of JSON objects to
and from entities ("Java classes").

Additionally, the library provides a number of utilities for working with JSON Objects themselves.


Introduction
------------

Let start with some examples. Suppose your are building a bookstore and want to represent books and
authors. You might have two Java classes similar to (we will discuss the annotations in a minute):

````
@Entity
class Book {
  @Value
  private String title;
  @Value
  private String isbn;
  @Value
  private Set<Author> authors;
}

@Entity
class Author {
  @Value
  private String firstName;
  @Value
  private String lastName;
}
````

and from an instance of a book, build the JSON object

````
{"title":   "Vocation Createurs",
 "isbn":    "2829302680",
 "authors": [{"firstName": "Barbara", "lastName": "Polla"},
             {"firstName": "Pascal",  "lastName": "Perez"}]}

````

or vice-versa: you have a JSON representation and wish to create Java instances automatically from
it. JSONMarshaller offers exactly that.

````
Book vocationCreateurs = ...;
JSONMarshaller<Book> m = JSONmarshaller.create(Book.class);
JSONObject o = m.marshall(vocationCreateurs);
````

and

````
JSONObject o = ...;
JSONMarshaller<Book> m = JSONmarshaller.create(Book.class);
Book vocationCreateurs = m.unmarshall(o);
````

Entities
--------

Entities represent the domain model. They are objects holding data, such as Book, Author, User, or
Account. On the other hand, an InputStream object for instance, represents computation. To work with
the JSONMarshaller, your entities should provide a no argument constructor. This allows the marshaller
to create fresh instances and populate them.


Annotations
-----------

The JSONMarshaller uses two annotations to describe entities: @Entity and @Value.

As we have seen

````
@Entity
class Book {
````

the @Entity annotates a class and informs the marshaller that it is a JSON entity. Again, entities should
have a no argument constructor.

The second annotation

````
@Value
String firstName;
````

informs the marshaller that the field should be persisted to JSON. Non annotated fields are considered
transient (will not be persisted). Everything needed is automatically inferred from the bytecode of the
Book class!


Built in Type Support
---------------------

JSONMarshaller supports all base types, their corresponding wrapper classes, String and Enum types. To
(un)marshall user defined types please refer to the UserDefinedTypes tutorial.

Enum Support

Because the library has built in support for enum types, no @Entity annotation is needed in the type
definition. If we have an enum type Abc

````
enum Abc {
  A, B, C;
}
````

and an entity Foo

````
@Entity
class Foo {
   @Value
   private Abc abc = Abc.A;
}
````

This will marshall into the expected JSON representation {"abc": "A"} and vice-versa.

If your enum type has other fields they will simply be ignored. This is due to the fact that run-time
instantiation of enum types is forbidden by the JVM.


Options
-------

name option

When an entitiy is marshalled, the Java field name is used. To override this default behaviour, you can
use the name option


````
@Value(name = "fname")
String firstName;
````

This would be marshalled to

````
{..., "fname": "Pascal", ...}
````

instead of

````
{..., "firstName": "Pascal", ...}
````

optional option

The optional option indicates that a value is optional. When unmarshalling an entity, if the value is not
found no exception will be thrown. This allows to define defaults to certain properties of an entity that
are overridden only if a value is specified. For instance

````
@Entity
class Email {
   @Value(optional = true)
   private String email = "support@mydomain.com";
}
````


views option

Do you happen to have complex entities which need to be marshalled once with a set of fields, and in another
situation with other fields? Views allow you to specify different ways to marshall entities. The views option
takes a String array as parameter which are the views in which a field ought to be included. Consider the
Address class.

````
@Entity
class Address {
   @Value(views = {"full", "simple"}
   private String name;
   @Value(views = {"full"})
   private URL url;
}
````

The name field will be marshalled in the full and simple views, whereas the url field will only be marshalled in
the full view. To specify the view of an entity to take when marshalling or unmarshalling, please look out the
updated interface of the JSONMarshaller.




Interfaces
----------

How do I marshall a List that contains classes that implement an interface?

Imagine you have a garage with vehicles:


````
@Entity
public class Garage {
   @Value
   private List<Vehicle> vehicles = new ArrayList<Vehicle>();

   public void add(Vehicle v) {
      vehicles.add(v);
   }
}

public interface Vehicle {
   public int wheelCount();
}
````


How can I get JSONMarshaller to marshall this? If you just add @Entity to the Vehicle interface you'll get an
error: java.lang.IllegalArgumentException: interface Vehicle does not have a no argument constructor. And that
makes total sense, as it's an interface! Of course it doesn't have a no argument constructor! The implementing
classes have that constructor. Here's what you do:

* Add to the Interface the option discriminatorName. This will add a field in the json of the subclasses with
  that name. This field is used to distinguish between the different subclasses.
* Add to each subclass the option discriminator. This will be the value used for above mentioned field, for that
  subclass.
* Add to the Interface the option subclasses. This will let the marshaller know which classes it needs to parse.

So our example becomes:

````
@Entity
public class Garage {
   @Value
   private List<Vehicle> vehicles = new ArrayList<Vehicle>();

   public void add(Vehicle v) {
      vehicles.add(v);
   }
}

@Entity(discriminatorName = "type", subclasses = {Car.class, Motorcycle.class})
public interface Vehicle {
   public int wheelCount();
}

@Entity(discriminator="Car")
public class Car implements Vehicle {
   @Value
   private int wheels = 4;

   public int wheelCount() {
      return wheels;
   }
}

@Entity(discriminator = "Motorcycle")
public class Motorcycle implements Vehicle {
   @Value
   private int wheels = 2;

   public int wheelCount() {
      return wheels;
   }
}
````

If we marshall this:

````
Garage g = new Garage();
g.add(new Car());
g.add(new Motorcycle());
JSONMarshaller<Garage> m = JSONMarshaller.create(Garage.class);
System.out.println(m.marshall(g));
````

We get as a result:

````
{
   "vehicles":[
      {
         "type":"Car",
         "wheels":4
      },
      {
         "type":"Motorcycle",
          "wheels":2
      }
   ]
}
````

The two objects in the List vehicles now each have an extra field type that tells the marshaller what
class to use when unmarshalling this type.



User Defined Types
------------------

To support objects for which you do not control the source code or complex types, JSONMarshaller supports
user defined types.

Lets see an example:

````
@Entity
public class Resource {
   @Value
   private URI uri;
}
````

The URI class is actually a pre-packaged user type so the following is actually done and bundled with the
API.

First we need to create our JSONUserType implementation.


````
public class URIType implements JSONUserType<URI, JSONString> {

   public JSONString marshall(URI entity)
   {
      return string(entity.toString());
   }

   public URI unmarshall(JSONString object)
   {
      try {
         return new URI(object.getString());
      } catch(URISyntaxException e) {
         throw new MarshallingException("invalid uri syntax", e);
      }
   }

   public Class<URI> getReturnedClass()
   {
      return URI.class;
   }
}
````

The above sample simply stores the uri as a JSON String. However, if the string is not in the proper format
a MarshallingException will be thrown when unmarshalling the source JSON.

Next we need to create our UserTypeService implementation:

````
public class DefaultUserTypes implements UserTypeService {

   public JSONUserType<?, ? extends JSONValue> create(Type type)
   {
      if(type instanceof Class) {
         Class klass = (Class) type;
         if (klass == URI.class) {
            return new URIType();
         }
      }
      return null;
   }
}
````

In this case our service only supports the single user type. It would of course behove you to bundle several
user types together in a single service provider impl.

Finally, we need to define our service provider using the standard java service provider methodology. To do
that we create a text file that will be located in the classpath with the following path and file name:

/META-INF/services/xpertss.json.spi.UserTypeService

The contents would be the fully qualified name of your service procvider impl

````
org.xpertss.json.types.DefaultUserTypes
````

