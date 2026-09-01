package com.example.demo1;

import com.example.demo1.User.User;
import com.example.demo1.User.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;

public class HomePage{
    public User user;
    @FXML
    private Tab HomePageProfile;

    @FXML
    private Label HomePageUserName;

    @FXML
    void onHomePageProfileClicked(ActionEvent event) {
        HomePageUserName.setText(UserDAO.users.get(UserDAO.whichUser).getUsername());
    }
}
