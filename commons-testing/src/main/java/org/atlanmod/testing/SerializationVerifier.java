package org.atlanmod.testing;

import org.atlanmod.commons.reflect.MoreReflection;

import java.io.*;
import java.util.function.Function;
import java.util.stream.Stream;



public class SerializationVerifier<T extends Serializable> {
    private Class<T> type;
    private Object[] arguments;
    private  static  final  long serialVersionUID = 1623437;

    SerializationVerifier(Class<T> type) {
        this.type = type;
    }


    public SerializationVerifier<T> withArguments(Object... arguments) {
        this.arguments = arguments;
        return this;
    }

    private static Class[] mapToClasses(Object[] objects) {
        return Stream.of(objects)
                .map(Object::getClass)
                .toArray(Class[]::new);
    }

    public void check() throws IOException, ClassNotFoundException {

        Class[] argumentTypes = mapToClasses(arguments);
        Function<Object[], T> instantiator = MoreReflection.getInstantiator(type, argumentTypes);
        Object object = instantiator.apply(arguments);

        // serialiser object
        ByteArrayOutputStream boos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(boos);
        oos.writeObject(object);

        //deserialiser object
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(boos.toByteArray()));
        Object object2 = (Object) ois.readObject();

        System.out.println(object2);


        assertIsEqual(object,object2);

    }

    public static void assertIsEqual(Object one, Object other) {
        if (!one.equals(other)) {
            throw new AssertionError("Expecting objects to be equal");
        } else if (!other.equals(one)) {
            throw new AssertionError("Equals is supposed to be symmetric");
        } else if (one.hashCode() != other.hashCode()) {
            throw new AssertionError("Equal objects must have the same hash code");
        }
    }
}
