package org.atlanmod.testing;

import java.io.Serializable;
import java.util.stream.Stream;
import org.atlanmod.commons.reflect.MoreReflection;
import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Stream;
import static org.atlanmod.testing.EqualsVerifier.*;


public class SerializationVerifier<T extends Serializable> {
    private Class<T> type;
    private Object[] arguments;
    private  static  final  long serialVersionUID = 1623437;


    public SerializationVerifier(Class<T> type ){
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
        String filename =  LocalDateTime.now().toString() + ".atser";
        File fichier =  new File(filename);
        ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(fichier));
        oos.writeObject(object);

        //deserialiser object
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(filename)) ;
        Object object2 = (Object)ois.readObject() ;
        System.out.println(object2);

        // verifier que l'objet deserialiser contient toute les informations initiales stockÃ© dans la variable object
        assertIsEqual(object2, object);
    }




}
