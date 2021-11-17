package org.atlanmod.testing;

import org.atlanmod.commons.Guards;

import java.util.ArrayList;
import java.util.Arrays;

public class StringBoundaryGenerator implements Generator<String> {

    private String[] values;
    private int index;

    public StringBoundaryGenerator() {
        this(new String[]{"monsieur", "yassine", "el", "kamel", ""});
    }

    public StringBoundaryGenerator(String[] values) {
        Guards.checkGreaterThan(values.length, 0);
        this.values = Arrays.copyOfRange(values, 0, values.length);
    }

    @Override
    public String generate() {
        return this.values[index % values.length];
    }

    @Override
    public Class<String>[] types() {
        return new Class[0];
    }

    public static void main(String[] args) {
        int i;
        StringBoundaryGenerator ibou=new StringBoundaryGenerator();
        for(i=0;i<5;i++) {
            System.out.println(ibou.generate());
        }
    }
}