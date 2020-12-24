package me.kyunghwan.springproxystudy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceClassTypeTest {

    @Test
    public void testDi(){
        MethodInterceptor handler = new MethodInterceptor() {

            BookServiceClassType bookServiceClassType = new BookServiceClassType();

            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                if(method.getName().equals("rent")) {
                    System.out.println("aaaa");
                    Object invoke = method.invoke(bookServiceClassType, args);
                    System.out.println("bbbb");
                    return invoke;
                }
                return method.invoke(bookServiceClassType , args);
            }
        };
        BookServiceClassType bookServiceClassType = (BookServiceClassType) Enhancer.create(BookServiceClassType.class, handler);

        Book book = new Book();
        book.setTitle("spring");
        bookServiceClassType.rent(book);
        bookServiceClassType.returnRent(book);

    }

}