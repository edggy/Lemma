package verifier.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import verifier.Line;
import verifier.Proof;
import verifier.Sentence;
import verifier.SentenceParser;
import verifier.SentenceParser.InvalidSentenceException;

public class Main extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	private GridPane proofGrid;
	private Proof proof;
	private File file; 
	private static SentenceParser parser = new verifier.impl.SentenceParser2();
	private AnchorPane lastedit;
	@FXML private MenuItem save;
	@FXML private MenuItem saveAs;
	@FXML private MenuItem open;
	@FXML private MenuItem clear;
	@FXML private MenuItem addAfter;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Lemma");
		proof = new verifier.impl.Proof();

		initRootLayout();
		showPersonOverview();

	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addLine() throws IOException {
		addLineAt(proofGrid.getChildren().size());
	}
	/**
	 * Adds a line to the end of the grid
	 * @throws IOException
	 */
	public void addLineAt(int lineNum) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ProofLine.fxml"));
		AnchorPane proofLine = (AnchorPane) loader.load();

		proofGrid.addRow(lineNum, proofLine);

		/*EventHandler<InputMethodEvent> handler = new EventHandler<InputMethodEvent>() {
            public void handle(InputMethodEvent event) {
                System.out.println("Handling event " + event.getEventType()); 
                event.consume();
            }
        };*/
		TextField senField = (TextField) proofLine.lookup("#sentence");
		senField.textProperty().addListener((observable, oldValue, newValue) -> {
			//System.out.println("TextField Text Changed (newValue: " + newValue + ")");
			//TextField senField = (TextField)this.primaryStage.getScene().focusOwnerProperty().get();
			Label numLabel = ((Label)proofLine.lookup("#lineNum"));
			try {
				Sentence sen = parser.parse(newValue);
				proof.addSentence(Integer.parseInt(numLabel.getText()), sen);
				System.out.println(sen);
				numLabel.setTextFill(Paint.valueOf("Black"));
			} catch (InvalidSentenceException e) {
				numLabel.setTextFill(Paint.valueOf("Red"));
			}
			lastedit = proofLine;

		});
		//System.out.println(senField);
		//senField.setOnInputMethodTextChanged(handler);
		//proofLine.lookup("#sentence").setOnInputMethodTextChanged(handler);
		//proofLine.setId("line" + lines);

		//Label lineNum = (Label) proofLine.lookup("#lineNum");
		//lineNum.setText((lines<=9?"0":"") + (lines));*/

		numberLines();
		proof.insertLine(lineNum);
	}

	public void numberLines() {
		for(int i = 0; i < proofGrid.getChildren().size(); i++) {

			AnchorPane cur = (AnchorPane) proofGrid.getChildren().get(i);
			//int row = GridPane.getRowIndex(cur);
			Label lineNum = (Label) cur.lookup("#lineNum");
			lineNum.setText((i<=9?"0":"") + (i));
			cur.setId("line" + i);
		}
	}

	/**
	 * Removes a line
	 */
	public void deleteLine(int lineNum) {

		proofGrid.getChildren().remove(lineNum);

		//Renumber the lines
		numberLines();
		proof.removeLine(lineNum);
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showPersonOverview() {
		// Load person overview.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ProofLine.fxml"));
		//AnchorPane proofLine = (AnchorPane) loader.load();

		proofGrid = new GridPane();
		try {
			insertAfter();
			insertAfter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//grid.addRow(0, proofLine);


		/*for(int i = 0; i < 20; i++) {
			addLine();
		}*/
			/*loader = new FXMLLoader();
		    loader.setLocation(Main.class.getResource("view/ProofLine.fxml"));
		    proofLine = (AnchorPane) loader.load();
		    proofLine.setScaleShape(true);
		    proofGrid.addRow(i, proofLine);
		    Label lineNum = (Label) proofLine.lookup("#lineNum");
		    lineNum.setText((i<9?"0":"") + (i+1));
			 */
		//deleteLine(5);
		//deleteLine(10);

		proofGrid.setGridLinesVisible(true);
		ColumnConstraints column = new ColumnConstraints();
		column.setHgrow(Priority.ALWAYS);
		proofGrid.getColumnConstraints().add(column);

		ScrollPane scroll = new ScrollPane();

		scroll.setPannable(true);
		scroll.setFitToWidth(true);
		scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
		scroll.setContent(proofGrid);

		rootLayout.setCenter(scroll);
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void getFilename(boolean save) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Proof Files", "*.proof", "*.lemma"),
				new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));
		File selectedFile = null;
		if(save) selectedFile = fileChooser.showSaveDialog(this.primaryStage);
		else selectedFile = fileChooser.showOpenDialog(this.primaryStage);
		if (selectedFile != null) {
			file = selectedFile;
		}
	}
	
	

	@FXML
	public void save() {
		if(file == null) {
			getFilename(true);
		}
		if(file == null) return;
		
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try{
		    fout = new FileOutputStream(file);
		    oos = new ObjectOutputStream(fout);
		    oos.writeObject(proof);
		    System.out.println(proof);
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		    if(oos  != null){
		    	try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		}


	}

	@FXML
	public void saveAs() {
		getFilename(true);
		if(file == null) return;
		save();


	}
	
	@FXML
	public void clear() {
		proofGrid.getChildren().clear();
		proof = new verifier.impl.Proof();
	}
	
	@FXML
	public void open() {
		getFilename(false);
		if(file == null) return;
		try {
			ObjectInputStream obj_in = new ObjectInputStream (new FileInputStream(file));
			Object o = obj_in.readObject();
			System.out.println(o);
			obj_in.close();
			//proofGrid.getChildren().clear();
			
			List<Line> Lines = proof.getLines();
			for(int i = 0; i < Lines.size(); i++) {
				Line line = Lines.get(i);
				this.addLine();
				TextField proofLine = (TextField) proofGrid.getChildren().get(i);
				TextField senField = (TextField) proofLine.lookup("#sentence");
				senField.setText(line.s.toString());
				//TODO add inferences
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void insertAfter() throws IOException {
		System.out.println(proofGrid);
		Label numLabel = null;
		if(lastedit != null) {
			numLabel = ((Label)lastedit.lookup("#lineNum"));
			addLineAt(Integer.parseInt(numLabel.getText())+1);
		}
		else {
			addLineAt(0);
		}
	}
}
