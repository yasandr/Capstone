package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SerializationVerifierTest {

    @Test
    void withArguments() {
    }

    @Test
    void check() throws IOException, ClassNotFoundException {
        new SerializationVerifier(String.class)
                .withArguments("ibou")
                .check();
    }

    @Test
    void assertIsEqual() {
    }
}