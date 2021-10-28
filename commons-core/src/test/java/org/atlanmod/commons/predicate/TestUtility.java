package org.atlanmod.commons.predicate;

import org.atlanmod.commons.Preconditions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

public class TestUtility {
    static void throwsPreconditionError(Executable executable) {
        Assertions.assertThrows(Preconditions.PreconditionError.class, executable);
    }
}
