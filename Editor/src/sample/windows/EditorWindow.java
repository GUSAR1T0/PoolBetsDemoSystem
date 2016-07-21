package sample.windows;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import sample.models.Bet;

import java.util.ArrayList;
import java.util.Objects;

import static sample.additions.Utils.*;

/**
 * Created by Mashenkin Roman on 18.07.2016.
 */
class EditorWindow {

    private ArrayList<String> leagues;
    private ArrayList<String> seasons;
    private ArrayList<String> EPL;
    private ArrayList<String> SLL;
    private ArrayList<String> ISA;
    private ArrayList<String> FL1;
    private ArrayList<String> GB1;
    private ArrayList<String> RPL;
    private ArrayList<String> l;

    private MainWindow mainWindow;
    private Bet bet;
    private int index;

    private Label errorLabel;

    EditorWindow(MainWindow mainWindow, Bet bet, int index) {

        this.mainWindow = mainWindow;
        this.bet = bet;
        this.index = index;

        System.out.println(index);

        leagues = mainWindow.getClient().getLeagues();
        seasons = mainWindow.getClient().getSeasons();
        EPL = initEPL(mainWindow.getClient().getTeams());
        SLL = initSLL(mainWindow.getClient().getTeams());
        ISA = initISA(mainWindow.getClient().getTeams());
        FL1 = initFL1(mainWindow.getClient().getTeams());
        GB1 = initGB1(mainWindow.getClient().getTeams());
        RPL = initRPL(mainWindow.getClient().getTeams());

        if (bet != null) {
            if (bet.getLeague().equals(leagues.get(0)))
                l = EPL;
            if (bet.getLeague().equals(leagues.get(1)))
                l = SLL;
            if (bet.getLeague().equals(leagues.get(2)))
                l = ISA;
            if (bet.getLeague().equals(leagues.get(3)))
                l = FL1;
            if (bet.getLeague().equals(leagues.get(4)))
                l = GB1;
            if (bet.getLeague().equals(leagues.get(5)))
                l = RPL;
        }

        Stage stage = new Stage();
        if (bet == null) stage.setTitle("Bet Editor - Adding");
        else stage.setTitle("Bet Editor - Editing");
        stage.setResizable(false);

        VBox root = createMatchInfo(stage, bet);

        stage.setScene(new Scene(root, 800, 600));
        stage.setResizable(false);
        stage.show();
    }

