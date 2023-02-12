package com.javagui.gui.controller;

import com.javagui.be.Movie;
import com.javagui.bll.api.ApiService;
import com.javagui.bll.api.IApiService;
import com.javagui.gui.model.AppModel;
import com.javagui.gui.model.CurrentUser;
import com.javagui.gui.model.MovieDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class HomeController extends AbstractController implements Initializable {
    @FXML
    private MFXProgressBar progressBar;
    @FXML
    private MFXScrollPane pane,pane1,pane2;

    private AppModel appModel;

    private IApiService apiService;

    private long timerStartMillis = 0;
    private String timerMsg = "";

    private final CurrentUser currUser = CurrentUser.getInstance();
    private LoginController loginController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         this.apiService = new ApiService();
//        MFXButton logoutBtn = (MFXButton) root.lookup("#navActionBtn");
//        logoutBtn.setOnAction(this::logOut);
     //  this.appModel = new AppModel();
      // appModel.loadData(CurrentUser.getInstance().getLoggedUser());


      //  appModel.getObsTopMovieSeen().forEach(System.out::println);
       // fillUI();
//        var test  = appModel.getObsTopMovieSeen();
//        appModel.getObsTopMovieSeen().forEach(System.out::println);

    }

    public void setData(AppModel appModel){
        this.appModel = appModel;

        startTimer("Loading all data for user: " + currUser);
        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() {
                appModel.loadData(currUser.getLoggedUser());
                return null;
            }
        };
        loadDataTask.setOnSucceeded(event -> {
            stopTimer();
            startTimer("Filling UI took");
            fillUI();
            stopTimer();
            progressBar.setVisible(false);
        });
        new Thread(loadDataTask).start();
    }

    private void stopTimer(){
        System.out.println(timerMsg + " took : " + (System.currentTimeMillis() - timerStartMillis) + "ms");
    }

    private void startTimer(String message){
        timerStartMillis = System.currentTimeMillis();
        timerMsg = message;
    }

    private void logOut(ActionEvent actionEvent) {
        CurrentUser.getInstance().logout();
        AbstractController parent = null;
        try {
            parent = loadNodes("login-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AbstractController finalParent = parent;
        swapViews(finalParent.getView());
    }

    private void fillUI() {
        pane.setContent(constructGridPane(10,appModel.getObsTopMovieSeen()));
        pane1.setContent(constructGridPane(10,appModel.getObsTopMovieSeen()));
        pane2.setContent(constructGridPane(10,appModel.getObsTopMovieNotSeen()));

        // pane1.setContent(constructGridPane(10,appModel.getObsTopMoviesSimilarUsers()));
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }


    /**
     * main method for creation grid sections
     * @return constructed GridPane with its elements
     */
    private GridPane constructGridPane(int amountToDisplay,ObservableList<Movie> topMovieSeen){
        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(10);
        pane2.setAlignment(Pos.CENTER);

        IntStream.range(0, amountToDisplay)
                .parallel()
                .forEach(i -> {
                    Label label = constructLabel(topMovieSeen.get(i).getTitle());
                   // ImageView imageView = constructImage(label);
                    StackPane stackPane = constructStackPane(label, new ImageView());

                    synchronized (pane2) {
                        pane2.add(stackPane, i, 0);
                    }
                });
       return pane2;
    }

    private StackPane constructStackPane(Label label,ImageView imageView) {
        StackPane stack = new StackPane();
        stack.getChildren().addAll(imageView, label);
        stack.getStyleClass().add("image-card");
        stack.setAlignment(Pos.CENTER);
        GridPane.setHgrow(stack, Priority.ALWAYS);
        return stack;
    }

    private ImageView constructImage(Label label) {

        String labelText = label.getText();
        int colonIndex = labelText.indexOf(":");
        if (colonIndex != -1) {
            labelText = labelText.substring(0, colonIndex).trim();
        }
        MovieDTO movieDTO = apiService.getMovieByTitle(labelText);
        ImageView imageView = new ImageView();
        if (movieDTO.Poster != null && !movieDTO.Poster.equals("N/A")) {
            imageView.setImage(new Image(movieDTO.Poster));
        } else {
            imageView.setImage(new Image("https://www.retro-synthwave.com/wp-content/uploads/2020/08/le-doc.jpg"));
        }

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(200);
        imageView.setFitWidth(140);
        return imageView;
    }

    private Label constructLabel(String name) {
        Label label = new Label(name);

        label.setOpacity(0);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(140);
        label.setMinHeight(30);
        label.setTextFill(Color.WHITE);

        label.setOnMouseEntered(event -> {
            label.setOpacity(1);
            label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        });
        label.setOnMouseExited(event -> label.setOpacity(0));

        return label;
    }


    private AbstractController loadNodes(String path) throws IOException {
            return ControllerFactory.loadFxmlFile(path);
    }

    private void swapViews(Parent parent) {
        StackPane contentPane = (StackPane) getView().lookup("contentPane");
        contentPane.getChildren().clear();
        contentPane.getChildren().add(parent);
    }


    public void setParentController(LoginController loginController) {
        this.loginController = loginController;
    }
}
