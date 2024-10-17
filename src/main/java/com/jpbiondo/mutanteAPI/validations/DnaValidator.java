package com.jpbiondo.mutanteAPI.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DnaValidator implements ConstraintValidator<ValidDna, String[]> {
    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext constraintValidatorContext) {
        return dna != null &&
                isNxNDNA(dna) &&
                containsDnaValidChars(dna) &&
                dna.length > 0;
    }

    private boolean isNxNDNA(String[] dna) {
        int dnaRows = dna.length;
        for (String dnaRow : dna) {
            if (dnaRow.length() != dnaRows) return false;
        }
        return true;
    }

    private boolean containsDnaValidChars(String[] dna) {
        final String dnaValidChars = "ACTG";
        for(String dnaRow: dna) {
            for(char dnaChar: dnaRow.toCharArray()) {
                if(dnaValidChars.indexOf(dnaChar) == -1) return false;
            }
        }
        return true;
    }
}
