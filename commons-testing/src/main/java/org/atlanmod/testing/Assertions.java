package org.atlanmod.testing;

import org.atlanmod.commons.Throwables;
import org.atlanmod.commons.annotation.Static;
import org.atlanmod.commons.predicate.*;

import javax.annotation.ParametersAreNonnullByDefault;

@Static
@ParametersAreNonnullByDefault
public class Assertions {
    private final static PredicateContext CONTEXT = new AssertionContext();

    private Assertions() {
        throw Throwables.notInstantiableClass(getClass());
    }

    public static BooleanPredicate assertThat(boolean expression) {
        return new BooleanPredicate(CONTEXT, expression);
    }

    public static IntPredicate assertThat(int expression) {
        return new IntPredicate(CONTEXT, expression);
    }

    public static LongPredicate assertThat(long expression) {
        return new LongPredicate(CONTEXT, expression);
    }

    public static StringPredicate assertThat(String expression) {
        return new StringPredicate(CONTEXT, expression);
    }

    public static ObjectPredicate<ObjectPredicate, Object> assertThat(Object expression) {
        return new ObjectPredicate(CONTEXT, expression);
    }

    static class AssertionContext implements PredicateContext {
        @Override
        public void send(String pattern, Object... args) {
            throw new AssertionError(String.format(pattern, args));
        }
    }
}
