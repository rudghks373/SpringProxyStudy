package me.kyunghwan.springproxystudy;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


class DefaulBookServiceTest {

    BookService bookService = (BookService) Proxy.newProxyInstance(BookService.class.getClassLoader(), new Class[]{BookService.class},
            new InvocationHandler() {
                BookService bookService = new DefaulBookService();

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if(method.getName().equals("rent")) {
                        System.out.println("aaaa");
                        Object invoke = method.invoke(bookService, args);
                        System.out.println("bbbbb");
                        return invoke;
                    }
                    return method.invoke(bookService, args);
                }
            });

    @Test
    public void testdi() {
        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
        bookService.returnRent(book);
    }

}