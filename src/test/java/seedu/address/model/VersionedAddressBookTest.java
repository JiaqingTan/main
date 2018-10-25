package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalGuests.AMY;
import static seedu.address.testutil.TypicalGuests.BOB;
import static seedu.address.testutil.TypicalGuests.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ConciergeBuilder;

public class VersionedConciergeTest {

    private final ReadOnlyConcierge addressBookWithAmy = new ConciergeBuilder().withGuest(AMY).build();
    private final ReadOnlyConcierge addressBookWithBob = new ConciergeBuilder().withGuest(BOB).build();
    private final ReadOnlyConcierge addressBookWithCarl = new ConciergeBuilder().withGuest(CARL).build();
    private final ReadOnlyConcierge emptyConcierge = new ConciergeBuilder().build();

    @Test
    public void commit_singleConcierge_noStatesRemovedCurrentStateSaved() {
        VersionedConcierge versionedConcierge = prepareConciergeList(emptyConcierge);

        versionedConcierge.commit();
        assertConciergeListStatus(versionedConcierge,
                Collections.singletonList(emptyConcierge),
                emptyConcierge,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleConciergePointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);

        versionedConcierge.commit();
        assertConciergeListStatus(versionedConcierge,
                Arrays.asList(emptyConcierge, addressBookWithAmy, addressBookWithBob),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleConciergePointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 2);

        versionedConcierge.commit();
        assertConciergeListStatus(versionedConcierge,
                Collections.singletonList(emptyConcierge),
                emptyConcierge,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleConciergePointerAtEndOfStateList_returnsTrue() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);

        assertTrue(versionedConcierge.canUndo());
    }

