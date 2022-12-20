package com.shahpar.unittesttraining.lib;

import junit.framework.TestCase;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DietPlannerTest extends TestCase {

    private DietPlanner dietPlanner;

    @BeforeEach
    void setup() {
        this.dietPlanner = new DietPlanner(20, 30, 50);
    }

    @AfterEach
    void afterEach() {
        System.out.println("******* Unit DietPlaner test finished");
    }

    @DisplayName("Should return correct diets plan when correct coder")
    @Test
    void testCorrectDiets() {

        // given
        Coder coder = new Coder(1.82, 75.0, 26, Gender.MALE);
        DietPlan expected = new DietPlan(2202, 110, 73, 275);

        // when
        DietPlan actual = dietPlanner.calculateDiet(coder);

        // then

//        Assertions.assertEquals(expected, actual); NOT ALLOWED FOR CLASSES

        Assertions.assertAll(
                () -> Assertions.assertEquals(expected.getCalories(), actual.getCalories()),
                () -> Assertions.assertEquals(expected.getProtein(), actual.getProtein()),
                () -> Assertions.assertEquals(expected.getFat(), actual.getFat()),
                () -> Assertions.assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate())
        );
    }
}