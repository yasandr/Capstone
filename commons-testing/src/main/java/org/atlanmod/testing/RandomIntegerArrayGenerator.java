package org.atlanmod.testing;

public class RandomIntegerArrayGenerator implements Generator {



    @Override
    public int[] generate() {
        int[] tab={1,2,3};
        return tab;
    }

    @Override
    public Class[] types() {
       // Class[] types={Integer[].class,int[].class};
        return new Class[0];
    }
}
