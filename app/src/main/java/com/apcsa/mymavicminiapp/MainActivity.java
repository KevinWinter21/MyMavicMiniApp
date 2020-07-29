package com.apcsa.mymavicminiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    // Store home location of drone
    public double homeX;
    public double homeY;
    public double homeZ;
    public double currentLocX;
    public double currentLocY;
    public double currentLocZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button button;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set all buttons to not clickable until drone is connected.
        disableButton((Button) findViewById(R.id.take_off_land_button));
        disableButton((Button) findViewById(R.id.go_home_button));
        disableButton((Button) findViewById(R.id.reset_home_button));
        // disable take off button until drone and app are connected
        disableButton((Button) findViewById(R.id.rotate_90_cw_button));
        // disable take off button until drone and app are connected
        disableButton((Button) findViewById(R.id.make_5ft_square_button));
        // disable take off button until drone and app are connected
        disableButton((Button) findViewById(R.id.complete_obstacle_course_button));
        // disable take off button until drone and app are connected
        disableButton((Button) findViewById(R.id.take_picture_button));

        ImageView thisView = (ImageView) findViewById(R.id.imageView_check);
        thisView.setVisibility(View.INVISIBLE);  // show the check

    }

    // Connect the app to the drone
    public void onConnectingButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), getString(R.string.connect_to_mini), Toast.LENGTH_LONG);
        myToast.show();

        enableButton((Button) findViewById(R.id.take_off_land_button));

        ImageView thisView = (ImageView) findViewById(R.id.imageView_check);
        thisView.setVisibility(View.VISIBLE);  // show the check

        // reset the home location of drone to (0.0, 0.0, 5.0)
        currentLocX = 0.0;
        currentLocY = 0.0;
        currentLocZ = 0.0;
        setHomeLocation(0.0, 0.0, 5.0); // choose the point above the current drone location,
        // 5 ft off the ground
    }

    public void onTakeOffLandButtonTap(View v) {

        Button thisButton = (Button) findViewById(R.id.take_off_land_button);

        // Determine the next action for the drone based on state of button (take off or landing)
        if (thisButton.getText().equals(getString(R.string.take_off))) {
            Toast myToast = Toast.makeText(getApplicationContext(), "taking off...", Toast.LENGTH_LONG);
            myToast.show();
            // Enable the reset home button and the go home button
            enableButton((Button) findViewById(R.id.reset_home_button));

            // Enable the other buttons now that the drone is in the air
            enableButton((Button) findViewById(R.id.rotate_90_cw_button));
            //tmpButton.setClickable(true);  // enable take off button
            enableButton((Button) findViewById(R.id.make_5ft_square_button));
            //tmpButton.setClickable(true);  // enable take off button
            enableButton((Button) findViewById(R.id.complete_obstacle_course_button));
            //tmpButton.setClickable(true);  // enable take off button
            enableButton((Button) findViewById(R.id.take_picture_button));
            //tmpButton.setClickable(true);  // enable take off button

            // Toggle the name of the button to land.
            thisButton.setText(getString(R.string.land));

        } else {  // Land the drone
            Toast myToast = Toast.makeText(getApplicationContext(), "landing...", Toast.LENGTH_SHORT);
            myToast.show();

            // Disable the go home button since the drone has landed
            disableButton((Button) findViewById(R.id.go_home_button));
            disableButton((Button) findViewById(R.id.reset_home_button));
            // Disable the other buttons now that the drone is on the ground
            disableButton((Button) findViewById(R.id.rotate_90_cw_button));
            // disable take off button until drone and app are connected
            disableButton((Button) findViewById(R.id.make_5ft_square_button));
            // disable take off button until drone and app are connected
            disableButton((Button) findViewById(R.id.complete_obstacle_course_button));
            // disable take off button until drone and app are connected
            disableButton((Button) findViewById(R.id.take_picture_button));

            // Toggle the name of the button to take off.
            thisButton.setText(getString(R.string.take_off));

        }
    }

    // Rotate the drone 90 degrees clockwise (looking from above the drone).
    public void onRotate90CWButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "insert your code here to rotate clockwise, 90 degrees...", Toast.LENGTH_SHORT);
        myToast.show();
    }

    // move in a square with 5ft sides
    public void onMaking5ftSquareButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "insert your code here to move in a 5ft square...", Toast.LENGTH_SHORT);
        myToast.show();
    }

    // Causes the drone to complete the obstacle course
    public void onCompleteObstacleCourseButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "insert your code here to complete the obstacle course...", Toast.LENGTH_SHORT);
        myToast.show();
        isDroneHome(0.0, 0.0, 5.0);
    }

    // Have the drone take a picture.
    public void onTakePictureButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "insert your code here to take a picture...", Toast.LENGTH_SHORT);
        myToast.show();
    }

    // Set the Home location
    public void onResetHomeButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "insert your code here to set home...", Toast.LENGTH_SHORT);
        myToast.show();

        setHomeLocation(0.0, 0.0, 5.0);  // MODIFY, once SDK is imported.
        isDroneHome(getDroneX(), getDroneY(), getDroneZ());  // Check if drone is home and update home button
    }

    // Make the drone go home
    public void onGoHomeButtonTap(View v) {
        Toast myToast = Toast.makeText(getApplicationContext(), "insert your code here to go home...", Toast.LENGTH_SHORT);
        myToast.show();
        isDroneHome(getDroneX(), getDroneY(), getDroneZ());  // Check if drone is home and update home button
    }

    // disable button
    public void disableButton(Button thisButton) {
        thisButton.setEnabled(false);
        thisButton.postInvalidate();  // force a refresh
    }
    // enable button
    public void enableButton(Button thisButton) {
        thisButton.setEnabled(true);
        thisButton.postInvalidate();  // force a refresh
    }

    private void setHomeLocation(double x, double y, double z) {
        homeX = x;
        homeY = y;
        homeZ = z;
    }

    // determine if the drone's position matches the home position
    private boolean isDroneHome(double x, double y, double z) {
        if ((x != homeX) || (y != homeY) || (z != homeZ)) {
            // drone is not at home, enable the return home button
            enableButton((Button) findViewById(R.id.go_home_button));
            return false;
        }
        else {
            // Drone is home, disable the return home button
            disableButton((Button) findViewById(R.id.go_home_button));
            return true;
        }
    }
    // determines the drones location on the x-axis.
    private double getDroneX() {
        return (0.0);  // needs to be modified to get position from drone.
    }
    // determines the drones location on the y-axis.
    private double getDroneY() {
        return (0.0);  // needs to be modified to get position from drone.
    }
    // determines the drones location on the x-axis.
    private double getDroneZ() {
        return (0.0);  // needs to be modified to get position from drone.
    }
}