package com.joy.yariklab;

import androidx.annotation.NonNull;

public class TestExpl {

    static class Student {
        String name;
        int age;

        @NonNull
        @Override
        public String toString() {
            return "name = " + name + ", age = " + age;
        }
    }

    public static void yarikMain() {
        /*Student std = new Student();
        Thread th1 = new Thread(() -> {
            std.setAge(18);
            System.out.println(std);
        });
        th1.start();

        Thread th2 = new Thread(() -> {
            std.setName("Yarik");
            System.out.println(std);
        });
        th2.start();*/

        ProducerConsumerReentrantLock<Student> producer = new ProducerConsumerReentrantLock<>();

        Thread th1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                Student std = new Student();
                std.name = "Yarik";
                std.age = i;
                try {
                    producer.produce(std);
                    System.out.println("produce -> " + std);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        th1.start();

        Thread th2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    Student std = producer.consume();
                    System.out.println("consume -> " + std);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        th2.start();
    }
}

