/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.reflect;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Builder;
import org.atlanmod.commons.annotation.Singleton;
import org.atlanmod.commons.annotation.Static;
import sun.reflect.ReflectionFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static org.atlanmod.commons.Guards.checkArgument;
import static org.atlanmod.commons.Guards.checkNotNull;

/**
 * Static utility methods related to reflection.
 */
@ParametersAreNonnullByDefault
public final class MoreReflection {

    private static final Map<Class<?>, Class<?>> primitiveToWrapper = new HashMap<>();

    static {
        primitiveToWrapper.put(Boolean.TYPE, Boolean.class);
        primitiveToWrapper.put(Byte.TYPE, Byte.class);
        primitiveToWrapper.put(Character.TYPE, Character.class);
        primitiveToWrapper.put(Double.TYPE, Double.class);
        primitiveToWrapper.put(Float.TYPE, Float.class);
        primitiveToWrapper.put(Integer.TYPE, Integer.class);
        primitiveToWrapper.put(Long.TYPE, Long.class);
        primitiveToWrapper.put(Short.TYPE, Short.class);
    }

    private MoreReflection() {
        throw Throwables.notInstantiableClass(getClass());
    }

    /**
     * Gets or creates a instance of the given {@code type} by using the static method named {@code name}.
     * <p>
     * If the {@code type} is annotated by {@link Singleton} or by {@link Builder}, then the static method identified by
     * the value of the annotation is used to get the instance. Otherwise, the default constructor is used.
     *
     * @param type the class to look for
     * @param <T>  the type of the instance
     * @return the single instance if the {@code type} is a singleton, or a new instance
     * @throws ReflectionException if an error occurs during the instantiation
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type) {
        checkNotNull(type, "type");

        T instance;
        Optional<String> methodName = findConstructionMethod(type);

        try {
            if (methodName.isPresent()) {
                Method method = type.getMethod(methodName.get());
                method.setAccessible(true);
                instance = (T) method.invoke(null);
            } else {
                instance = type.getConstructor().newInstance();
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ReflectionException(e);
        }

        return instance;
    }

    /**
     * Returns the name of the method to use for creating a new instance of {@code type}, according to its annotations.
     *
     * @param type the class to look for
     * @return an {@link Optional} containing the name of the construction method, or {@link Optional#empty()} if the
     * {@code type} is not annotated
     */
    @Nonnull
    private static Optional<String> findConstructionMethod(Class<?> type) {
        checkArgument(!type.isAnnotationPresent(Static.class), "%s is annotated with @%s: cannot be instantiated", type.getName(), Static.class.getSimpleName());

        Optional<String> methodName;

        if (type.isAnnotationPresent(Singleton.class)) {
            methodName = Optional.of(type.getAnnotation(Singleton.class).value());
        } else if (type.isAnnotationPresent(Builder.class)) {
            methodName = Optional.of(type.getAnnotation(Builder.class).value());
        } else {
            // Use the default constructor
            methodName = Optional.empty();
        }

        return methodName;
    }

    /**
     * Retrieves all interfaces implemented by the given class and its superclasses.
     * <p>
     * The order is determined by looking through each interface in turn as declared in the source file and following
     * its hierarchy up. Then each superclass is considered in the same way. Later duplicates are ignored, so the order
     * is maintained.
     *
     * @param type the class to look up
     * @return a {@link Set} of interfaces in order
     */
    @Nonnull
    public static Set<Class<?>> getAllInterfaces(Class<?> type) {
        Set<Class<?>> interfaces = new LinkedHashSet<>();
        appendAllInterfaces(type, interfaces);
        return interfaces;
    }

    /**
     * Retrieves recursively the interfaces for the specified class.
     *
     * @param type       the class to look up
     * @param interfaces the set where to append interfaces of the class
     */
    private static void appendAllInterfaces(Class<?> type, Set<Class<?>> interfaces) {
        while (type != null) {
            Arrays.stream(type.getInterfaces()).filter(interfaces::add).forEachOrdered(i -> appendAllInterfaces(i, interfaces));
            type = type.getSuperclass();
        }
    }

    /**
     * Returns the constructor for creating a new instance of {@code type}, whose parameters are assignable
     * with the {@code argumentTypes}.
     *
     * @param type          the class to look for
     * @param argumentTypes a set of types allowing to find the suitable constructor.
     * @return an {@link Optional} containing the suitable constructor, or {@link Optional#empty()} if no constructor
     * is found.
     */
    public static Optional<Constructor> findConstructor(Class type, Class[] argumentTypes) {
        return Stream.of(type.getConstructors()).filter(each -> matches(each, argumentTypes))
                .findFirst();
    }

