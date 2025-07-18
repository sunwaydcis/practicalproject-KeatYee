package ch.makery.address.view
import javafx.event.ActionEvent
import javafx.fxml.FXML
import ch.makery.address.MainApp


@FXML
class RootLayoutController():
  def handleClose(action: ActionEvent):Unit =
    MainApp.stage.close()