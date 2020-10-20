package jimmy.mcgymmy.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import jimmy.mcgymmy.commons.core.Messages;
import jimmy.mcgymmy.logic.parser.parameter.Parameter;
import jimmy.mcgymmy.logic.predicate.FoodContainsKeywordsPredicate;
import jimmy.mcgymmy.model.Model;

/**
 * Finds and lists all persons in mcgymmy whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {
    public static final String COMMAND_WORD = "find";
    public static final String SHORT_DESCRIPTION = "Filter the displayed list by a given keyword.";

    private Parameter<FoodContainsKeywordsPredicate> predicateParameter = this.addParameter(
            "keyword",
            "",
            "keywords (case-insensitive).",
            "chicken beef mutton", (s) -> new FoodContainsKeywordsPredicate(Arrays.asList(s.split("\\s+")))
    );

    void setParameters(Parameter<FoodContainsKeywordsPredicate> predicateParameter) {
        this.predicateParameter = predicateParameter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        FoodContainsKeywordsPredicate predicate = predicateParameter.consume();
        model.updateFilteredFoodList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_FOOD_LISTED_OVERVIEW, model.getFilteredFoodList().size()));
    }
}
