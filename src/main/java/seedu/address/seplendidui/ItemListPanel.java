package seedu.address.seplendidui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.SeplendidDataType;
import seedu.address.model.localcourse.LocalCourse;

/**
 * Panel containing the list of items.
 * @param <T> The type of items in the list.
 */
public class ItemListPanel<T extends SeplendidDataType> extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ItemListPanel.class);

    @FXML
    private ListView<T> itemListView;
    private ItemDetailPanel<T> itemDetailPanel;

    /**
     * Creates a {@code ListPanel} with the given {@code ObservableList}.
     */
    public ItemListPanel(ObservableList<T> itemList, ItemDetailPanel<T> itemDetailPanel) {
        super(FXML);
        this.itemDetailPanel = itemDetailPanel;
        itemListView.setItems(itemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell<T>());

        // Add selection listener to update the ItemDetailPanel
        itemListView.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Item} using a {@code ItemCard}.
     */
    class ItemListViewCell<T extends SeplendidDataType> extends ListCell<T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LocalCourseItem((LocalCourse) item, getIndex() + 1).getRoot());
            }
        }
    }


    /**
     * Show the details of the selected item in the ItemDetailPanel.
     * @param item The selected item
     */
    private void showItemDetails(T item) {
        itemDetailPanel.setItemDetail(item);
    }
}
