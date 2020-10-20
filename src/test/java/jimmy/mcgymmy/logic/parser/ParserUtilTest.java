package jimmy.mcgymmy.logic.parser;

import static jimmy.mcgymmy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jimmy.mcgymmy.logic.parser.exceptions.ParseException;
import jimmy.mcgymmy.model.food.Carbohydrate;
import jimmy.mcgymmy.model.food.Fat;
import jimmy.mcgymmy.model.food.Name;
import jimmy.mcgymmy.model.food.Protein;
import jimmy.mcgymmy.model.tag.Tag;
import jimmy.mcgymmy.testutil.TypicalIndexes;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PROTEIN = "+651%)234";
    private static final String INVALID_FAT = " ";
    private static final String INVALID_CARB = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PROTEIN = "123456";
    private static final String VALID_FAT = "1230505";
    private static final String VALID_CARB = "2134";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String VALID_FILE = Paths
            .get("src", "test", "data", "JsonSerializableMcGymmyTest", "typicalFoodMcGymmy.json").toString();
    private static final String INVALID_FILE_1 = Paths
            .get("src", "test", "data", "JsonSerializableMcGymmyTest", "typical.json").toString();
    private static final String INVALID_FILE_2 = Paths
            .get("src", "test", "data", "JsonSerializableMcGymmyTest").toString();

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
                ParserUtil.MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_FOOD, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(TypicalIndexes.INDEX_FIRST_FOOD, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseProtein_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseProtein((String) null));
    }

    @Test
    public void parseProtein_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseProtein(INVALID_PROTEIN));
    }

    @Test
    public void parseProtein_validValueWithoutWhitespace_returnsProtein() throws Exception {
        Protein expectedProtein = new Protein(VALID_PROTEIN);
        assertEquals(expectedProtein, ParserUtil.parseProtein(VALID_PROTEIN));
    }

    @Test
    public void parseProtein_validValueWithWhitespace_returnsTrimmedProtein() throws Exception {
        String proteinWithWhitespace = WHITESPACE + VALID_PROTEIN + WHITESPACE;
        Protein expectedProtein = new Protein(VALID_PROTEIN);
        assertEquals(expectedProtein, ParserUtil.parseProtein(proteinWithWhitespace));
    }

    @Test
    public void parseCarb_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCarb((String) null));
    }

    @Test
    public void parseCarb_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCarb(INVALID_FAT));
    }

    @Test
    public void parseCarb_validValueWithoutWhitespace_returnsCarbohydrate() throws Exception {
        Carbohydrate expectedCarbohydrate = new Carbohydrate(VALID_FAT);
        Assertions.assertEquals(expectedCarbohydrate, ParserUtil.parseCarb(VALID_FAT));
    }

    @Test
    public void parseCarb_validValueWithWhitespace_returnsTrimmedCarbohydrate() throws Exception {
        String carbohydrateWithWhitespace = WHITESPACE + VALID_FAT + WHITESPACE;
        Carbohydrate expectedCarbohydrate = new Carbohydrate(VALID_FAT);
        Assertions.assertEquals(expectedCarbohydrate, ParserUtil.parseCarb(carbohydrateWithWhitespace));
    }

    @Test
    public void parseFat_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseFat((String) null));
    }

    @Test
    public void parseFat_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFat(INVALID_CARB));
    }

    @Test
    public void parseFat_validValueWithoutWhitespace_returnsFat() throws Exception {
        Fat expectedFat = new Fat(VALID_CARB);
        assertEquals(expectedFat, ParserUtil.parseFat(VALID_CARB));
    }

    @Test
    public void parseFat_validValueWithWhitespace_returnsTrimmedFat() throws Exception {
        String fatWithWhitespace = WHITESPACE + VALID_CARB + WHITESPACE;
        Fat expectedFat = new Fat(VALID_CARB);
        assertEquals(expectedFat, ParserUtil.parseFat(fatWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseFile_withInvalidFilePath() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFile(INVALID_FILE_1));
    }

    @Test
    public void parseFile_withFolderPath() {
        assertThrows(ParseException.class, () -> ParserUtil.parseFile(INVALID_FILE_2));
    }

    @Test
    public void parseFile_withValidFilePath() throws ParseException {
        Path path = Paths.get("src", "test", "data",
                "JsonSerializableMcGymmyTest", "typicalFoodMcGymmy.json");
        assertEquals(path, ParserUtil.parseFile(VALID_FILE));
    }
}
