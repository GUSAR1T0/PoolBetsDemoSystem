package sample.windows;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import sample.models.Bet;
import sample.models.Client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sample.additions.Codes.CODE_EDITOR_AUTHORIZATION;
import static sample.additions.Codes.CODE_EDITOR_CONNECTED;

/**
 * Created by Mashenkin Roman on 19.07.2016.
 */
public class MainWindow {

    private Button addButton;
    private Button editButton;
    private Button removeButton;
    private ToggleButton launchButton;

    private ObservableList<Bet> bets;
    private TableView<Bet> betsTable;
    private Client client = null;
    private int port;

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    public VBox getRoot() {

        betsTable = getBetsTable();

        addButton = new Button("Add");
        addButton.setPrefWidth(100);
        addButton.setDisable(true);
        addButton.setOnMouseClicked(event -> new EditorWindow(this, null));

        editButton = new Button("Edit");
        editButton.setPrefWidth(100);
        editButton.setDisable(true);
        editButton.setOnMouseClicked(event -> {
            if (bets.size() > 0) new EditorWindow(this, betsTable.getFocusModel().getFocusedItem());
        });

        removeButton = new Button("Remove");
        removeButton.setPrefWidth(100);
        removeButton.setDisable(true);
        removeButton.setOnMouseClicked(event -> {
            if (bets.size() > 0) bets.remove(betsTable.getFocusModel().getFocusedIndex());
        });

        bets.addListener(new ListChangeListener<Bet>() {
            @Override
            public void onChanged(Change<? extends Bet> c) {
                if (launchButton.isSelected())
                    if (bets.size() > 0) {
                        editButton.setDisable(false);
                        removeButton.setDisable(false);
                    } else {
                        editButton.setDisable(true);
                        removeButton.setDisable(true);
                    }
            }
        });

        HBox buttonHBox = new HBox(addButton, editButton, removeButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(150);

        HBox connectionBox = createHBoxLayout("PoolBets Server");
        connectionBox.setPadding(new Insets(20, 0 , 0, 0));

        VBox root = new VBox();
        root.getChildren().addAll(betsTable, buttonHBox, connectionBox);
        root.setSpacing(12.5f);
        root.setPadding(new Insets(10));

        return root;
    }

    private TableView<Bet> getBetsTable() {

        bets = FXCollections.observableArrayList();

        TableView<Bet> betsTable = new TableView<>();
        betsTable.setMinHeight(480);
        betsTable.setEditable(false);

        TableColumn<Bet, String> leagueColumn = new TableColumn<>("League");
        leagueColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(5));
        leagueColumn.setCellValueFactory(new PropertyValueFactory<>("league"));

        TableColumn<Bet, String> seasonColumn = new TableColumn<>("Season");
        seasonColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(10));
        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("season"));

        TableColumn<Bet, String> firstTeamColumn = new TableColumn<>("Team 1");
        firstTeamColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(5));
        firstTeamColumn.setCellValueFactory(new PropertyValueFactory<>("firstTeam"));

        TableColumn<Bet, String> secondTeamColumn = new TableColumn<>("Team 2");
        secondTeamColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(5));
        secondTeamColumn.setCellValueFactory(new PropertyValueFactory<>("secondTeam"));

        TableColumn<Bet, String> winFirstTeamColumn = new TableColumn<>("1");
        winFirstTeamColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(13));
        winFirstTeamColumn.setCellValueFactory(new PropertyValueFactory<>("winFirstTeam"));

        TableColumn<Bet, String> drawColumn = new TableColumn<>("X");
        drawColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(13));
        drawColumn.setCellValueFactory(new PropertyValueFactory<>("draw"));

        TableColumn<Bet, String> winSecondTeamColumn = new TableColumn<>("2");
        winSecondTeamColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(13));
        winSecondTeamColumn.setCellValueFactory(new PropertyValueFactory<>("winSecondTeam"));

        TableColumn<Bet, String> resultColumn = new TableColumn<>("Result");
        resultColumn.prefWidthProperty().bind(betsTable.widthProperty().divide(15));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        betsTable.setItems(bets);
        betsTable.getColumns().addAll(leagueColumn,
                seasonColumn,
                firstTeamColumn,
                secondTeamColumn,
                winFirstTeamColumn,
                drawColumn,
                winSecondTeamColumn,
                resultColumn);

        return betsTable;
    }

    void updateBetObservableList(Bet bet) {
        bets.add(bet);
    }

    boolean check(String league, String season, String firstTeam, String secondTeam) {

        for (Bet i : bets) {
            if (i.getLeague().equals(league) &&
                    i.getSeason().equals(season) &&
                    i.getFirstTeam().equals(firstTeam) &&
                    i.getSecondTeam().equals(secondTeam)) return true;
        }

        return false;
    }

    private HBox createHBoxLayout(String textLabel) {

        Label serverLabel = new Label(textLabel);
        serverLabel.setPrefWidth(100);
        serverLabel.setAlignment(Pos.CENTER);

        Label ipLabel = new Label("IP Address:");
        TextField ipTextField = new TextField();
        ipTextField.setText("95.37.138.89");

        Label portLabel = new Label("Port:");
        TextField portTextField = new TextField();
        portTextField.setText("11296");

        launchButton = (new ToggleButton());
        launchButton.setText("Launch");
        launchButton.setMinWidth(100);

        HBox box = new HBox(serverLabel, ipLabel, ipTextField, portLabel, portTextField, launchButton);
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.setAlignment(Pos.CENTER);

        HBox.setHgrow(ipLabel, Priority.ALWAYS);
        HBox.setHgrow(ipTextField, Priority.ALWAYS);
        HBox.setHgrow(portLabel, Priority.ALWAYS);
        HBox.setHgrow(portTextField, Priority.ALWAYS);
        HBox.setHgrow(launchButton, Priority.ALWAYS);

        launchButton.setOnMouseClicked(event -> {
            if (launchButton.getText().equals("Launch")) {
                if (validateIP(ipTextField.getText()) && (validatePort(portTextField.getText()))) {
                    try {
                        client = new Client(ipTextField.getText(), port, CODE_EDITOR_AUTHORIZATION);

                        if (client.getCode().equals(CODE_EDITOR_CONNECTED)) {
                            launchButton.setText("Stop");

                            addButton.setDisable(false);
                            if (bets.size() > 0) {
                                editButton.setDisable(false);
                                removeButton.setDisable(false);
                            }

                            ipTextField.setDisable(true);
                            portTextField.setDisable(true);
                        } else {
                            launchButton.setSelected(false);
                            addButton.setDisable(true);
                            editButton.setDisable(true);
                            removeButton.setDisable(true);
                        }
                    } catch (Throwable throwable) {
                        launchButton.setSelected(false);
                        addButton.setDisable(true);
                        editButton.setDisable(true);
                        removeButton.setDisable(true);
                    }
                }
            } else {
                try {
                    client.disconnect();
                    launchButton.setText("Launch");
                    launchButton.setSelected(false);

                    addButton.setDisable(true);
                    editButton.setDisable(true);
                    removeButton.setDisable(true);

                    ipTextField.setDisable(false);
                    portTextField.setDisable(false);
                } catch (Throwable throwable) {
                    launchButton.setSelected(true);

                    addButton.setDisable(false);
                    if (bets.size() > 0) {
                        editButton.setDisable(false);
                        removeButton.setDisable(false);
                    }
                }
            }
        });

        return box;
    }

    private boolean validateIP(String ipAddress){

        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipAddress);

        return matcher.matches() || ipAddress.equals("localhost");
    }

    private boolean validatePort(String port) {

        try {
            int i = Integer.parseInt(port);
            if ((i >= 1025) && (i < 65536)) {
                this.port = i;
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public Client getClient() {
        return client;
    }
}
