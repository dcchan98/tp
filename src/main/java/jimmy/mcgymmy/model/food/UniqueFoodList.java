package jimmy.mcgymmy.model.food;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jimmy.mcgymmy.commons.util.CollectionUtil;
import jimmy.mcgymmy.model.food.exceptions.DuplicateFoodException;
import jimmy.mcgymmy.model.food.exceptions.FoodNotFoundException;

/**
 * A list of food items that enforces uniqueness between its elements and does not allow nulls.
 * A food is considered unique by comparing using {@code Food#isSameFood(Food)}. As such, adding and updating of
 * food items uses Food#isSameFood(Food) for equality so as to ensure that the food being added or updated is
 * unique in terms of identity in the UniqueFoodList. However, the removal of a food uses Food#equals(Object) so
 * as to ensure that the food with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Food#equals(Object)
 */
public class UniqueFoodList implements Iterable<Food> {

    private final ObservableList<Food> internalList = FXCollections.observableArrayList();
    private final ObservableList<Food> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent food as the given argument.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a food to the list.
     * The food must not already exist in the list.
     */
    public void add(Food toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateFoodException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the food {@code target} in the list with {@code editedFood}.
     * {@code target} must exist in the list.
     * The food identity of {@code editedFood} must not be the same as another existing food in the list.
     */
    public void setFood(Food target, Food editedFood) {
        CollectionUtil.requireAllNonNull(target, editedFood);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new FoodNotFoundException();
        }

        if (!target.equals(editedFood) && contains(editedFood)) {
            throw new DuplicateFoodException();
        }

        internalList.set(index, editedFood);
    }

    /**
     * Removes the equivalent food from the list.
     * The food must exist in the list.
     */
    public void remove(Food toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new FoodNotFoundException();
        }
    }

    public void setFoodItems(UniqueFoodList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code foods}.
     * {@code foods} must not contain duplicate foods.
     */
    public void setFoodItems(List<Food> foods) {
        CollectionUtil.requireAllNonNull(foods);
        if (!foodItemsAreUnique(foods)) {
            throw new DuplicateFoodException();
        }

        internalList.setAll(foods);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Food> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Food> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueFoodList // instanceof handles nulls
                && internalList.equals(((UniqueFoodList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code foods} contains only unique foods.
     */
    private boolean foodItemsAreUnique(List<Food> foods) {
        for (int i = 0; i < foods.size() - 1; i++) {
            for (int j = i + 1; j < foods.size(); j++) {
                if (foods.get(i).equals(foods.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
