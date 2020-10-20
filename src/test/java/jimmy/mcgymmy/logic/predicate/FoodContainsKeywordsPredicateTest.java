package jimmy.mcgymmy.logic.predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.model.food.Name;
import jimmy.mcgymmy.testutil.FoodBuilder;

class FoodContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FoodContainsKeywordsPredicate firstPredicate = new FoodContainsKeywordsPredicate(firstPredicateKeywordList);
        FoodContainsKeywordsPredicate secondPredicate = new FoodContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FoodContainsKeywordsPredicate firstPredicateCopy = new FoodContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different food -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameFoundTagNotFound_returnsTrue() {
        FoodContainsKeywordsPredicate predicate =
                new FoodContainsKeywordsPredicate(Arrays.asList("Chicken", "Dinner"));
        assertTrue(predicate.test(new FoodBuilder().withName(new Name("Chicken McWing")).withTags("Lunch").build()));
    }

    @Test
    public void test_nameFoundTagFound_returnsTrue() {
        FoodContainsKeywordsPredicate predicate =
                new FoodContainsKeywordsPredicate(Arrays.asList("Chicken", "Dinner"));
        assertTrue(predicate.test(new FoodBuilder().withName(new Name("Chicken McWing")).withTags("Dinner").build()));
    }

    @Test
    public void test_nameNotFoundTagFound_returnsTrue() {
        FoodContainsKeywordsPredicate predicate =
                new FoodContainsKeywordsPredicate(Arrays.asList("Chicken", "Dinner"));
        assertTrue(predicate.test(new FoodBuilder().withName(new Name("Big Mac")).withTags("Dinner").build()));
    }

    @Test
    public void test_nameNotFoundTagNotFound_returnsFalse() {
        FoodContainsKeywordsPredicate predicate =
                new FoodContainsKeywordsPredicate(Arrays.asList("Chicken", "Lunch"));
        assertFalse(predicate.test(new FoodBuilder().withName(new Name("Big Mac")).withTags("Dinner").build()));
    }
}
