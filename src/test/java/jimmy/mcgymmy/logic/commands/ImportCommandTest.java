package jimmy.mcgymmy.logic.commands;

import static jimmy.mcgymmy.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.logic.parser.CommandParserTestUtil.ParameterStub;
import jimmy.mcgymmy.model.McGymmy;
import jimmy.mcgymmy.model.ModelManager;
import jimmy.mcgymmy.testutil.TypicalFoods;

/**
 * Invalid Commands are not tested as they are checked in the parser.
 */
class ImportCommandTest {

    private static final String VALID_FILE = Paths
            .get("src", "test", "data", "JsonSerializableMcGymmyTest", "typicalFoodMcGymmy.json").toString();

    @Test
    public void validImport_success() {
        ModelManager model1 = new ModelManager();
        ParameterStub<Path> fileParameterStub = new ParameterStub<Path>(
                "",
                Path.of(VALID_FILE)
        );
        ImportCommand importCommand = new ImportCommand();
        importCommand.setParameters(fileParameterStub);

        //Create Expected model
        ModelManager model = new ModelManager();
        McGymmy mcGymmy = new McGymmy();
        mcGymmy.setFoodItems(TypicalFoods.getTypicalFoodItems());
        model.setMcGymmy(mcGymmy);

        assertCommandSuccess(importCommand, model1,
                String.format(ImportCommand.MESSAGE_IMPORT_FOOD_SUCCESS, "typicalFoodMcGymmy.json"), model);

    }
}
