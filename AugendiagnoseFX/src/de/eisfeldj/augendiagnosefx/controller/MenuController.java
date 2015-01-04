package de.eisfeldj.augendiagnosefx.controller;

import java.util.ArrayList;
import java.util.List;

import de.eisfeldj.augendiagnosefx.util.DialogUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

/**
 * Controller class for the menu.
 */
public class MenuController implements Controller {
	/**
	 * The main menu bar.
	 */
	@FXML
	private MenuBar menuBar;

	/**
	 * The Menu entry "Close".
	 */
	@FXML
	private MenuItem menuClose;

	@Override
	public final Parent getRoot() {
		return menuBar;
	}

	/**
	 * A reference to the main controller.
	 */
	private MainController mainController;

	/**
	 * Instantiate main controller.
	 *
	 * @param controller
	 *            The main controller.
	 */
	public final void setMainController(final MainController controller) {
		if (mainController == null) {
			mainController = controller;
		}
		else {
			throw new UnsupportedOperationException("Illegal second instantiation of main controller.");
		}
	}

	/**
	 * A list storing the handlers for closing windows.
	 */
	private List<EventHandler<ActionEvent>> closeHandlerList = new ArrayList<EventHandler<ActionEvent>>();

	/**
	 * Handler for menu entry "Exit".
	 *
	 * @param event
	 *            The action event.
	 */
	@FXML
	protected final void exitApplication(final ActionEvent event) {
		Platform.exit();
	}

	/**
	 * Handler for menu entry "Preferences".
	 *
	 * @param event
	 *            The action event.
	 */
	@FXML
	public final void showPreferences(final ActionEvent event) {
		DialogUtil.displayPreferencesDialog();
	}

	/**
	 * Enable the close menu item.
	 *
	 * @param eventHandler
	 *            The event handler to be called when closing.
	 */
	public final void enableClose(final EventHandler<ActionEvent> eventHandler) {
		menuClose.setDisable(false);
		closeHandlerList.add(eventHandler);
		menuClose.setOnAction(eventHandler);

		mainController.getCloseButton().setVisible(true);
		mainController.getCloseButton().setOnAction(eventHandler);
	}

	/**
	 * Disable one level of the close menu item.
	 */
	public final void disableClose() {
		closeHandlerList.remove(closeHandlerList.size() - 1);
		if (closeHandlerList.size() > 0) {
			EventHandler<ActionEvent> newEventHandler = closeHandlerList.get(closeHandlerList.size() - 1);
			menuClose.setOnAction(newEventHandler);
			mainController.getCloseButton().setOnAction(newEventHandler);
		}
		else {
			menuClose.setDisable(true);
			mainController.getCloseButton().setVisible(false);
		}
	}

	/**
	 * Disable all levels of the close menu icon.
	 */
	public final void disableAllClose() {
		closeHandlerList.clear();
		menuClose.setDisable(true);
		mainController.getCloseButton().setVisible(false);
	}

}
