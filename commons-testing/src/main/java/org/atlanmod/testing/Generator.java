package org.atlanmod.testing;

public interface Generator <T>  {

    T generate() ;
    Class<T>[] types();

}
