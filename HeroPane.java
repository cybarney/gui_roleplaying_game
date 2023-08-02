
//Organizes and creates all the items on the heroPane including the add hero interface, and showcasing the hero info on the right.
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HeroPane extends HBox {
	ArrayList<Hero> heroList;

	String selectedHeroType;

	TextArea rightTextArea;
	VBox leftVBox;
	ComboBox<String> heroTypeComboBox;
	ImageView imageView;

	GridPane inputPane;
	Label name;
	Label strength;
	Label charisma;
	Label damage;
	TextField nameCorresponding;
	TextField strengthCorresponding;
	TextField charismaCorresponding;
	TextField damageCorresponding;
	Button random;

	Button addNewHero;//Declare addNewHero Button
	
	Label heroStatus;
	Label addHero;
	
	public static final int WINSIZE_X = 950, WINSIZE_Y = 600;

	public HeroPane(ArrayList<Hero> heroList) {

		this.heroList = heroList;

		this.leftVBox = new VBox();
		this.rightTextArea = new TextArea();
		
		String[] heroType = { "Mage", "Fighter", "Unicorn", "Zombie" };
		heroTypeComboBox = new ComboBox<String>();
		heroTypeComboBox.setValue("Hero Type");
		heroTypeComboBox.getItems().addAll(heroType);
		heroTypeComboBox.setOnAction(new HeroTypeComboBoxHandler());
		leftVBox.getChildren().add(heroTypeComboBox);

		name = new Label("Name");
		strength = new Label("Strength");
		charisma = new Label("Charisma");
		damage = new Label("Damage");
		nameCorresponding = new TextField("");
		strengthCorresponding = new TextField("");
		charismaCorresponding = new TextField("");
		damageCorresponding = new TextField("");
		Button random = new Button("Random");
		damageCorresponding.setEditable(false);//Makes it so damage cannot be edited

		addHero = new Label("Add New Hero!!!");	
		heroStatus = new Label();
		heroStatus.setTextFill(Color.RED);

		inputPane = new GridPane();
		inputPane.setAlignment(Pos.CENTER);
		inputPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		inputPane.setHgap(5.5);
		inputPane.setVgap(5.5);
		inputPane.add(name, 0, 1);
		inputPane.add(strength, 0, 2);
		inputPane.add(charisma,0,3);
		inputPane.add(damage, 0, 4);
		inputPane.add(nameCorresponding, 1,1);
		inputPane.add(strengthCorresponding, 1, 2);
		inputPane.add(charismaCorresponding, 1, 3);
		inputPane.add(damageCorresponding, 1, 4);
		inputPane.add(random, 2, 4);

		addNewHero = new Button("Add New Hero!!!");
		addNewHero.setOnAction(new AddNewHeroButtonHandler());
		random.setOnAction(new RandomButtonHandler());
		
		leftVBox.getChildren().addAll(inputPane,addNewHero,heroStatus);

		inputPane.setHgap(20);
		leftVBox.setPadding(new Insets(40, 50, 0, 50));
		leftVBox.setSpacing(40);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		leftVBox.setPrefWidth(WINSIZE_X / 2);

		imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(100);
		leftVBox.getChildren().add(imageView);
		FileInputStream input;
		try {
			input = new FileInputStream("unicorn.png");
			Image image = new Image(input);
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			imageView.setImage(null);
		}
		
		this.getChildren().addAll(leftVBox, rightTextArea);
	}
	
	// Generate random damage value (50 <= damage <= 100)
	private class RandomButtonHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {

				if(damageCorresponding.getText().isEmpty()) {//Randomizes damage if there is not already one generated
					int range = (100-50)*1;//Sets range that will be used in randomizer
					int randDamage = (int)(Math.random()*range)+50;//Casts the random value as an int
					damageCorresponding.setText(""+randDamage);
				}else {
					heroStatus.setText("Damage is already generated");//Makes it so only one damage an be generated 
				}


		}
	}


	private class AddNewHeroButtonHandler implements EventHandler<ActionEvent> {

		// This method will be called once we click the button
		public void handle(ActionEvent event) {

			String damageText = damageCorresponding.getText();
			String nameText = nameCorresponding.getText();
			String charismaText = charismaCorresponding.getText();
			String strengthText = strengthCorresponding.getText();

			try {
					
				
				if (selectedHeroType == null) {
					throw new Exception("Hero type is not yet selected");
				}

				if(damageCorresponding.getText().isEmpty()||nameCorresponding.getText().isEmpty()||charismaCorresponding.getText().isEmpty()||strengthCorresponding.getText().isEmpty()) {
					throw new Exception("At least one of the text fields is empty");
					
					}

				for(int i=0;i<heroList.size();i++) {//Checks for duplicate hero names
					if(heroList.get(i).getName().equals(nameText)) {
						throw new Exception("Hero Existed!");
					}
				}

				int damageInt = Integer.parseInt(damageText);
				int strengthInt = Integer.parseInt(strengthText);
				int charismaInt = Integer.parseInt(charismaText);

				if(strengthInt<0||charismaInt<0) {//Checks that both strength and chraisma are positive numbers.
					throw new Exception("Both Strength and Charisma must be positive numbers");
				}

				if(strengthInt+charismaInt>100) {//Checks if the sum of charisma and strength is less than 100.
					throw new Exception("The sum of strength and charisma must be less or equal to 100");
				}

				Hero heroInput = new Hero(nameText,selectedHeroType,strengthInt,charismaInt,damageInt);
				heroList.add(heroInput);
				
				heroStatus.setTextFill(Color.RED);
				damageCorresponding.setText("");
				charismaCorresponding.setText("");
				nameCorresponding.setText("");
				strengthCorresponding.setText("");

				updateTextArea();

			} catch (NumberFormatException exception) {//Catches if one of the text fields is in an incorrect format. 
				heroStatus.setText("At least one of the text fields is in the incorrect format");
			

			} catch (Exception exception) {
				heroStatus.setText(exception.getMessage());//Sets exception equal to the heroStatus text.

			}

		}
	}

	private void updateTextArea() {
		rightTextArea.clear();//Clears so that the hero list doesn't keep stacking.
		for(int i =0;i<heroList.size();i++) {//Adds hero info to list on the side. 
			String placeHolder3 = rightTextArea.getText();
			String heroInfo = heroList.get(i).toString();
			String placeHolder = (placeHolder3+heroInfo);
			rightTextArea.setText(placeHolder);
			
		}
		
	}
	
	
	private class HeroTypeComboBoxHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			selectedHeroType = heroTypeComboBox.getSelectionModel().getSelectedItem();
			FileInputStream input;
			try {
				input = new FileInputStream(selectedHeroType.toLowerCase() + ".png");
				Image image = new Image(input);
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				imageView.setImage(null);
			}

		}
	}


}
