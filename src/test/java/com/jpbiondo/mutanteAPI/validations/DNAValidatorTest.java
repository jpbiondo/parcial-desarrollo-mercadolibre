package com.jpbiondo.mutanteAPI.validations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DNAValidatorTest {
    private final DnaValidator dnaValidator = new DnaValidator();

    @Test
    void testNullDna() {
        Assertions.assertFalse(dnaValidator.isValid(null, null));
    }

    @Test
    void testEmptyDna() {
        String dna[] = {};
        Assertions.assertFalse(dnaValidator.isValid(dna, null));
    }

    @Test
    void testNotNxNDnaMatrix() {
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAG"
        };

        Assertions.assertFalse(dnaValidator.isValid(dna, null));
    }

    @Test
    void testNxNDnaMatrixValid() {
        String[] dna = {
                "AAGA",
                "AAGC",
                "ATGC",
                "AAGA"
        };

        Assertions.assertTrue(dnaValidator.isValid(dna, null));
    }

    @Test
    void testNxNDnaMatrixInvalidChars() {
        String[] dna = {
                "AAGA",
                "AAGB",
                "ATGC",
                "AAGA"
        };

        Assertions.assertFalse(dnaValidator.isValid(dna, null));
    }

    @Test
    void testNullDnaMatrixInvalid() {
        String[] dna = {
                null, null,  null
        };
        Assertions.assertFalse(dnaValidator.isValid(dna, null));
    }
}
