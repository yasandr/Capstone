/*
 * Copyright (c) 2020 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;

import org.atlanmod.commons.Throwables;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.lang.reflect.Array;

/**
 * Entry point for verification methods that improve unit tests.
 * Each method in this class is a static factory for a specific verification object.
 *
 * For instance:
 *
 * <pre><code class='java'>
 * {@link Verifier#verifyEqualsOf(Class) verifyEqualsOf(String.class)}
 *      .{@link EqualsVerifier#withArguments(Object...) withArguments("a String"}
 *      .{@link EqualsVerifier#andVariants(Object...) andVariants("another String"}
 *      .{@link EqualsVerifier#check() check()}
 *
 * </code></pre>
 */
public class Verifier {
    private static final Map <Class<?>, Generator<?> > generators= new HashMap<>();
    private static RandomStringGenerator stringGenerator= new RandomStringGenerator();
    private static RandomIntegerGenerator integerGenerator= new RandomIntegerGenerator();

/**    static {
        registerGenerator(String.class,stringGenerator);
        registerGenerator(Integer.class,integerGenerator);
    }**/

  //  private static final Map<Class<?>, String> findPrimitiveClass;
    static {
    registerGenerator(integerGenerator);
    registerGenerator(stringGenerator);
    registerGenerator(new RandomIntegerArrayGenerator());
      /*  findPrimitiveClass = new HashMap<Class<?>, String>(18);
        findPrimitiveClass.put(int.class, "FOUND int");
        findPrimitiveClass.put(String.class, "FOUND String");
        findPrimitiveClass.put(Integer.class, "int");
        findPrimitiveClass.put(Integer[].class, "int[]");
        findPrimitiveClass.put(Byte.class, "byte");
        findPrimitiveClass.put(Byte[].class, "byte[]");
        findPrimitiveClass.put(Character.class, "char");
        findPrimitiveClass.put(Character[].class, "char[]");
        findPrimitiveClass.put(Boolean.class, "boolean");
        findPrimitiveClass.put(Boolean[].class, "boolean[]");
        findPrimitiveClass.put(Double.class, "double");
        findPrimitiveClass.put(Double[].class, "double[]");
        findPrimitiveClass.put(Float.class, "float");
        findPrimitiveClass.put(Float[].class, "float[]");
        findPrimitiveClass.put(Long.class, "long");
        findPrimitiveClass.put(Long[].class, "long[].");
        findPrimitiveClass.put(Short.class, "short");
        findPrimitiveClass.put(Short[].class, "short[]");
        findPrimitiveClass.put(Void.class, "void");
        findPrimitiveClass.put(Void[].class, "void[]");*/
    }

    private Verifier() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Creates a {@link EqualsVerifier} for class {@code type}.
     *
     * @param type the class whose {@code equals()} method will be verified.
     * @param <T> the actual class of the class {@type}.
     * @return an instance of {@link EqualsVerifier}.
     */
     public static <T> EqualsVerifier<T> verifyEqualsOf(Class<T> type) {
        return new EqualsVerifier<>(type);
    }


     public static <T extends Serializable> SerializationVerifier<T> verifySerializer(Class<T> type) {
        return new SerializationVerifier<>(type);
    }

    public static <T> void registerGenerator(Generator <T> generator) {
         for (Class<?> type :generator.types() ) {
             generators.put(type, generator);
         }
    }


   /* public static boolean checkIfGeneratorExists(Class  klass) {
        for (Constructor<?> each :  klass.getConstructors()
        ) {
            System.out.println(each);
            Arrays.stream(each.getParameterTypes()).anyMatch(type->{
                System.out.println("type :"+type.getTypeName());
                return true;
            });
            System.out.println(each);
            int i=0;
            for (Class type :each.getParameterTypes()) {
                System.out.println("type :"+type.getTypeName());
                String tmp=type.getTypeName().toString();

                //lorsqu'on ne trouve pas de générateur correspondant à un argument du construcrteur
                if (!findPrimitiveClass.containsValue(tmp)) {
                    System.out.println("Il n'y a pas de générateur pour ce constructeur"+"\n");
                    break;
                }
                i++;
                //Lorsqu'on a trouvé un générateur pour chaque argument du constructeur
                if(i==each.getParameterCount() && findPrimitiveClass.containsValue(tmp)) {
                    System.out.println("Nous avons trouvé générateur pour ce constructeur"+"\n");
                    i=0;
                }
                }
            }

         return false;
    }*/

    public static void generateConstructorsOfClass(Class klass)  {
        for (Constructor<?> each : klass.getConstructors()) {
            System.out.println("La signature du constructeur : "+each);
            if(canGenerateConstructor(each)) generateConstructor(each);
            else System.out.println("Impossible to generate this constructor" + "\n");
        }
    }

    public static void generateConstructor(Constructor<?> constructeur)  {
            List<Object> list = new ArrayList<>();
            for (Class<?> type : constructeur.getParameterTypes()) {
                Object o = generators.get(type).generate();
                if(type==int[].class) {
                    list.add((int[]) Array.newInstance(int.class, 3));
                }
                else list.add(o);
            }
            try {
              //  System.out.println(Arrays.toString((int[]) list.get(0)));
                Object o = constructeur.newInstance(list.toArray());
                //System.out.println("La nouvelle instance de l'objet: " + o + "\n");
            } catch (InstantiationException | IllegalAccessException |InvocationTargetException e) {
                e.printStackTrace();
            }
    }

    public static boolean canGenerateConstructor(Constructor<?> constr) {
        boolean possible = false;
        int i = 0;
        for (Class<?> type : constr.getParameterTypes()) {
            System.out.println("Le Type du paramètre est: " +type);
            if(generators.containsKey(type)) {
                System.out.println("Le generateur associé est: " + generators.get(type));
                i++;
                if(i==constr.getParameterCount()) {
                    possible = true;
                    System.out.println("Nous avons trouvé générateur pour ce constructeur");
                }
            }
            else {
                System.out.println("Il n'y a pas de générateur pour ce constructeur");
                break;
            }
        }
        return possible;
    }

    public static void main(String[] args) {
        generateConstructorsOfClass(String.class);
    }
}
