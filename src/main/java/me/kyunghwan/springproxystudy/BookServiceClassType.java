package me.kyunghwan.springproxystudy;

public class BookServiceClassType {

    public void rent(Book book) {
        System.out.println("rent: " + book.getTitle());
    }

    public  void returnRent(Book book){
        System.out.println("rent: " + book.getTitle());
    }


}
