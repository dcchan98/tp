package jimmy.mcgymmy.logic.parser;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import jimmy.mcgymmy.commons.core.index.Index;
import jimmy.mcgymmy.commons.util.FileUtil;
import jimmy.mcgymmy.commons.util.StringUtil;
import jimmy.mcgymmy.logic.parser.exceptions.ParseException;
import jimmy.mcgymmy.model.date.Date;
import jimmy.mcgymmy.model.food.Carbohydrate;
import jimmy.mcgymmy.model.food.Fat;
import jimmy.mcgymmy.model.food.Name;
import jimmy.mcgymmy.model.food.Protein;
import jimmy.mcgymmy.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Gets integer from string value.
     *
     * @param value        String containing value of nutrient.
     * @param errorMessage String containing the error message.
     * @return Integer value of string.
     * @throws ParseException if the value is invalid.
     */
    private static int getNutrientValue(String value, String errorMessage) throws ParseException {
        requireNonNull(value);
        String trimmedValue = value.trim();
        int nutrientValue;
        try {
            nutrientValue = Integer.parseInt(trimmedValue);
        } catch (NumberFormatException numberFormatException) {
            throw new ParseException(errorMessage);
        }
        return nutrientValue;
    }

    /**
     * Parses a {@code String protein} into a {@code Protein}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Protein} is invalid.
     */
    public static Protein parseProtein(String protein) throws ParseException {
        int proteinValue = getNutrientValue(protein, Protein.MESSAGE_CONSTRAINTS);
        return new Protein(proteinValue);
    }


    /**
     * Parses a {@code String carb} into an {@code Carbohydrate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code carb} is invalid.
     */
    public static Carbohydrate parseCarb(String carb) throws ParseException {
        int carbValue = getNutrientValue(carb, Carbohydrate.MESSAGE_CONSTRAINTS);
        return new Carbohydrate(carbValue);
    }

    /**
     * Parses a {@code String fat} into an {@code Fat}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code fat} is invalid.
     */
    public static Fat parseFat(String fat) throws ParseException {
        int fatValue = getNutrientValue(fat, Fat.MESSAGE_CONSTRAINTS);
        return new Fat(fatValue);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        try {
            return new Tag(trimmedTag);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String filepath} into an {@code File}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code filepath} is invalid.
     */
    public static Path parseFile(String filepath) throws ParseException {

        //If filepath is invalid or does not exist throw an exception
        if (!FileUtil.isValidPath(filepath)) {
            throw new ParseException(String.format("Invalid Filepath %s", filepath));
        }

        if (!FileUtil.isFileExists(Path.of(filepath))) {
            throw new ParseException(String.format("File at %s does not exist", filepath));
        }

        //Return Path object of file
        return Path.of(filepath);
    }

    /**
     * Parses {@Code String directory} into a {@code File}.
     *
     * @param directory Directory to store the file.
     * @return Path containing directoryPath.
     * @throws ParseException if directory does not exist.
     */
    public static Path parseDir(String directory) throws ParseException {
        //Create the directory
        Path path = Path.of(directory);
        File file = new File(directory);
        if (!file.exists()) {
            throw new ParseException(String.format("Directory does not exist %s", path.toString()));
        }
        return path;
    }

    /**
     * Parse {@code String outputName} into a {@code String}.
     *
     * @param outputPath Name to save the output file.
     * @return outputPath as string.
     * @throws ParseException when outputPath is empty.
     */
    public static String parseOutputName(String outputPath) throws ParseException {
        if (outputPath.trim().equals("")) {
            throw new ParseException("Filename cannot be empty");
        }
        if (!outputPath.contains(".json")) {
            outputPath += ".json";
        }
        return outputPath;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return new Date(trimmedDate);
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Helper function for commons-cli's HelpFormatter.
     * Generates the usage string from commons-cli options.
     *
     * @param commandName name of the command.
     * @param header extra text to be included before usage statement.
     * @param options commons-cli options to format.
     * @return usage string.
     */
    public static String getUsageFromHelpFormatter(String commandName, String header, Options options) {
        HelpFormatter formatter = new HelpFormatter();
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        formatter.printHelp(
            printWriter,
            formatter.getWidth(),
            commandName,
            header,
            options,
            formatter.getLeftPadding(),
            formatter.getDescPadding(),
            "");
        return stringWriter.toString();
    }

    /**
     * Because Java does not support tuples/pairs.
     * Also a ton of boilerplate because module guidelines
     * doesn't let me use public variables even here.
     */
    public static class HeadTailString {
        // mandated private fields from the Church of OOP.
        private final String head;
        private final String[] tail;

        // package private because it can only be created here
        HeadTailString(String head, String[] tail) {
            this.head = head;
            this.tail = tail;
        }

        /**
         * Splits a string using the delimiter,
         * storing the first string as the head, and the rest as the tail.
         *
         * @param input raw input string.
         * @param delimiter Java regex string to split the string by.
         * @return HeadTailString object which is essentially a pair (String, String[]) but with Java cruft.
         */
        public static HeadTailString splitString(String input, String delimiter) {
            try {
                String[] headTail = input.split(delimiter);
                return new HeadTailString(headTail[0], Arrays.copyOfRange(headTail, 1, headTail.length));
            } catch (ArrayIndexOutOfBoundsException e) {
                return new HeadTailString("", new String[]{""});
            }
        }

        /**
         * Splits a string by whitespaces,
         * storing the first string as the head, and the rest as the tail.
         *
         * @param input raw input string.
         * @return HeadTailString object which is essentially a pair (String, String[]) but with Java cruft.
         */
        public static HeadTailString splitString(String input) {
            return HeadTailString.splitString(input, "\\s+");
        }

        public String getHead() {
            return head;
        }

        public String[] getTail() {
            return tail;
        }
    }
}
