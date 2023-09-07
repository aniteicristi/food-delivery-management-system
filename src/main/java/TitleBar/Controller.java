package TitleBar;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The title bar controller.
 */
public class Controller
{


    /**
     * Arithmetic inner class for computing the position of the frame and the amount of drag when the user drags it.
     */
    class Delta { double x, y; }
    final Delta dragDelta = new Delta();

    /**
     * References to the main stage (for binding to the close and minimize functions)
     * and to the main controller (also known as the main controller)
     */
    private Stage mainStage;


    /**
     * Setter for the mainStage.
     * @param stage the stage we want to assign to our reference.
     */
    public void setStage(Stage stage){
        mainStage = stage;
    }


    /**
     * The minimize function bound to the minimize button
     */
    @FXML private void minimize()
    {
        mainStage.setIconified(true);
    }

    /**
     * The minimize function bound to the maximize button
     */
    @FXML private void maximize() {
        if(mainStage.isMaximized()){
            mainStage.setHeight(800);
            mainStage.setWidth(1200);
            mainStage.setMaximized(false);
        }
        else
            mainStage.setMaximized(true);
    }

    /**
     * The close function bound to the title-bar close button.
     */
    @FXML private void close()
    {
        mainStage.close();
    }

    /**
     * The bound method when someone clicks on the title-bar. We save the coordinates of the main stage.
     * @param mouseEvent the event containing the information.
     */
    @FXML private void pressed(MouseEvent mouseEvent){
        dragDelta.x = mainStage.getX() - mouseEvent.getScreenX();
        dragDelta.y = mainStage.getY() - mouseEvent.getScreenY();
    }

    /**
     * The bound method when someone drags the mouse accross the screen. Our app will follow the direction by changing its position
     * according to the cursor.
     * @param mouseEvent the object containing the event information.
     */
    @FXML private void dragged(MouseEvent mouseEvent){
        mainStage.setX(mouseEvent.getScreenX() + dragDelta.x);
        mainStage.setY(mouseEvent.getScreenY() + dragDelta.y);
    }

}
