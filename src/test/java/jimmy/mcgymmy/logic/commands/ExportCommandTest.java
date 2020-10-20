package jimmy.mcgymmy.logic.commands;

import static jimmy.mcgymmy.logic.commands.CommandTestUtil.assertCommandFailure;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.logic.parser.CommandParserTestUtil.OptionalParameterStub;
import jimmy.mcgymmy.logic.parser.CommandParserTestUtil.ParameterStub;
import jimmy.mcgymmy.model.Model;
import jimmy.mcgymmy.model.ModelManager;

class ExportCommandTest {

    @Test
    public void directoryNotFoundError() {
        ExportCommand exportCommand = new ExportCommand();
        ParameterStub<Path> parameterStub = new ParameterStub<Path>("", Path.of("c:\\Invalid String"));
        OptionalParameterStub<String> optionalParameterStub = new OptionalParameterStub<String>("o", "mcgymmy.json");
        exportCommand.setPathParameter(parameterStub, optionalParameterStub);
        Model model = new ModelManager();
        assertCommandFailure(exportCommand, model, String.format(ExportCommand.MESSAGE_FAILURE, "c:\\Invalid String"));
    }

    @Test
    public void invalidOutputName() {
        ExportCommand exportCommand = new ExportCommand();
        ParameterStub<Path> parameterStub = new ParameterStub<Path>("", Path.of("c:\\Invalid String"));
        OptionalParameterStub<String> optionalparameterStub = new OptionalParameterStub<String>("o", "");
        exportCommand.setPathParameter(parameterStub, optionalparameterStub);
        Model model = new ModelManager();
        assertCommandFailure(exportCommand, model, String.format(ExportCommand.MESSAGE_FAILURE, "c:\\Invalid String"));
    }

}
