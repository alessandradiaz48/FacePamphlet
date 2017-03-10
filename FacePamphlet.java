/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements
		FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, (getWidth() - message.getWidth()) / 2, getHeight()
				- BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		displayName(profile);
		// The Y coordinate for the image, created so that it may be used in
		// relation to the location of other objects
		double yCoorRect = TOP_MARGIN + displayName(profile).getHeight()
				+ IMAGE_MARGIN;
		displayImage(profile, yCoorRect);
		displayStatus(profile, yCoorRect);
		displayFriends(profile, yCoorRect);
	}

	/**
	 * This method creates the label that displays the name of the current
	 * profile.
	 */
	private GLabel displayName(FacePamphletProfile profile) {
		GLabel name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.BLUE);
		double yCoor = TOP_MARGIN + name.getHeight();
		add(name, LEFT_MARGIN, yCoor);
		return name;
	}

	/**
	 * This method displays the profile image for the current profile. If not
	 * image has been assigned, then a blank rectangle is displayed with the
	 * message "No Image." If an image has been chosen, then it is displayed as
	 * the profile image.
	 */
	private void displayImage(FacePamphletProfile profile, double yCoorRect) {
		if (profile.getImage() == null) {
			GRect imageSpace = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(imageSpace, LEFT_MARGIN, yCoorRect);
			GLabel imageLabel = new GLabel("No Image");
			imageLabel.setFont(PROFILE_IMAGE_FONT);
			double xCoorLabel = LEFT_MARGIN + (IMAGE_WIDTH / 2)
					- (imageLabel.getWidth() / 2);
			double yCoorLabel = yCoorRect + (IMAGE_HEIGHT / 2)
					+ (imageLabel.getHeight() / 2);
			add(imageLabel, xCoorLabel, yCoorLabel);
		} else {
			GImage image = profile.getImage();
			image.scale(IMAGE_WIDTH / image.getWidth(),
					IMAGE_HEIGHT / image.getHeight());
			add(image, LEFT_MARGIN, yCoorRect);
		}
	}

	/**
	 * This method displays the status of the current profile. When a status has
	 * been added, then is it displayed. If no status has been added, then the
	 * program displays an according message.
	 */
	private GLabel displayStatus(FacePamphletProfile profile, double yCoorRect) {
		String status = "";
		if (profile.getStatus().equals("")) {
			status = "No Current Status";
		} else {
			status += profile.getName() + " is " + profile.getStatus();
		}
		GLabel statusLabel = new GLabel(status);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		double yCoorStatus = yCoorRect + IMAGE_HEIGHT + STATUS_MARGIN;
		add(statusLabel, LEFT_MARGIN, yCoorStatus);
		return statusLabel;
	}

	/**
	 * This method displays the friend of the current profile. First, a bold
	 * label that says profile is shown. Next, the names of the current
	 * profile's friends are shown below, stacked in a column of friends' names.
	 */
	private void displayFriends(FacePamphletProfile profile, double yCoorRect) {
		GLabel friendLabel = new GLabel("Friends: ");
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendLabel, getWidth() / 2, yCoorRect);
		// A counter is used to determine which number friend has been added so
		// that the Y position of its label may adjust accordingly
		int counter = 1;
		Iterator<String> iterator = profile.getFriends();
		while (iterator.hasNext()) {
			String nextFriend = iterator.next();
			GLabel friend = new GLabel(nextFriend);
			friend.setFont(PROFILE_FRIEND_FONT);
			// The Y coordinate changes based on the counter so that the names
			// aren't placed on top of each other
			add(friend, getWidth() / 2,
					yCoorRect + (counter * friend.getHeight()));
			counter++;
		}
	}
}
