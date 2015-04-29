/**
 * Created by IntelliJ IDEA.
 * User: cfloersch
 * Date: 4/8/11 2:27 PM
 * Copyright Manheim online
 */
package xpertss.json.book;

import xpertss.json.Entity;
import xpertss.json.Value;

import java.net.URI;
import java.util.Objects;
import java.util.Set;

@Entity
public class Book {

   @Value
   private String title;

   @Value
   private String isbn;

   @Value
   private URI uri;


   @Value
   private Email email;

   @Value
   private Set<Author> authors;

   public Book() { }
   public Book(String title, String isbn, URI uri)
   {
      this.title = title;
      this.isbn = isbn;
      this.uri = uri;
   }


   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public String getIsbn()
   {
      return isbn;
   }

   public void setIsbn(String isbn)
   {
      this.isbn = isbn;
   }


   public Email getEmail()
   {
      return email;
   }

   public void setEmail(Email email)
   {
      this.email = email;
   }

   public URI getUri()
   {
      return uri;
   }

   public void setUri(URI uri)
   {
      this.uri = uri;
   }



   public Set<Author> getAuthors()
   {
      return authors;
   }

   public void setAuthors(Set<Author> authors)
   {
      this.authors = authors;
   }


   public boolean equals(Object o)
   {
       if(o instanceof Book) {
           Book obj = (Book)o;
           return Objects.equals(title, obj.title) &&
                   Objects.equals(isbn, obj.isbn) &&
                   Objects.equals(uri, obj.uri) &&
                   Objects.equals(email, obj.email) &&
                   Objects.equals(authors, obj.authors);
       }
       return false;
   }

    public int hashCode()
    {
        return Objects.hash(title, isbn, uri, email, authors);
    }


}
