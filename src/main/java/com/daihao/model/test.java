package com.daihao.model;

public class test {
    public static void main(String[] args) {
        A obj = new B();
        obj.say();
    }
}

class A {
    public A()
    {
        System.out.println("Constr A");
    }

    public void say()
    {
        System.out.println("Say A");
    }
}

class B extends A {

    public void say()
    {
        System.out.println("Say B");
    }

    public void sayB(){
        System.out.println("SAYB B");
    }
}