    private VBox createMatchInfo(Stage stage, Bet bet) {

        /* ----------------- The first line of editor ---------------- */

        Label matchLabel = new Label("MATCH INFORMATION");
        matchLabel.setFont(new Font(16));

        Label leagueLabel = new Label("League:");

        ComboBox<String> leagueCB = new ComboBox<>();
        leagueCB.getItems().addAll(FXCollections.observableArrayList(leagues));
        leagueCB.setPrefWidth(250);

        if (bet == null) leagueCB.getSelectionModel().select(0);
        else leagueCB.getSelectionModel().select(bet.getLeague());

        Label seasonLabel = new Label("Season:");

        ComboBox<String> seasonCB = new ComboBox<>();
        seasonCB.getItems().addAll(FXCollections.observableArrayList(seasons));
        seasonCB.setPrefWidth(150);

        if (bet == null) seasonCB.getSelectionModel().select(0);
        else seasonCB.getSelectionModel().select(bet.getSeason());

        /* ----------- The end of the first line of editor ----------- */

        /* ---------------- The second line of editor ---------------- */

        Label firstTeamLabel = new Label("Team 1:");

        ComboBox<String> firstTeamCB = new ComboBox<>();
        if (bet != null)
            firstTeamCB.setItems(FXCollections.observableArrayList(l));
        else
            firstTeamCB.setItems(FXCollections.observableArrayList(EPL));
        firstTeamCB.setPrefWidth(200);

        if (bet == null) firstTeamCB.getSelectionModel().select(0);
        else firstTeamCB.getSelectionModel().select(bet.getFirstTeam());

        Label secondTeamLabel = new Label("Team 2:");

        ComboBox<String> secondTeamCB = new ComboBox<>();
        if (bet != null)
            secondTeamCB.setItems(FXCollections.observableArrayList(l));
        else
            secondTeamCB.setItems(FXCollections.observableArrayList(EPL));
        secondTeamCB.setPrefWidth(200);

        if (bet == null) secondTeamCB.getSelectionModel().select(1);
        else secondTeamCB.getSelectionModel().select(bet.getSecondTeam());

        leagueCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (leagueCB.getSelectionModel().getSelectedIndex()) {

                case 0: {
                    firstTeamCB.setItems(FXCollections.observableArrayList(EPL));
                    secondTeamCB.setItems(FXCollections.observableArrayList(EPL));
                    break;
                }

                case 1: {
                    firstTeamCB.setItems(FXCollections.observableArrayList(SLL));
                    secondTeamCB.setItems(FXCollections.observableArrayList(SLL));
                    break;
                }

                case 2: {
                    firstTeamCB.setItems(FXCollections.observableArrayList(ISA));
                    secondTeamCB.setItems(FXCollections.observableArrayList(ISA));
                    break;
                }

                case 3: {
                    firstTeamCB.setItems(FXCollections.observableArrayList(FL1));
                    secondTeamCB.setItems(FXCollections.observableArrayList(FL1));
                    break;
                }

                case 4: {
                    firstTeamCB.setItems(FXCollections.observableArrayList(GB1));
                    secondTeamCB.setItems(FXCollections.observableArrayList(GB1));
                    break;
                }

                case 5: {
                    firstTeamCB.setItems(FXCollections.observableArrayList(RPL));
                    secondTeamCB.setItems(FXCollections.observableArrayList(RPL));
                    break;
                }
            }

            Platform.runLater(() -> {
                firstTeamCB.getSelectionModel().select(0);
                secondTeamCB.getSelectionModel().select(1);
            });
        });

        firstTeamCB.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, secondTeamCB.getSelectionModel().getSelectedItem()))
                Platform.runLater(() -> firstTeamCB.getSelectionModel().select(oldValue));
        }));

        secondTeamCB.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, firstTeamCB.getSelectionModel().getSelectedItem()))
                Platform.runLater(() -> secondTeamCB.getSelectionModel().select(oldValue));
        }));

        /* ----------- The end of the second line of editor ----------- */

        /* ----------------- The third line of editor ----------------- */

        Label dateLabel = new Label("COEFFICIENTS OF MATCH");
        dateLabel.setFont(new Font(16));

        Label messageLabel = new Label(
                "For counting coefficients, you should get percentages of probabilities of match result.\n" +
                "Please, fill all fields!");
        messageLabel.setFont(new Font(14));
        messageLabel.setTextAlignment(TextAlignment.CENTER);
        messageLabel.setPadding(new Insets(0, 0, 20, 0));
        messageLabel.setPrefHeight(100);

        Label winFirstTeamLabel = new Label("Winning of Team 1:");
        winFirstTeamLabel.setAlignment(Pos.CENTER);
        winFirstTeamLabel.setPrefWidth(150);

        TextField winFirstTeamField = new TextField();

        if (bet == null) winFirstTeamField.setText("40.0");
        else winFirstTeamField.setText((100d / Double.parseDouble(bet.getWinFirstTeam())) + "");

        Label coefFirstTeamLabel = new Label();
        coefFirstTeamLabel.setAlignment(Pos.CENTER);
        coefFirstTeamLabel.setPrefWidth(150);

        if (bet == null) coefFirstTeamLabel.setText("~ 2.5");
        else coefFirstTeamLabel.setText("~ " + bet.getWinFirstTeam());

        Label drawLabel = new Label("Draw:");
        drawLabel.setAlignment(Pos.CENTER);
        drawLabel.setPrefWidth(150);

        TextField drawField = new TextField();

        if (bet == null) drawField.setText("20.0");
        else drawField.setText((100d / Double.parseDouble(bet.getDraw())) + "");

        Label coefDrawLabel = new Label();
        coefDrawLabel.setAlignment(Pos.CENTER);
        coefDrawLabel.setPrefWidth(150);

        if (bet == null) coefDrawLabel.setText("~ 5");
        else coefDrawLabel.setText("~ " + bet.getDraw());

        Label winSecondTeamLabel = new Label("Winning of Team 2:");
        winSecondTeamLabel.setAlignment(Pos.CENTER);
        winSecondTeamLabel.setPrefWidth(150);

        TextField winSecondTeamField = new TextField();

        if (bet == null) winSecondTeamField.setText("40.0");
        else winSecondTeamField.setText((100d / Double.parseDouble(bet.getWinSecondTeam())) + "");

        Label coefSecondTeamLabel = new Label();
        coefSecondTeamLabel.setAlignment(Pos.CENTER);
        coefSecondTeamLabel.setPrefWidth(150);

        if (bet == null) coefSecondTeamLabel.setText("~ 2.5");
        else coefSecondTeamLabel.setText("~ " + bet.getWinSecondTeam());

        String percentageInfo = "Unallocated percentages: ";
        final Float[] valueUP = {(100 - (Float.parseFloat(winFirstTeamField.getText()) +
                Float.parseFloat(drawField.getText()) +
                Float.parseFloat(winSecondTeamField.getText())))};
        Label percentageLabel = new Label(percentageInfo + valueUP[0]);
        percentageLabel.setFont(new Font(16));

        Label resultLabel = new Label("Result:");
        resultLabel.setAlignment(Pos.CENTER);
        resultLabel.setPrefWidth(150);

        ComboBox<String> resultCB = new ComboBox<>();
        resultCB.setItems(FXCollections.observableArrayList("-", "1", "X", "2"));
        resultCB.setPrefWidth(200);

        if (bet == null) resultCB.getSelectionModel().select(0);
        else resultCB.getSelectionModel().select(bet.getResult());

        winFirstTeamField.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() ->  {
            if (!newValue.matches("((\\d*)|(\\d+\\.\\d*))")) {
                winFirstTeamField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!winFirstTeamField.getText().equals("") && !winFirstTeamField.getText().equals("-")) {
                if (100 - (Float.parseFloat(drawField.getText()) +
                        Float.parseFloat(winSecondTeamField.getText())) <
                        Float.parseFloat(winFirstTeamField.getText()))
                    winFirstTeamField.setText((100 - (Float.parseFloat(drawField.getText()) +
                            Float.parseFloat(winSecondTeamField.getText()))) + "");
                if (Float.parseFloat(winFirstTeamField.getText()) <= 0)
                    winFirstTeamField.setText("0.01");
            }
            coefFirstTeamLabel.setText("~ " + (100f / Float.parseFloat(winFirstTeamField.getText())));
            valueUP[0] = (100 - (Float.parseFloat(winFirstTeamField.getText()) +
                    Float.parseFloat(drawField.getText()) +
                    Float.parseFloat(winSecondTeamField.getText())));
            percentageLabel.setText(percentageInfo + valueUP[0] + "");
        }));

        drawField.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (!newValue.matches("((\\d*)|(\\d+\\.\\d*))")) {
                drawField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!drawField.getText().equals("") && !drawField.getText().equals("-")) {
                if (100 - (Float.parseFloat(winFirstTeamField.getText()) +
                        Float.parseFloat(winSecondTeamField.getText())) <
                        Float.parseFloat(drawField.getText()))
                    drawField.setText((100 - (Float.parseFloat(winFirstTeamField.getText()) +
                            Float.parseFloat(winSecondTeamField.getText()))) + "");
                if (Float.parseFloat(drawField.getText()) < 0)
                    drawField.setText("0");
            }
            coefDrawLabel.setText("~ " + (100f / Float.parseFloat(drawField.getText())));
            valueUP[0] = (100 - (Float.parseFloat(winFirstTeamField.getText()) +
                    Float.parseFloat(drawField.getText()) +
                    Float.parseFloat(winSecondTeamField.getText())));
            percentageLabel.setText(percentageInfo + valueUP[0] + "");
        }));

        winSecondTeamField.textProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> {
            if (!newValue.matches("((\\d*)|(\\d+\\.\\d*))")) {
                winSecondTeamField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!winSecondTeamField.getText().equals("") && !winSecondTeamField.getText().equals("-")) {
                if (100 - (Float.parseFloat(winFirstTeamField.getText()) +
                        Float.parseFloat(drawField.getText())) <
                        Float.parseFloat(winSecondTeamField.getText()))
                    winSecondTeamField.setText((100 - (Float.parseFloat(winFirstTeamField.getText()) +
                            Float.parseFloat(drawField.getText()))) + "");
                if (Float.parseFloat(winSecondTeamField.getText()) <= 0)
                    winSecondTeamField.setText("0.01");
            }
            coefSecondTeamLabel.setText("~ " + (100f / Float.parseFloat(winSecondTeamField.getText())));
            valueUP[0] = (100 - (Float.parseFloat(winFirstTeamField.getText()) +
                    Float.parseFloat(drawField.getText()) +
                    Float.parseFloat(winSecondTeamField.getText())));
            percentageLabel.setText(percentageInfo + valueUP[0] + "");
        }));

        /* ----------- The end of the third line of editor ------------ */

        /* ---------------- The fourth line of editor ----------------- */

        errorLabel = new Label();
        errorLabel.setTextFill(Color.web("#cc4b4b"));
        errorLabel.setOpacity(0);

        Button okButton = new Button("OK");
        okButton.setPrefWidth(100);

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(100);

        okButton.setOnMouseClicked(event -> {
            if (valueUP[0] > 0f) {
                errorLabel.setText("YOU SHOULD USE UNALLOCATED PERCENTAGES!");
                errorLabel.setOpacity(1);
            } else {
                if (saveData(leagueCB, seasonCB, firstTeamCB, secondTeamCB, winFirstTeamField, drawField,
                        winSecondTeamField, resultCB))
                    stage.close();
            }
        });

        cancelButton.setOnMouseClicked(event -> stage.close());

        /* ----------- The end of the fourth line of editor ----------- */

        /* ------------------ The main part of editor ----------------- */

        ArrayList<HBox> boxes = new ArrayList<>();
        boxes.add(new HBox(matchLabel));
        boxes.add(new HBox(leagueLabel, leagueCB, seasonLabel, seasonCB));
        boxes.add(new HBox(firstTeamLabel, firstTeamCB, secondTeamLabel, secondTeamCB));
        boxes.add(new HBox(new Line(0, 0, 750, 0)));
        boxes.add(new HBox(dateLabel));
        boxes.add(new HBox(messageLabel));
        boxes.add(new HBox(winFirstTeamLabel, winFirstTeamField, coefFirstTeamLabel));
        boxes.add(new HBox(drawLabel, drawField, coefDrawLabel));
        boxes.add(new HBox(winSecondTeamLabel, winSecondTeamField, coefSecondTeamLabel));
        boxes.add(new HBox(percentageLabel));
        boxes.add(new HBox(resultLabel, resultCB));
        boxes.add(new HBox(new Line(0, 0, 750, 0)));
        boxes.add(new HBox(errorLabel, okButton, cancelButton));

        for (int i = 0; i < boxes.size(); i++) {
            boxes.get(i).setPadding(new Insets(10));
            boxes.get(i).setSpacing(20);
            if (i != boxes.size() - 1)
                boxes.get(i).setAlignment(Pos.CENTER);
            else
                boxes.get(i).setAlignment(Pos.CENTER_RIGHT);
        }

        VBox matchBox = new VBox();
        matchBox.getChildren().addAll(boxes);

        return matchBox;
    }

    private boolean saveData(ComboBox<String> leagueCB, ComboBox<String> seasonCB, ComboBox<String> firstTeamCB,
                          ComboBox<String> secondTeamCB, TextField winFirstTeamField, TextField drawField,
                          TextField winSecondTeamField, ComboBox<String> reasonCB) {

        if (bet != null) {
            bet.setLeague(leagueCB.getValue());
            bet.setSeason(seasonCB.getValue());
            bet.setFirstTeam(firstTeamCB.getValue());
            bet.setSecondTeam(secondTeamCB.getValue());
            bet.setWinFirstTeam((100d / Double.parseDouble(winFirstTeamField.getText())) + "");
            bet.setDraw((100d / Double.parseDouble(drawField.getText())) + "");
            bet.setWinSecondTeam((100d / Double.parseDouble(winSecondTeamField.getText())) + "");
            bet.setResult(reasonCB.getValue());
            mainWindow.updateBetOnMySQLServer(bet, index);
        } else {
            if (!mainWindow.check(leagueCB.getValue(), seasonCB.getValue(),
                    firstTeamCB.getValue(), secondTeamCB.getValue()))
                mainWindow.updateBetObservableList(new Bet(
                        leagueCB.getValue(),
                        seasonCB.getValue(),
                        firstTeamCB.getValue(),
                        secondTeamCB.getValue(),
                        (100d / Double.parseDouble(winFirstTeamField.getText())) + "",
                        (100d / Double.parseDouble(drawField.getText())) + "",
                        (100d / Double.parseDouble(winSecondTeamField.getText())) + "",
                        reasonCB.getValue()));
            else {
                errorLabel.setText("THIS BET IS ALREADY IN DATABASE!");
                errorLabel.setOpacity(1);
                return false;
            }
        }
        return true;
    }
}