    /**
     * Returns the static factory method for creating instances of {@code type}, whose parameters are
     * assignable with the {@code argumentTypes}.
     *
     * @param type          the class to look for.
     * @param argumentTypes a set of types allowing to find the suitable method.
     * @return an {@link Optional} containing the suitable factory method, or {@link Optional#empty()}
     * if no constructor is found.
     */
    public static Optional<Method> findFactoryMethod(Class type, Class[] argumentTypes) {
        return Stream.of(type.getMethods())
                .filter(each -> Modifier.isStatic(each.getModifiers()))
                .filter(each -> each.getReturnType().isAssignableFrom(type))
                .filter(each -> matches(each, argumentTypes))
                .findFirst();
    }

    /**
     * Returns a {@link Function} that creates new instances of {@code type}, using as arguments instances of
     * {@code argumentTypes}, i.e., using a constructor or factory method whose signature matches {@code argumentTypes}.
     *
     * @param type          the class to look for.
     * @param argumentTypes the signature of the suitable constructor or method
     * @return a {@link Function} that creates new instances of {@code type}, using either a {@link Constructor} or
     * a static factory {@link Method}.
     */
    public static <T> Function<Object[], T> getInstantiator(Class<T> type, Class[] argumentTypes) {
        Optional<Constructor> constructor = findConstructor(type, argumentTypes);
        if (constructor.isPresent()) {
            return arguments -> {
                try {
                    return (T) constructor.get().newInstance(arguments);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Could not instantiate class with constructor");
                }
            };
        } else {
            Optional<Method> factory = findFactoryMethod(type, argumentTypes);
            if (factory.isPresent()) {
                return arguments -> {
                    try {
                        return (T) factory.get().invoke(null, arguments);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        throw new RuntimeException(
                                "Could not instantiate class with factory method");
                    }
                };
            } else {
                throw new IllegalArgumentException(
                        "Could not find compatible constructor or factory method");
            }
        }
    }

    /**
     * Checks whether an {@code Executable} ({@code Constructor} or {@code Method}) is compatible with
     * the {@code argumentTypes}, i.e., whether all parameters types are assignable from all types of
     * {@code argumentTypes}, respectively.
     * <p>
     * The {@code argumentTypes} parameter is an array of {@code Class} objects that are assignment-compatible
     * with the executable's formal parameter types, in declared order.
     *
     * @param executable    the instance of {@code Executable} to be considered.
     * @param argumentTypes an array of types to be compared with the executable parameters.
     * @return true, if each parameter of {@code executable} is assignable from an element of {@code argumentTypes}.
     */
    public static boolean matches(Executable executable, Class[] argumentTypes) {
        Class[] types = executable.getParameterTypes();
        if (types.length != argumentTypes.length) {
            return false;
        }
        for (int i = 0; i < argumentTypes.length; i++) {
            if (!isAssignable(types[i], argumentTypes[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether a left operand type can be assigned from the right operand type
     * If the operand is a primitive type, first converts it to its wrapper class.
     * <p>
     * Contrary to the {@link Class#isAssignableFrom(java.lang.Class)} method,
     * this method takes ont account the assignment between Wrappers Classes and
     * Primitive Types.
     *
     * @param leftOperand  The Class or Primitive we want to assign into
     * @param rightOperand The Class or Primitive we want to assign
     * @return {@code true}, if assignment is possible
     */
    public static boolean isAssignable(final Class<?> leftOperand, final Class<?> rightOperand) {
        return wrapperClassFor(leftOperand).isAssignableFrom(wrapperClassFor(rightOperand));
    }

    /**
     * Returns the Wrapper class for a Primitive Type.
     * If {@code type} is not primitive, returns itself.
     *
     * @param type the primitive type
     * @return the corresponding Wrapper class
     */
    private static Class<?> wrapperClassFor(final Class<?> type) {
        assert !type.isPrimitive() || primitiveToWrapper.containsKey(type);

        if (!type.isPrimitive()) return type;
        return primitiveToWrapper.get(type);
    }

    /**
     * Instantiates a class without calling its constructor.
     *
     * @param type The class to be instantiated
     * @param <T> The type of the instance
     * @return an instance of {@code <T>}
     */
    public static <T> T softInstantiate(Class<T> type) {
        return softInstantiate(type, Object.class);
    }

    /**
     * Instantiates a class without calling its constructor.
     *
     * Nevertheless, the constructor of the supertype is executed.
     *
     * @param type The class to be instantiated
     * @param parent The parent class whose constructor will be called
     * @param <T> The type of the instance
     * @return an instance of {@code <T>}
     */
    public static <T> T softInstantiate(Class<T> type, Class<? super T> parent) {
        try {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor parentConstructor = parent.getDeclaredConstructor();
            Constructor softConstructor = rf.newConstructorForSerialization(type, parentConstructor);
            return type.cast(softConstructor.newInstance());

        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }
}
