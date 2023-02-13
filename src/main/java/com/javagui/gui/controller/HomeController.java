package com.javagui.gui.controller;

import com.javagui.be.Movie;
import com.javagui.be.TopMovie;
import com.javagui.bll.api.ApiService;
import com.javagui.bll.api.IApiService;
import com.javagui.gui.model.AppModel;
import com.javagui.gui.model.CurrentUser;
import com.javagui.gui.model.MovieDTO;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
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


    public void fillUI() {
        constructPane(pane, appModel.getObsTopMovieSeen());
        constructPane(pane1, appModel.getObsTopMoviesSimilarUsers());
        constructPane(pane2, appModel.getObsTopMovieNotSeen());
    }

    private void constructPane(MFXScrollPane pane, ObservableList<?> movieList) {
        pane.setContent(constructGridPane(20, movieList));
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }


    /**
     * main method for creation grid sections
     * @return constructed GridPane with its elements
     */
    private GridPane constructGridPane(int amountToDisplay,ObservableList<?> movieList){
        Objects.requireNonNull(movieList,"List of movies cannot be empty");
        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(10);
        pane2.setAlignment(Pos.CENTER);

        List<Object> randomMovies = movieList.stream()
                .limit(amountToDisplay)
                .collect(Collectors.toList());

        IntStream.range(0, amountToDisplay)
                .parallel()
                .forEach(i -> {
                    Object obj = randomMovies.get(i);
                   Label movieTitle;
                    if (obj instanceof Movie) {
                        movieTitle = constructLabel(((Movie) obj).getTitle());
//                        labels[1] = constructLabel(String.valueOf(((Movie) obj).getYear()));
//                        labels[2] = constructLabel(String.valueOf(((Movie) obj).getAverageRating()));
                    } else if (obj instanceof TopMovie) {
                        movieTitle = constructLabel(((TopMovie) obj).getMovie().getTitle());
//                        labels[1] = constructLabel(String.valueOf(((TopMovie) obj).getYear()));
//                        labels[2] = constructLabel(String.valueOf(((TopMovie) obj).getAverageRating()));
                    } else {
                        movieTitle = null;
                    }

                    if (movieTitle != null) {
                       // var imageView = new ImageView("https://www.retro-synthwave.com/wp-content/uploads/2021/05/02-army-of-the-dead-poster.jpg");
                        Task<ImageView> imageTask = constructImage(movieTitle);
                        imageTask.setOnSucceeded(event -> {
                            ImageView imageView = imageTask.getValue();
                            StackPane stackPane = constructStackPane(movieTitle, imageView);
                            synchronized (pane2) {
                                pane2.add(stackPane, i, 0);
                            }
                        });
                        new Thread(imageTask).start();

//                        var imageView = constructImage(movieTitle);
//                        imageView.setFitHeight(200);
//                        imageView.setFitWidth(140);
//                        StackPane stackPane = constructStackPane(movieTitle,imageView);
//                        synchronized (pane2) {
//                            pane2.add(stackPane, i, 0);
//                        }
                    }
                });
       return pane2;
    }

    private StackPane constructStackPane(Label labels,ImageView imageView) {
        StackPane stack = new StackPane();
        stack.getChildren().addAll(imageView,labels);
        stack.getStyleClass().add("image-card");
        stack.setAlignment(Pos.CENTER);
        GridPane.setHgrow(stack, Priority.ALWAYS);
        return stack;
    }

//    private ImageView constructImage(Label label) {
//
//        String labelText = label.getText();
//        int colonIndex = labelText.indexOf(":");
//        if (colonIndex != -1) {
//            labelText = labelText.substring(0, colonIndex).trim();
//        }
//        MovieDTO movieDTO = apiService.getMovieByTitle(labelText);
//        ImageView imageView = new ImageView();
//        if (movieDTO.Poster != null && !movieDTO.Poster.equals("N/A")) {
//            imageView.setImage(new Image(movieDTO.Poster));
//        } else {
//            imageView.setImage(new Image("https://www.retro-synthwave.com/wp-content/uploads/2020/08/le-doc.jpg"));
//        }
//
//        imageView.setPreserveRatio(true);
//        imageView.setFitHeight(200);
//        imageView.setFitWidth(140);
//        return imageView;
//    }

    private Task<ImageView> constructImage(Label label) {
        return new Task<>() {
            @Override
            protected ImageView call() {
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
        };
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
