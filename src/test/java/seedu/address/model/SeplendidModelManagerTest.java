package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.SeplendidModel.PREDICATE_SHOW_ALL_LOCAL_COURSES;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.CS2030S;
import static seedu.address.testutil.TypicalObjects.CS2040S;
import static seedu.address.testutil.TypicalObjects.CS3230;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.testutil.LocalCourseCatalogueBuilder;

public class SeplendidModelManagerTest {

    private SeplendidModelManager modelManager = new SeplendidModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new LocalCourseCatalogue(), new LocalCourseCatalogue(modelManager.getLocalCourseCatalogue()));
        // Developers note: Add for appropriate catalogues
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        // TBD: remove when morph is done
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setLocalCourseCatalogueFilePath(Paths.get("local/course/catalogue/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setLocalCourseCatalogueFilePath(Paths.get("local/course/catalogue/file/path"));
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
    public void setLocalCourseCatalogueFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setLocalCourseCatalogueFilePath(null));
    }

    @Test
    public void setLocalCourseCatalogueFilePath_validPath_setsLocalCourseCatalogueFilePath() {
        Path path = Paths.get("local/course/catalogue/file/path");
        modelManager.setLocalCourseCatalogueFilePath(path);
        assertEquals(path, modelManager.getLocalCourseCatalogueFilePath());
    }

    @Test
    public void hasLocalCourse_nullLocalCourse_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasLocalCourse(null));
    }

    @Test
    public void hasLocalCourse_personNotInLocalCourseCatalogue_returnsFalse() {
        assertFalse(modelManager.hasLocalCourse(CS3230));
    }

    @Test
    public void hasLocalCourse_personInLocalCourseCatalogue_returnsTrue() {
        modelManager.addLocalCourse(CS3230);
        assertTrue(modelManager.hasLocalCourse(CS3230));
    }

    @Test
    public void getFilteredLocalCourseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredLocalCourseList()
                .remove(0));
    }

    @Test
    public void equals() {
        LocalCourseCatalogue localCourseCatalogue =
                new LocalCourseCatalogueBuilder().withLocalCourse(CS2030S).withLocalCourse(CS2040S).build();
        LocalCourseCatalogue differentLocalCourseCatalogue = new LocalCourseCatalogue();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new SeplendidModelManager(localCourseCatalogue, userPrefs);
        SeplendidModelManager modelManagerCopy = new SeplendidModelManager(localCourseCatalogue, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different localCourseCatalogue -> returns false
        assertFalse(modelManager.equals(new SeplendidModelManager(differentLocalCourseCatalogue, userPrefs)));

        // different filteredList -> returns false
        modelManager.updateFilteredLocalCourseList(unused -> false);
        assertFalse(modelManager.equals(new SeplendidModelManager(localCourseCatalogue, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredLocalCourseList(PREDICATE_SHOW_ALL_LOCAL_COURSES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLocalCourseCatalogueFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new SeplendidModelManager(localCourseCatalogue, differentUserPrefs)));
    }
}
