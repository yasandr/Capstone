package org.atlanmod.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class TestUtility {
    static void throwsAssertionError(Executable executable) {
        Assertions.assertThrows(AssertionError.class, executable);
    }
}
