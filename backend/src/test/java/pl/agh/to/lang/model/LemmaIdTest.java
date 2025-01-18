package pl.agh.to.lang.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LemmaIdTest {

    @Test
    void testLemmaIdEquality() {
        LemmaId id1 = new LemmaId();
        id1.setName("run");
        id1.setLanguage("en");

        LemmaId id2 = new LemmaId();
        id2.setName("run");
        id2.setLanguage("en");

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    void testLemmaIdInequality() {
        LemmaId id1 = new LemmaId();
        id1.setName("run");
        id1.setLanguage("en");

        LemmaId id2 = new LemmaId();
        id2.setName("walk");
        id2.setLanguage("en");

        assertNotEquals(id1, id2);
    }
}
