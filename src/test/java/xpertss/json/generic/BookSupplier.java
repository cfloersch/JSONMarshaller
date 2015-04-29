package xpertss.json.generic;

import xpertss.json.Entity;
import xpertss.json.book.Book;

@Entity
public class BookSupplier extends Supplier<Book> {

    public BookSupplier() { }
    public BookSupplier(Book book) { super(book); }

}
