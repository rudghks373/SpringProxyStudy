package me.kyunghwan.springproxystudy;

public interface BookService {

    void rent(Book book);

    void returnRent(Book book);
}
