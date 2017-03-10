
/*
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */
 
import acm.program.*;
import acm.graphics.*;
import acm.util.*;
 
import java.awt.event.*;
 
import javax.swing.*;
 
public class FacePamphlet extends Program implements FacePamphletConstants {
 
    /** The label that shows what should be entered in the JTextField */
    private JLabel nameLabel;
 
    /** The text field where you type in names */
    private JTextField enterNameField;
 
    /** The button that adds profiles */
    private JButton addButton;
 
    /** The button that deletes profiles */
    private JButton deleteButton;
 
    /** The button that looks up profiles */
    private JButton lookupButton;
 
    /** The text field where you can enter a desired status */
    private JTextField changeStatusField;
 
    /** The button that updates the status */
    private JButton changeStatusButton;
 
    /** The text field where you can assign an image */
    private JTextField changePicField;
 
    /** The button that changes the picture for a profile */
    private JButton changePicButton;
 
    /** The text field where you enter the friend you want to add */
    private JTextField addFriendField;
 
    /** The button that adds friends */
    private JButton addFriendButton;
 
    /**
     * The class FacePampheletDatabase, so that its methods may be accessed here
     */
    private FacePamphletDatabase database;
 
    /** The class FacePamphletCanvas so that its methods may be accessed here */
    private FacePamphletCanvas canvas;
 
    /**
     * The String of the name of the current profile, which is assigned based on
     * the situation (a profile added, deleted, looked up)
     */
    private String currentProfileName;
 
    /**
     * This method has the responsibility for initializing the interactors in
     * the application, and taking care of any other initialization that needs
     * to be performed.
     */
    public void init() {
        initializeNorthObjects();
        initializeWestObjects();
        addActionListeners();
        database = new FacePamphletDatabase();
        // Created the canvas
        canvas = new FacePamphletCanvas();
        add(canvas);
    }
 
    /**
     * This method creates all the objects for the northern margin of the
     * screen, including the name label, the text field for searching names, and
     * the buttons to add, delete, and look up a profile.
     */
    private void initializeNorthObjects() {
        // Creates the label that shows what should be entered in the text field
        nameLabel = new JLabel("Name");
        add(nameLabel, NORTH);
        // Adds the text field for entering names
        enterNameField = new JTextField(TEXT_FIELD_SIZE);
        add(enterNameField, NORTH);
        // Allows for the field to respond to the enter button
        enterNameField.addActionListener(this);
        addButton = new JButton("Add");
        add(addButton, NORTH);
        deleteButton = new JButton("Delete");
        add(deleteButton, NORTH);
        lookupButton = new JButton("Lookup");
        add(lookupButton, NORTH);
    }
 
    /**
     * This method creates all the objects on the western margin of the
     * application. This includes the text files and buttons pertaining to
     * adding a friend, adding/changing a profile picture, and adding/updating a
     * profile's status.
     */
    private void initializeWestObjects() {
        // Objects related to changing the status
        changeStatusField = new JTextField(TEXT_FIELD_SIZE);
        add(changeStatusField, WEST);
        changeStatusField.addActionListener(this);
        changeStatusButton = new JButton("Change Status");
        add(changeStatusButton, WEST);
        // This creates a space between the Change Status button and the Change
        // Picture text field.
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
 
        // Objects related to changing the image
        changePicField = new JTextField(TEXT_FIELD_SIZE);
        add(changePicField, WEST);
        changePicField.addActionListener(this);
        changePicButton = new JButton("Change Picture");
        add(changePicButton, WEST);
        add(new JLabel(EMPTY_LABEL_TEXT), WEST);
 
        // Objects related to adding a friend
        addFriendField = new JTextField(TEXT_FIELD_SIZE);
        add(addFriendField, WEST);
        addFriendField.addActionListener(this);
        addFriendButton = new JButton("Add Friend");
        add(addFriendButton, WEST);
    }
 
    /**
     * This class is responsible for detecting when the buttons are clicked or
     * interactors are used, so you will have to add code to respond to these
     * actions.
     */
    public void actionPerformed(ActionEvent e) {
        // Clears the canvas each time so that the canvas shows the most updated
        // version of a profile
        canvas.removeAll();
        String nameEntered = enterNameField.getText();
        // None of these actions may be called if no name has been entered
        if (!nameEntered.equals("")) {
            // When the add button is clicked
            if (e.getSource() == addButton) {
                // If the profile you want to add hasn't already been added
                if (!database.containsProfile(nameEntered)) {
                    FacePamphletProfile profile = new FacePamphletProfile(
                            nameEntered);
                    // When a new profile is created, it is the current profile
                    currentProfileName = nameEntered;
                    database.addProfile(profile);
                    canvas.showMessage("New profile created");
                } else {
                    currentProfileName = nameEntered;
                    canvas.showMessage("A profile with the name "
                            + currentProfileName + " already exists.");
                }
                // Updates the display
                canvas.displayProfile(database.getProfile(currentProfileName));
            }
            // When the delete button is clicked
            if (e.getSource() == deleteButton) {
                // Checks if the profile you want to delete exists
                if (database.containsProfile(nameEntered) == true) {
                    // Removes profile
                    database.deleteProfile(nameEntered);
                    canvas.showMessage("Profile of name " + nameEntered
                            + " deleted");
                    // When the profile is deleted, the canvas is blank (with
                    // the exception of the message at the bottom), and there is
                    // no current profile
                    currentProfileName = null;
                    // If the profile you want to delete doesn't exist, then
                    // there is still no current profile and an according
                    // message is displayed
                } else {
                    canvas.showMessage("A profile with the name " + nameEntered
                            + " does not exist");
                    currentProfileName = null;
                }
            }
            // When the lookup button is clicked
            if (e.getSource() == lookupButton) {
                // Makes sure that the name you search for exists in the
                // database
                if (database.containsProfile(nameEntered) == true) {
                    // Set as the current profile
                    currentProfileName = nameEntered;
                    // Displays the profile and the message for the current
                    // profile (the one that was looked up)
                    canvas.displayProfile(database
                            .getProfile(currentProfileName));
                    canvas.showMessage("Displaying " + currentProfileName);
                } else {
                    // A blank canvas is shown with the according message if you
                    // search for a name that doesn't exist
                    canvas.showMessage("A profile with the name " + nameEntered
                            + " does not exist");
                    currentProfileName = null;
                }
            }
        }
        // Executes the commands for adding a friend, adding an image, and
        // adding a status
        executeWestButtons(e);
    }
 
