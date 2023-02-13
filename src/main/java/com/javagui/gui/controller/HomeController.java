package com.javagui.gui.controller;

import com.javagui.be.Movie;
import com.javagui.be.TopMovie;
import com.javagui.bll.api.ApiService;
import com.javagui.bll.api.IApiService;
import com.javagui.gui.model.AppModel;
import com.javagui.gui.model.CurrentUser;
import com.javagui.gui.model.MovieDTO;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HomeController extends AbstractController implements Initializable {
    @FXML
    private MFXProgressBar progressBar;
    @FXML
    private MFXScrollPane pane, pane1, pane2;

    private final String PLACEHOLDER_IMG = "https://www.retro-synthwave.com/wp-content/uploads/2019/09/American-Horror-Story-1984-9.jpg";

    private final int NUMBER_TO_DISPLAY = 10;
    private final CurrentUser currUser = CurrentUser.getInstance();

    private LoginController loginController;
    private AppModel appModel;
    private IApiService apiService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.apiService = new ApiService();
    }

    public void setData(AppModel appModel, MFXButton btn) {
        this.appModel = appModel;
        btn.setOnAction(this::logOut);

        Task<Void> loadDataTask = new Task<>() {
            @Override
            protected Void call() {
                appModel.loadData(currUser.getLoggedUser());
                return null;
            }
        };
        loadDataTask.setOnSucceeded(event -> {
            fillUI();
            progressBar.setVisible(false);
        });
        new Thread(loadDataTask).start();

    }

    public void logOut(ActionEvent actionEvent) {
        CurrentUser.getInstance().logout();
        AbstractController parent = null;
        try {
            parent = loadNodes("login-view.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Success: You have been successfully logged out");
        alert.show();
        AbstractController finalParent = parent;
        swapViews(finalParent.getView());
    }

    public void fillUI() {
        constructPane(NUMBER_TO_DISPLAY, pane, appModel.getObsTopMovieSeen());
        constructPane(NUMBER_TO_DISPLAY, pane1, appModel.getObsTopMoviesSimilarUsers());
        constructPane(NUMBER_TO_DISPLAY, pane2, appModel.getObsTopMovieNotSeen());
    }

    private void constructPane(int numberToDisplay, MFXScrollPane pane, ObservableList<?> movieList) {
        pane.setContent(constructGridPane(numberToDisplay, movieList));
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    private GridPane constructGridPane(int amountToDisplay, ObservableList<?> movieList) {
        Objects.requireNonNull(movieList, "List of movies cannot be empty");
        GridPane pane2 = new GridPane();
        pane2.setHgap(10);
        pane2.setVgap(10);
        pane2.setAlignment(Pos.CENTER);

        Collections.shuffle(movieList);
        List<Object> randomMovies = movieList.stream()
                .limit(amountToDisplay)
                .distinct()
                .collect(Collectors.toList());

        IntStream.range(0, amountToDisplay)
                .parallel()
                .forEach(i -> {
                    Object obj = randomMovies.get(i);
                    Label movieTitle, movieYear, movieRating;
                    if (obj instanceof Movie) {
                        movieTitle = constructLabel(((Movie) obj).getTitle());
                        movieYear = constructLabel(String.valueOf(((Movie) obj).getYear()));
                        movieRating = constructLabel(String.valueOf(((Movie) obj).getAverageRating()));
                    } else if (obj instanceof TopMovie) {
                        movieTitle = constructLabel(((TopMovie) obj).getMovie().getTitle());
                        movieYear = constructLabel(String.valueOf(((TopMovie) obj).getYear()));
                        movieRating = constructLabel(String.valueOf(((TopMovie) obj).getAverageRating()));
                    } else {
                        movieTitle = null;
                        movieYear = null;
                        movieRating = null;
                    }

                    if (movieTitle != null) {
                        Label[] labels = {movieTitle, movieYear, movieRating};
                        resolveRatings(movieRating);

                        Task<ImageView> imageTask = constructImage(movieTitle);
                        imageTask.setOnSucceeded(event -> {
                            ImageView imageView = imageTask.getValue();
                            StackPane stackPane = constructStackPane(labels, imageView);
                            synchronized (pane2) {
                                pane2.add(stackPane, i, 0);
                            }
                        });
                        new Thread(imageTask).start();
                    }
                });
        return pane2;
    }

    private void resolveRatings(Label movieRating) {
        if (Double.parseDouble(movieRating.getText()) >= 4.5) {
            movieRating.setText("★★★★★");
        } else if (Double.parseDouble(movieRating.getText()) >= 3.5) {
            movieRating.setText("★★★★☆");
        } else if (Double.parseDouble(movieRating.getText()) >= 2.5) {
            movieRating.setText("★★★☆☆");
        } else if (Double.parseDouble(movieRating.getText()) >= 1.5) {
            movieRating.setText("★★☆☆☆");
        } else {
            movieRating.setText("★☆☆☆☆");
        }
    }

    private StackPane constructStackPane(Label[] labels, ImageView imageView) {
        StackPane stack = new StackPane();

        VBox vbox = new VBox();
        Arrays.stream(labels).forEach(x -> vbox.getChildren().add(x));
        vbox.setAlignment(Pos.CENTER);

        vbox.setVisible(false);

        stack.getChildren().addAll(imageView, vbox);
        stack.getStyleClass().add("image-card");

        stack.setOnMouseEntered(event -> vbox.setVisible(true));
        stack.setOnMouseExited(event -> vbox.setVisible(false));

        stack.setAlignment(Pos.CENTER);
        GridPane.setHgrow(stack, Priority.ALWAYS);
        return stack;
    }

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
                    imageView.setImage(new Image(PLACEHOLDER_IMG));
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

        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setMinWidth(140);
        label.setMinHeight(30);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
        return label;
    }

    private AbstractController loadNodes(String path) throws IOException {
        return ControllerFactory.loadFxmlFile(path);
    }

    private void swapViews(Parent parent) {
        try {
            Scene scene = new Scene(parent);
            Stage stage = (Stage) getView().getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void setParentController(LoginController loginController) {
        this.loginController = loginController;
    }
}
