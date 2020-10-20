package jimmy.mcgymmy.logic.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import jimmy.mcgymmy.logic.commands.exceptions.CommandException;
import jimmy.mcgymmy.logic.parser.ParserUtil;
import jimmy.mcgymmy.logic.parser.parameter.OptionalParameter;
import jimmy.mcgymmy.logic.parser.parameter.Parameter;
import jimmy.mcgymmy.model.Model;
import jimmy.mcgymmy.storage.JsonMcGymmyStorage;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String SHORT_DESCRIPTION = "Export McGymmy save file to directory";
    public static final String MESSAGE_SUCCESS = "Exported to %s";
    public static final String MESSAGE_FAILURE = "File failed to save to %s";
    public static final String DEFAULT_FILENAME = "/mcgymmy.json";

    private Parameter<Path> pathParameter = addParameter(
            "directoryPath",
            "",
            "Path to directory to save McGymmy file",
            "C:/mcgymmy",
            ParserUtil::parseDir
    );

    private OptionalParameter<String> outputFileName = addOptionalParameter(
            "outputName",
            "o",
            "Output name of McGymmy file",
            "mcgymmy.json",
            ParserUtil::parseOutputName
    );

    public void setPathParameter(Parameter<Path> pathParameter, OptionalParameter<String> outputFileName) {
        this.pathParameter = pathParameter;
        this.outputFileName = outputFileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //Get the path parameter
        Path path = pathParameter.consume();
        String filename = outputFileName.getValue().orElseGet(() -> DEFAULT_FILENAME);
        Logger.getLogger("ExportCommand").info(String.format("Directory Selected: %s", path.toString()));

        //Check if the directory exists
        File file = new File(path.toString());
        if (!file.exists()) {
            Logger.getLogger("Export Command").warning("Directory does not exist");
            throw new CommandException(String.format(MESSAGE_FAILURE, path.toString()));
        }

        //Add the mcgymmy filename to export
        path = Paths.get(path.toString(), filename);
        JsonMcGymmyStorage mcGymmyStorage = new JsonMcGymmyStorage(path);
        try {
            mcGymmyStorage.saveMcGymmy(model.getMcGymmy());
        } catch (IOException e) {
            Logger.getLogger("Export Command").warning(e.toString());
            throw new CommandException(String.format(MESSAGE_FAILURE, path.toString()));
        }

        //Return the successful commandResult
        return new CommandResult(String.format(MESSAGE_SUCCESS, path.toString()));
    }
}