    /**
     * This method executes the actions for adding friends, images, and
     * statuses. The commands are called are called either by clicking the
     * desired button or by clicking the enter button.
     */
    private void executeWestButtons(ActionEvent e) {
        executeChangeStatusButton(e);
        executeChangePictureButton(e);
        executeAddFriendButton(e);
    }
 
    /**
     * This method executes the commands for changing the status. If there is a
     * current profile, then the status entered becomes the status for the
     * profile at hand, and the new profile is displayed on the canvas. The
     * message at the bottom of the screen displays the actions taken.
     */
    private void executeChangeStatusButton(ActionEvent e) {
        String statusEntered = changeStatusField.getText();
        if (currentProfileName != null) {
            // Checks if a status has been entered in the text field
            if (!statusEntered.equals("")) {
                if (e.getSource() == changeStatusButton
                        || e.getSource() == changeStatusField) {
                    database.getProfile(currentProfileName).setStatus(
                            statusEntered);
                    canvas.displayProfile(database
                            .getProfile(currentProfileName));
                    canvas.showMessage("Status updated to " + statusEntered);
                }
            }
            // If there is no current profile, then the according message
            // displays
        } else {
            if (!statusEntered.equals("")) {
                if (e.getSource() == changeStatusButton
                        || e.getSource() == changeStatusField) {
                    canvas.showMessage("Please select a profile to change status");
                }
            }
        }
    }
 
    /**
     * This method executes the commands for changing the picture. If there is a
     * current profile and a valid picture file has been entered, then that will
     * be stored as the image for the profile and will be displayed on the
     * canvas.
     */
    private void executeChangePictureButton(ActionEvent e) {
        String pictureEntered = changePicField.getText();
        if (currentProfileName != null) {
            if (!pictureEntered.equals("")) {
                if (e.getSource() == changePicButton
                        || e.getSource() == changePicField) {
                    GImage image = null;
                    try {
                        image = new GImage(pictureEntered);
                        // Adding the image to the profile
                        database.getProfile(currentProfileName).setImage(image);
                        // Message showing the action performed
                        canvas.showMessage("Picture updated");
                        // For an invalid picture file, no image is shown and
                        // the according message is displayed.
                    } catch (ErrorException ex) {
                        image = null;
                        canvas.showMessage("Unable to open image file: "
                                + pictureEntered);
                    }
                    // Displays the updated canvas
                    canvas.displayProfile(database
                            .getProfile(currentProfileName));
                }
            }
            // If there is no current profile, then an according message is
            // displayed
        } else {
            if (!pictureEntered.equals("")) {
                if (e.getSource() == changePicButton
                        || e.getSource() == changePicField) {
                    canvas.showMessage("Please select a profile to change image");
                }
            }
        }
    }
 
    /**
     * This method executes the actions for the add friend button. If there is a
     * current profile and a name is entered to the addFriend text field, and
     * the friend you want to add is within the database (but this friend you
     * want to add cannot be the profile of the person adding the friend), then
     * the friend is added to that profile's list of friends.
     */
    private void executeAddFriendButton(ActionEvent e) {
        String friendEntered = addFriendField.getText();
        if (currentProfileName != null) {
            if (!friendEntered.equals("")) {
                if (e.getSource() == addFriendButton
                        || e.getSource() == addFriendField) {
                    if (database.containsProfile(friendEntered) == true
                            && !friendEntered.equals(currentProfileName)) {
                        // Checks if the name you want to add is a valid profile
                        // and that the profile isn't trying to add itself as a
                        // friend (If you try to add yourself as a friend, it
                        // will act the same way as if you were trying to add an
                        // nonexistent profile)
                        if (database.getProfile(currentProfileName).addFriend(
                                friendEntered) == true) {
                            database.getProfile(currentProfileName).addFriend(
                                    friendEntered);
                            canvas.displayProfile(database
                                    .getProfile(currentProfileName));
                            // Displays the action perfomed
                            canvas.showMessage(friendEntered
                                    + " added as a friend");
                            // If the friend does not have the person who added
                            // them as a friend, then the person that added them
                            // is added to their list of friends (dealing with
                            // reciprocal friendships)
                            if (database.getProfile(friendEntered).addFriend(
                                    currentProfileName)) {
                                database.getProfile(friendEntered).addFriend(
                                        currentProfileName);
                            }
                            // Shows message if the friend you want to add has
                            // already been added
                        } else {
                            canvas.showMessage(currentProfileName
                                    + " already has " + friendEntered
                                    + " as a friend");
                        }
                        // Shows message if you want to add an invalid profile
                    } else {
                        canvas.showMessage(friendEntered + " does not exist");
                    }
                }
            }
            // If you are trying to add a friend to without a current profile,
            // an according message will display
        } else {
            if (!friendEntered.equals("")) {
                if (e.getSource() == addFriendButton
                        || e.getSource() == addFriendField) {
                    canvas.showMessage("Please select a profile to add friend");
                }
            }
        }
    }
}
