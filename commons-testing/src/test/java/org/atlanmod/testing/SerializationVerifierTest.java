package org.atlanmod.testing;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SerializationVerifierTest {

    @Test
    void test() throws IOException, ClassNotFoundException {
        Verifier.verifySerializer(Integer.class)
                .withArguments(11110000)
                .check();
    }


}
