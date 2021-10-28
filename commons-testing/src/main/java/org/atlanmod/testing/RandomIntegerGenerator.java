package org.atlanmod.testing;

import java.lang.reflect.Constructor;
import java.util.Random;

public class RandomIntegerGenerator implements Generator<Integer>{

    // Pour les tests
    public static void main(String[] args) {
        for (Constructor<?> each :  IntegerBoundaryGenerator.class.getDeclaredConstructors()
             ) {
            System.out.println(each);
        }
        /**
        for (int i = 0; i < 10; i++) {
            System.out.println(getRandomNumberInRange(5, 10));
        }**/
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max doit etre supérieur au Min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    public Integer generate() {
        return 0;
    }
}
