package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GUEST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Concierge;
import seedu.address.model.Model;
import seedu.address.model.guest.Guest;
import seedu.address.model.guest.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditGuestDescriptorBuilder;
import seedu.address.testutil.TypicalRoomNumbers;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_TAG_HANDICAP = "handicap";
    public static final String VALID_ROOM_NUMBER_AMY =
            TypicalRoomNumbers.ROOM_NUMBER_010.value;
    public static final String VALID_ROOM_NUMBER_BOB =
            TypicalRoomNumbers.ROOM_NUMBER_002.value;
    public static final String VALID_ROOM_NUMBER_001 =
            TypicalRoomNumbers.ROOM_NUMBER_001.value;
    public static final String VALID_ROOM_NUMBER_020 =
            TypicalRoomNumbers.ROOM_NUMBER_020.value;
    public static final Integer VALID_CAPACITY_SINGLE = 1;
    public static final Integer VALID_CAPACITY_DOUBLE = 2;
    public static final Integer VALID_CAPACITY_SUITE = 5;
    public static final String VALID_DATE_START_AMY = "14/11/2018";
    public static final String VALID_DATE_END_AMY = "17/11/2018";
    public static final String VALID_DATE_START_BOB = "03/11/2018";
    public static final String VALID_DATE_END_BOB = "05/11/2018";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ROOM_DESC_001 =
            " " + PREFIX_ROOM + " " + VALID_ROOM_NUMBER_001;
    public static final String ROOM_DESC_020 =
            " " + PREFIX_ROOM + " " + VALID_ROOM_NUMBER_020;
    public static final String ROOM_DESC_AMY = " " + PREFIX_ROOM + " " + VALID_ROOM_NUMBER_AMY;
    public static final String ROOM_DESC_BOB = " " + PREFIX_ROOM + " " + VALID_ROOM_NUMBER_BOB;
    public static final String DATE_START_DESC_AMY =
            " " + PREFIX_DATE_START + " " + VALID_DATE_START_AMY;
    public static final String DATE_END_DESC_AMY =
            " " + PREFIX_DATE_END + " " + VALID_DATE_END_AMY;
    public static final String DATE_START_DESC_BOB =
            " " + PREFIX_DATE_START + " " + VALID_DATE_START_BOB;
    public static final String DATE_END_DESC_BOB =
            " " + PREFIX_DATE_END + " " + VALID_DATE_END_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_ROOM_DESC = " " + PREFIX_ROOM + "101"; // rooms only valid from 001 to 100.
    public static final String INVALID_INDEX_DESC = " " + PREFIX_INDEX + "-1"; // index has to be a counting number
    public static final String INVALID_DATE_START_DESC =
            " " + PREFIX_DATE_START + "138213"; // date has to be in dd/MM/yyyy format
    public static final String INVALID_DATE_END_DESC =
            " " + PREFIX_DATE_END + "33/33/3333"; // invalid month format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditGuestDescriptor DESC_AMY;
    public static final EditCommand.EditGuestDescriptor DESC_BOB;

    public static final String VALID_FLAG_ROOM = PREFIX_ROOM.toString();
    public static final String VALID_FLAG_GUEST = PREFIX_GUEST.toString();

    static {
        DESC_AMY = new EditGuestDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail. Failure reason: " + ce.getMessage(), ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered guest list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Concierge expectedConcierge = new Concierge(actualModel.getConcierge());
        List<Guest> expectedFilteredList = new ArrayList<>(actualModel.getFilteredGuestList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedConcierge, actualModel.getConcierge());
            assertEquals(expectedFilteredList, actualModel.getFilteredGuestList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the guest at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showGuestAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGuestList().size());

        Guest guest = model.getFilteredGuestList().get(targetIndex.getZeroBased());
        final String[] splitName = guest.getName().fullName.split("\\s+");
        model.updateFilteredGuestList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredGuestList().size());
    }

    /**
     * Deletes the first guest in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstGuest(Model model) {
        Guest firstGuest = model.getFilteredGuestList().get(0);
        model.deleteGuest(firstGuest);
        model.commitConcierge();
    }

}
