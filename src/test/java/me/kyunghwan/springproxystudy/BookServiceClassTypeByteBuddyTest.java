package me.kyunghwan.springproxystudy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceClassTypeByteBuddyTest {


    @Test
    public void testDi() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class<? extends BookServiceClassType> proxyClass = new ByteBuddy().subclass(BookServiceClassType.class)
                .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    BookServiceClassType bookServiceClassType = new BookServiceClassType();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("aaaa");
                        Object invoke = method.invoke(bookServiceClassType, args);
                        System.out.println("bbbb");
                        return invoke;
                    }
                }))
                .make().load(BookServiceClassType.class.getClassLoader()).getLoaded();
        BookServiceClassType bookServiceClassType = proxyClass.getConstructor(null).newInstance();

        Book book = new Book();
        book.setTitle("spring");
        bookServiceClassType.rent(book);
        bookServiceClassType.returnRent(book);
    }
}