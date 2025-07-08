package ch.makery.address.view
import ch.makery.address.model.Person
import ch.makery.address.MainApp
import javafx.fxml.FXML
import javafx.scene.control.{Label, TableColumn, TableView}
import scalafx.Includes.*
import javafx.scene.control.TextField
import ch.makery.address.util.DateUtil.*
import javafx.event.ActionEvent
import scalafx.beans.binding.Bindings
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
@FXML
class PersonOverviewController():
  @FXML
  private var personTable: TableView[Person] = null
  @FXML
  private var firstNameColumn: TableColumn[Person, String] = null
  @FXML
  private var lastNameColumn: TableColumn[Person, String] = null
  @FXML
  private var firstNameLabel: Label = null
  @FXML
  private var lastNameLabel: Label = null
  @FXML
  private var streetLabel: Label = null
  @FXML
  private var postalCodeLabel: Label = null
  @FXML
  private var cityLabel: Label = null
  @FXML
  private var birthdayLabel: Label = null
  @FXML
  private var myText: TextField = null

  // initialize Table View display contents model
  def initialize() =
    personTable.items = MainApp.personData
    // initialize columns's cell values
    firstNameColumn.cellValueFactory = {_.value.firstName}
    lastNameColumn.cellValueFactory  = {_.value.lastName}

    firstNameLabel.text <== myText.text
    showPersonDetails(None) //put it here to make sure all ady assign value b4 call this func

    personTable.selectionModel().selectedItem.onChange(
      (_, _, newValue) => showPersonDetails(Option(newValue))
    ) //call option because value could be null

  private def showPersonDetails(person: Option[Person]): Unit =
    person match
      case Some(person) =>
        // Fill the labels with info from the person object.
        firstNameLabel.text <== person.firstName
        lastNameLabel.text <== person.lastName
        streetLabel.text <== person.street
        cityLabel.text <== person.city;
        postalCodeLabel.text <== person.postalCode.delegate.asString
        birthdayLabel.text <== Bindings.createStringBinding(()=>{
          person.date.value.asString
        },person.date)
      //ps:string prop cannot bind directly with object prop, that's why we do all these for birthday and postal code
      //string prop is a string in an object
      case None =>
        // Person is null, remove all the text.
        firstNameLabel.text.unbind()
        lastNameLabel.text.unbind()
        streetLabel.text.unbind()
        postalCodeLabel.text.unbind()
        cityLabel.text.unbind()
        birthdayLabel.text.unbind()

        firstNameLabel.text = ""
        lastNameLabel.text = ""
        streetLabel.text = ""
        postalCodeLabel.text = ""
        cityLabel.text = ""
        birthdayLabel.text = ""

  /**
   * Called when the user clicks on the delete button.
   */
  //This action is working with the fxml so it can put the tag here
  @FXML
  def handleDeletePerson(action: ActionEvent) =
    val selectedIndex = personTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) then
      personTable.items().remove(selectedIndex)
    //u can also:
    //MainApp.personData.remove(selectedIndex);
    else
      // Nothing selected.
      val alert = new Alert(AlertType.Warning):
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Person Selected"
        contentText = "Please select a person in the table."
      alert.showAndWait()


