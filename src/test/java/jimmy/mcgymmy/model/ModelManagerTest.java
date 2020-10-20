package jimmy.mcgymmy.model;

import static jimmy.mcgymmy.testutil.Assert.assertThrows;
import static jimmy.mcgymmy.testutil.TypicalFoods.CHICKEN_RICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.commons.core.GuiSettings;
import jimmy.mcgymmy.logic.predicate.NameContainsKeywordsPredicate;
import jimmy.mcgymmy.testutil.McGymmyBuilder;
import jimmy.mcgymmy.testutil.TypicalFoods;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new McGymmy(), new McGymmy(modelManager.getMcGymmy()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setMcGymmyFilePath(Paths.get("jimmy/mcgymmy/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setMcGymmyFilePath(Paths.get("new/jimmy/mcgymmy/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setMcGymmyFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setMcGymmyFilePath(null));
    }

    @Test
    public void setMcGymmyFilePath_validPath_setsMcGymmyFilePath() {
        Path path = Paths.get("jimmy/mcgymmy/file/path");
        modelManager.setMcGymmyFilePath(path);
        assertEquals(path, modelManager.getMcGymmyFilePath());
    }

    @Test
    public void hasFood_nullFood_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFood(null));
    }

    @Test
    public void hasFood_personNotInMcGymmy_returnsFalse() {
        assertFalse(modelManager.hasFood(CHICKEN_RICE));
    }

    @Test
    public void hasFood_personInMcGymmy_returnsTrue() {
        modelManager.addFood(CHICKEN_RICE);
        assertTrue(modelManager.hasFood(CHICKEN_RICE));
    }

    @Test
    public void getFilteredFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFoodList().remove(0));
    }

    @Test
    public void equals() {
        McGymmy mcGymmy = new McGymmyBuilder().withFood(TypicalFoods.CHICKEN_RICE).withFood(
                TypicalFoods.NASI_LEMAK).build();
        McGymmy differentMcGymmy = new McGymmy();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(mcGymmy, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(mcGymmy, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different mcGymmy -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentMcGymmy, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = CHICKEN_RICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredFoodList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(mcGymmy, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFoodList(Model.PREDICATE_SHOW_ALL_FOODS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setMcGymmyFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(mcGymmy, differentUserPrefs)));
    }
}
