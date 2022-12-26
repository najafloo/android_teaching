package com.shahpar.unittesttraining.lib;

import junit.framework.TestCase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;
import java.time.Duration;
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

    @ParameterizedTest
    @DisplayName("Should return false when diet not recommended")
    @ValueSource(doubles = {89.0, 95.0, 110.0})
    void should_ReturnFalse_When_DietNotRecommended1(Double coderWeight) {
        // given
        double weight = coderWeight;
        double height = 1.72;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        Assertions.assertTrue(recommended);
    }

    @ParameterizedTest(name = "weight={0} , height={1}")
    @DisplayName("Should return false when diet not recommended with more value")
    @CsvSource(value = {"89.0,1.72","95.0,1.75","110.0,1.78"})
    void should_ReturnFalse_When_DietNotRecommended2(Double coderWeight, Double coderHeight) {
        // given
        double weight = coderWeight;
        double height = coderHeight;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        Assertions.assertTrue(recommended);
    }

    @ParameterizedTest(name = "weight={0} , height={1}")
    @DisplayName("Should return false when diet not recommended with more value read data from CSV file")
    @CsvFileSource(resources = "/014 diet-recommended-input-data.csv", numLinesToSkip = 1)
    void should_ReturnFalse_When_DietNotRecommended3(Double coderWeight, Double coderHeight) {
        // given
        double weight = coderWeight;
        double height = coderHeight;

        // when
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // then
        Assertions.assertTrue(recommended);
    }

    @Test
    void Should_ReturnCoderWithWorstBMI1Ms_WhenListHas10000Elements() {
        // given
        List<Coder> coders = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            coders.add(new Coder(1.0 + i, 10.0 + i));
        }

        // when
        Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

        //then
        Assertions.assertTimeout(Duration.ofMillis(10), executable);
    }
}
