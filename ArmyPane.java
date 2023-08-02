
//Description: Organizes all the things that will be on the army pane such as the checkboxes, and the update button.
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ArmyPane extends BorderPane {
	ArrayList<Hero> heroList;
	
	int totalDamage;
	int totalStrength;
	int totalCharisma;

	Label armyInfo;
	VBox checkBoxes;
	Button loadHero;

	public ArmyPane(ArrayList<Hero> heroList) {
		this.heroList = heroList;
		this.armyInfo = new Label("Select heroes to add to your army");
		this.checkBoxes = new VBox();
		this.loadHero = new Button("Load Heroes/Clear Selection");

		loadHero.setOnAction(new LoadHeroesButtonHandler());
		
		this.setTop(armyInfo);
		this.setBottom(loadHero);
		this.setCenter(checkBoxes);

	}
	
	private class LoadHeroesButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			checkBoxes.getChildren().clear();
			
			totalDamage=0;//Sets attributes equal to 0 in the case that user has already loaded heroes once and wishes to do it again.
			totalCharisma=0;
			totalStrength=0;
			for(int i =0;i<heroList.size();i++) {//Sets check boxes for each Hero entry.
				CheckBox check = new CheckBox(heroList.get(i).toString());
				check.setOnAction(new CheckBoxHandler(heroList.get(i)));
				checkBoxes.getChildren().addAll(check);
				
			}

		}
	}

	private class CheckBoxHandler implements EventHandler<ActionEvent> {

		Hero hero;
		
		public CheckBoxHandler(Hero _hero) {
			this.hero = _hero;
		}

		@Override
		public void handle(ActionEvent event) {

			CheckBox triggered =(CheckBox)event.getSource();
					if(triggered.isSelected()) {//When checkmark is clicked/unclicked takes hero info of that checkbox and either adds or subtracts its info from the totals.
						totalDamage=(int)(totalDamage+hero.getDamage());
						totalCharisma=(int)(totalCharisma+hero.getCharisma());
						totalStrength=(int)(totalStrength+hero.getStrength());
					}else {
						totalDamage=(int)(totalDamage-hero.getDamage());
						totalCharisma=(int)(totalCharisma-hero.getCharisma());
						totalStrength=(int)(totalStrength-hero.getStrength());
					}
				
			
				armyInfo.setText("Total Damage: " + totalDamage + "\t\tTotal Strength: " + totalStrength + "\tTotal Charisma: " + totalCharisma);


		}
	}

}
