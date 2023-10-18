package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.university.University;

public interface ReadOnlyUniversityCatalogue {

    ObservableList<University> getUniversityList();

}
