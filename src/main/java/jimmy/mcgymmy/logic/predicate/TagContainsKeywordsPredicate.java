package jimmy.mcgymmy.logic.predicate;

import java.util.List;
import java.util.function.Predicate;

import jimmy.mcgymmy.model.food.Food;
import jimmy.mcgymmy.model.tag.Tag;

/**
 * Tests that a {@code Food}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Food> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Check if the food contains the tag with exactly the same name as one of the keyword (case sensitive)
     * @param food The food to be checked
     * @return True if can create a tag with the keyword and Food contains the tag equals to that tag, false otherwise
     */
    @Override
    public boolean test(Food food) {
        return keywords.stream()
                .anyMatch(keyword -> {
                    try {
                        return food.getTags().contains(new Tag(keyword));
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
