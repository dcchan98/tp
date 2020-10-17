package jimmy.mcgymmy.model.food;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jimmy.mcgymmy.commons.core.index.Index;
import jimmy.mcgymmy.commons.util.CollectionUtil;
import jimmy.mcgymmy.model.date.Date;
import jimmy.mcgymmy.model.tag.Tag;

/**
 * A list of food items that allows repeated elements and does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class Fridge implements Iterable<Food> {
    private final ObservableList<Food> internalList = FXCollections.observableArrayList();
    private final ObservableList<Food> filteredList = FXCollections.observableArrayList();
    private final ObservableList<Food> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private Optional<Date> optFilterDate = Optional.empty();
    private Optional<Tag> optFilterTag = Optional.empty();

    public void setFilterDate(Date date) {
        Optional<Date> optFilterDate = Optional.of(date);
        this.optFilterDate = optFilterDate;
    }

    public void setFilterTag(Tag tag) {
        Optional<Tag> optFilterTag = Optional.of(tag);
        this.optFilterTag = optFilterTag;
    }

    /**
     * filtering by parameters
     */
    public void filter() {
        internalList.clear();
        for (Food foodItem : internalList) {
            filteredList.add(foodItem);
        }
        if (optFilterTag.isPresent()) {
            filter(optFilterTag.get());
        }
        if (optFilterDate.isPresent()) {
            filter(optFilterDate.get());
        }
    }

    /**
     * Filter filteredList with filterDate
     *
     * @param filterDate desired date to filter
     */
    public void filter(Date filterDate) {
        for (Food currFood : internalList) {
            if (currFood.getDate().equals(filterDate)) {
                filteredList.add(currFood);
            }
        }
    }

    /**
     * Filter filteredList with filterDate
     *
     * @param filterTag desired date to filter
     */
    public void filter(Tag filterTag) {
        for (Food currFood : internalList) {
            if (currFood.getTags().equals(filterTag)) {
                filteredList.add(currFood);
            }
        }
    }

    /**
     * Returns true if the list contains an equivalent food item as the given argument.
     */
    public boolean contains(Food toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a food item to the list.
     */
    public void add(Food toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the food item at the {@code index} position in the list with {@code editedFood}.
     * {@code target} must exist in the list.
     */
    public void setFood(Index index, Food editedFood) {
        CollectionUtil.requireAllNonNull(editedFood, index);
        internalList.set(index.getZeroBased(), editedFood);
    }

    /**
     * Removes the food item at the position index from the list.
     * The food item must exist in the list.
     */
    public void remove(Index removeIndex) {
        requireNonNull(removeIndex);
        internalList.remove(removeIndex.getZeroBased());
    }

    public void setFoods(Fridge replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code foods}.
     */
    public void setFoods(List<Food> foods) {
        CollectionUtil.requireAllNonNull(foods);
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
                || (other instanceof Fridge // instanceof handles nulls
                && internalList.equals(((Fridge) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
