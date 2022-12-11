package com.epam.DocuSignDemo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        String a = "Rahul";
        String b = "Rahul";
        System.out.println(a==b);
        System.out.println(a.equals(b));



        String str1 = new String("abc");
        String str2 = new String("abc");
        System.out.println(str1==str2);
        System.out.println(str1.equals(str2));


        Employee e1 = new Employee(123l);
        Employee e2 = new Employee(123l);
        System.out.println(e1==e2);
        System.out.println(e1.equals(e2));
    }
}
