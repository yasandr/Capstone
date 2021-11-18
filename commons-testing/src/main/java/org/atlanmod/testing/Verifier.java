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
    private static Generator stringGenerator= new RandomStringGenerator();
    private static Generator integerGenerator= new RandomIntegerGenerator();

    static {
    registerGenerator(integerGenerator);
    registerGenerator(stringGenerator);
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


    public static void generateConstructorsOfClass(Class klass)  {
        /* Au cas où on genere les constructeurs de la classe Integer,
         on doit generer un String d'entiers pour le constructeur*/
        System.out.println("La classe du constructeur "+klass.getName()+" vs "+Integer.class.getName());
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(new RandomStringOfIntGenerator());
        }

        for (Constructor<?> each : klass.getConstructors()) {
            System.out.println("La signature du constructeur : "+each);
            if(canGenerateConstructor(each)) generateConstructor(each);
            else System.out.println("Impossible to generate this constructor" + "\n");
        }

        //On remet le generateur de String par défaut
        if (klass.getName().equals(Integer.class.getName())) {
            registerGenerator(stringGenerator);
        }

    }

    public static void generateConstructor(Constructor<?> constructeur)  {
        List<Object> list = new ArrayList<>();
        for (Class<?> type : constructeur.getParameterTypes()) {
            Object o = generators.get(type).generate();
            list.add(o);
        }
       // System.out.println("la taille de la liste est : "+list.size());
        try {
           /* for(int i=0;i<list.size();i++) {
                System.out.println("the object" + list.get(i));
            }*/
            Object o = constructeur.newInstance(list.toArray());
            System.out.println("La nouvelle instance de l'objet: {" + o +  "}\n");
        } catch (InstantiationException | IllegalAccessException |InvocationTargetException e) {
                e.printStackTrace();
        }
    }

    public static boolean canGenerateConstructor(Constructor<?> constr) {
        boolean possible = false;
        int i = 0;
        System.out.println("Le nombre de paramètres du constructeur : "+constr.getParameters().length);
        if(constr.getParameters().length==0) {
            System.out.println("Ce constructeur n'a pas de paramètres donc pas besoin de générateur.");
            return true;
        }
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
