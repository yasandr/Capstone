package org.atlanmod.testing;

import java.lang.reflect.Constructor;
import java.util.Random;

public class RandomIntegerGenerator implements Generator<Integer>{

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("Max doit etre supérieur au Min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1);
    }

    @Override
    public Integer generate() {
        int min= 1;
        int max= 20;
        return getRandomNumberInRange(min,max);
    }

    @Override
    public Class<Integer>[] types() {
        Class[] types={Integer.class,int.class,int[].class};
        return types;
    }
}
