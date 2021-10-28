package org.atlanmod.testing;

import org.atlanmod.commons.Guards;

import java.util.Arrays;

public class IntegerBoundaryGenerator implements Generator<Integer>{

    private int[] values;
    private int index = 0;

    public IntegerBoundaryGenerator(){
        this(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE});
    }

    public IntegerBoundaryGenerator(int[] values) {
        Guards.checkGreaterThan(values.length, 0);
        this.values = Arrays.copyOfRange(values, 0, values.length) ;
    }

    @Override
    public Integer generate() {
        return this.values[index % values.length];
    }
}
