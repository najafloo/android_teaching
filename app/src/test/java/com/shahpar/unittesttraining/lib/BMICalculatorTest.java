package com.shahpar.unittesttraining.lib;

import junit.framework.TestCase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BMICalculatorTest extends TestCase {

    @DisplayName("Test is diest recommended is True")
    @Test
    public void testIsDietRecommended() {
        // given
        double w = 89.0;
        double h = 1.72;

        // when
        boolean recommended = BMICalculator.isDietRecommended(w, h);

        // then
        Assertions.assertTrue(recommended);
    }

    @DisplayName("Test is diet recommended is False")
    @Test
    public void test_should_returnFalse_When_DietRecommended() {
        // given
        double w = 50.0;
        double h = 1.92;

        // when
        boolean recommended = BMICalculator.isDietRecommended(w, h);

        // then
        Assertions.assertFalse(recommended);
    }

    @DisplayName("Should return coder with BMI when coder list not empty")
    @Test
    public void should_ReturnCoderWithBMI_When_CoderListNotEmpty() {

        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.83, 64.7));

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> Assertions.assertEquals(98.0, coderWorstBMI.getWeight())
        );

    }

    @DisplayName("Should return coder with BMI when coder list Null")
    @Test
    public void should_ReturnCoderWithBMI_When_CoderListNull() {

        // given
        List<Coder> coders = new ArrayList<>();

        // when
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // then
        Assertions.assertNull(coderWorstBMI);
    }

    @DisplayName("Should return correct BMI score loist when coder list not empty")
    @Test
    public void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {

        // given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));
        double[] expected = {18.52, 29.59, 19.53};

        // when
        double[] bmiScores = BMICalculator.getBMIScores(coders);

        // then
        Assertions.assertArrayEquals(expected, bmiScores);
    }
}
