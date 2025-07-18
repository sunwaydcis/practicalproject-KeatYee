package ch.makery.address

import ch.makery.address.model.Person
import ch.makery.address.view.PersonEditDialogController
import javafx.fxml.FXMLLoader
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes.*
import javafx.scene as jfxs
import scalafx.beans.property.StringProperty
import scalafx.collections.ObservableBuffer
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp3:

  //Window Root Pane
  var roots: Option[scalafx.scene.layout.BorderPane] = None

  /**
   * The data as an observable list of Persons.
   */
  val personData = new ObservableBuffer[Person]()

  /**
   * Constructor
   */
  personData += new Person("Hans", "Muster")
  personData += new Person("Ruth", "Mueller")
  personData += new Person("Heinz", "Kurz")
  personData += new Person("Cornelia", "Meier")
  personData += new Person("Werner", "Meyer")
  personData += new Person("Lydia", "Kunz")
  personData += new Person("Anna", "Best")
  personData += new Person("Stefan", "Meier")
  personData += new Person("Martin", "Mueller")


  override def start(): Unit =
    // transform path of RootLayout.fxml to URI for resource location.
    val rootResource = getClass.getResource("view/RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(rootResource)
    // Load root layout from fxml file.
    loader.load()

    // retrieve the root component BorderPane from the FXML
    roots = Option(loader.getRoot[jfxs.layout.BorderPane])

    stage = new PrimaryStage():
      title = "AddressApp"
      scene = new Scene():
        root = roots.get

    // call to display PersonOverview when app start
    showPersonOverview()

  // actions for display person overview window
  def showPersonOverview(): Unit =
    val resource = getClass.getResource("view/PersonOverview.fxml")
    val loader = new FXMLLoader(resource)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.get.center = roots

  //these are not a string, these are the objects that contain String
  val stringA = new StringProperty("hello") //publisher
  val stringB = new StringProperty("sunway") //subscriber
  val stringC = new StringProperty("sunway") //subscriber

  stringA.value = "world"
  stringB <==> stringA //can call both setter
  stringC <== stringA //can only set value A

  stringB.value = "google"
  //stringC.value = "cctv" // this is wrong
  println(stringB())

  //onChange function, need to understand the parameters inside, search it online how it looks like
  stringA.onChange { (_, oldValue, newValue) =>
    println(s"stringA changed from $oldValue to $newValue")
  }

  stringA.value = "world"

  def showPersonEditDialog(person: Person): Boolean =
    val resource = getClass.getResource("view/PersonEditDialog.fxml")
    val loader = new FXMLLoader(resource)
    loader.load();
    val roots2 = loader.getRoot[jfxs.Parent] //write anchor pane also can, just make sure assign to the most outside one
    val control = loader.getController[PersonEditDialogController]

    val dialog = new Stage():
      initModality(Modality.ApplicationModal) //dialog stay on top of the window only
      initOwner(stage)
      scene = new Scene:
        root = roots2

    control.dialogStage = dialog
    control.person = person
    dialog.showAndWait()
    control.okClicked
