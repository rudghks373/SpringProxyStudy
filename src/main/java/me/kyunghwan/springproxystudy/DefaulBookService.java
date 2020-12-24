package me.kyunghwan.springproxystudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class DefaulBookService implements BookService {

    BookRepository bookRepository;

    public void rent(Book book) {
        System.out.println("rent: " + book.getTitle());
    }

    public  void returnRent(Book book){
        System.out.println("rent: " + book.getTitle());
    }

}
