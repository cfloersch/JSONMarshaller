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

or vice-versa: you have a JSON representation and wish to create Java instances automatically from it.
JSONMarshaller offers exactly that.

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