    @Test
    public void canUndo_multipleConciergePointerAtStartOfStateList_returnsTrue() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 1);

        assertTrue(versionedConcierge.canUndo());
    }

    @Test
    public void canUndo_singleConcierge_returnsFalse() {
        VersionedConcierge versionedConcierge = prepareConciergeList(emptyConcierge);

        assertFalse(versionedConcierge.canUndo());
    }

    @Test
    public void canUndo_multipleConciergePointerAtStartOfStateList_returnsFalse() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 2);

        assertFalse(versionedConcierge.canUndo());
    }

    @Test
    public void canRedo_multipleConciergePointerNotAtEndOfStateList_returnsTrue() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 1);

        assertTrue(versionedConcierge.canRedo());
    }

    @Test
    public void canRedo_multipleConciergePointerAtStartOfStateList_returnsTrue() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 2);

        assertTrue(versionedConcierge.canRedo());
    }

    @Test
    public void canRedo_singleConcierge_returnsFalse() {
        VersionedConcierge versionedConcierge = prepareConciergeList(emptyConcierge);

        assertFalse(versionedConcierge.canRedo());
    }

    @Test
    public void canRedo_multipleConciergePointerAtEndOfStateList_returnsFalse() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);

        assertFalse(versionedConcierge.canRedo());
    }

    @Test
    public void undo_multipleConciergePointerAtEndOfStateList_success() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);

        versionedConcierge.undo();
        assertConciergeListStatus(versionedConcierge,
                Collections.singletonList(emptyConcierge),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void undo_multipleConciergePointerNotAtStartOfStateList_success() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 1);

        versionedConcierge.undo();
        assertConciergeListStatus(versionedConcierge,
                Collections.emptyList(),
                emptyConcierge,
                Arrays.asList(addressBookWithAmy, addressBookWithBob));
    }

    @Test
    public void undo_singleConcierge_throwsNoUndoableStateException() {
        VersionedConcierge versionedConcierge = prepareConciergeList(emptyConcierge);

        assertThrows(VersionedConcierge.NoUndoableStateException.class, versionedConcierge::undo);
    }

    @Test
    public void undo_multipleConciergePointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 2);

        assertThrows(VersionedConcierge.NoUndoableStateException.class, versionedConcierge::undo);
    }

    @Test
    public void redo_multipleConciergePointerNotAtEndOfStateList_success() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 1);

        versionedConcierge.redo();
        assertConciergeListStatus(versionedConcierge,
                Arrays.asList(emptyConcierge, addressBookWithAmy),
                addressBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleConciergePointerAtStartOfStateList_success() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 2);

        versionedConcierge.redo();
        assertConciergeListStatus(versionedConcierge,
                Collections.singletonList(emptyConcierge),
                addressBookWithAmy,
                Collections.singletonList(addressBookWithBob));
    }

    @Test
    public void redo_singleConcierge_throwsNoRedoableStateException() {
        VersionedConcierge versionedConcierge = prepareConciergeList(emptyConcierge);

        assertThrows(VersionedConcierge.NoRedoableStateException.class, versionedConcierge::redo);
    }

    @Test
    public void redo_multipleConciergePointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedConcierge versionedConcierge = prepareConciergeList(
                emptyConcierge, addressBookWithAmy, addressBookWithBob);

        assertThrows(VersionedConcierge.NoRedoableStateException.class, versionedConcierge::redo);
    }

    @Test
    public void equals() {
        VersionedConcierge versionedConcierge = prepareConciergeList(addressBookWithAmy, addressBookWithBob);

        // same values -> returns true
        VersionedConcierge copy = prepareConciergeList(addressBookWithAmy, addressBookWithBob);
        assertTrue(versionedConcierge.equals(copy));

        // same object -> returns true
        assertTrue(versionedConcierge.equals(versionedConcierge));

        // null -> returns false
        assertFalse(versionedConcierge.equals(null));

        // different types -> returns false
        assertFalse(versionedConcierge.equals(1));

        // different state list -> returns false
        VersionedConcierge differentConciergeList = prepareConciergeList(addressBookWithBob, addressBookWithCarl);
        assertFalse(versionedConcierge.equals(differentConciergeList));

        // different current pointer index -> returns false
        VersionedConcierge differentCurrentStatePointer = prepareConciergeList(
                addressBookWithAmy, addressBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedConcierge, 1);
        assertFalse(versionedConcierge.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedConcierge} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedConcierge#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedConcierge#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertConciergeListStatus(VersionedConcierge versionedConcierge,
                                             List<ReadOnlyConcierge> expectedStatesBeforePointer,
                                             ReadOnlyConcierge expectedCurrentState,
                                             List<ReadOnlyConcierge> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Concierge(versionedConcierge), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedConcierge.canUndo()) {
            versionedConcierge.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyConcierge expectedConcierge : expectedStatesBeforePointer) {
            assertEquals(expectedConcierge, new Concierge(versionedConcierge));
            versionedConcierge.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyConcierge expectedConcierge : expectedStatesAfterPointer) {
            versionedConcierge.redo();
            assertEquals(expectedConcierge, new Concierge(versionedConcierge));
        }

        // check that there are no more states after pointer
        assertFalse(versionedConcierge.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedConcierge.undo());
    }

    /**
     * Creates and returns a {@code VersionedConcierge} with the {@code addressBookStates} added into it, and the
     * {@code VersionedConcierge#currentStatePointer} at the end of list.
     */
    private VersionedConcierge prepareConciergeList(ReadOnlyConcierge... addressBookStates) {
        assertFalse(addressBookStates.length == 0);

        VersionedConcierge versionedConcierge = new VersionedConcierge(addressBookStates[0]);
        for (int i = 1; i < addressBookStates.length; i++) {
            versionedConcierge.resetData(addressBookStates[i]);
            versionedConcierge.commit();
        }

        return versionedConcierge;
    }

    /**
     * Shifts the {@code versionedConcierge#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedConcierge versionedConcierge, int count) {
        for (int i = 0; i < count; i++) {
            versionedConcierge.undo();
        }
    }
}
