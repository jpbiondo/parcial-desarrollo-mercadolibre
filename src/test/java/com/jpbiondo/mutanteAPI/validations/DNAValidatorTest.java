package com.jpbiondo.mutanteAPI.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DNAValidatorTest {
    private DnaValidator dnaValidator = new DnaValidator();

    @Test
    void testNullDna() {
        Assertions.assertEquals(false, dnaValidator.isValid(null, null));
    }

    @Test
    void testEmptyDna() {
        String dna[] = {};
        Assertions.assertEquals(false, dnaValidator.isValid(dna, null));
    }

    @Test
    void testNotNxNDnaMatrix() {
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAG"
        };

        Assertions.assertEquals(false, dnaValidator.isValid(dna, null));
    }

    @Test
    void testNxNDnaMatrixValid() {
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAGA"
        };

        Assertions.assertEquals(true, dnaValidator.isValid(dna, null));
    }

    @Test
    void testNxNDnaMatrixInvalidChars() {
        String[] dna = {
                "AAGA",
                "AAGB",
                "ATGC",
                "AAGA"
        };

        Assertions.assertEquals(false, dnaValidator.isValid(dna, null));
    }
}
