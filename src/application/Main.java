package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	
	
	//Pane for image and line
	Pane startPane =  new Pane();
	
	TextArea pathArea ;
	TextArea distanceArea;
	
	//Group for circles 
	Group anotherGroup = new Group();
	Circle circleTarget = new Circle();
	Circle circleSource = new Circle();
	
	//main
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	//----------------------------Start-------------------------------------------------------------------------------------
	
	//start 
	@Override
	public void start(Stage startStage) {
		
		
		try {
			
			//Group for all
			Group startGroup = new Group();
			Scene scene = new Scene(startGroup,850,650,Color.LIGHTBLUE);
			startStage.setResizable(false);
			startPane.setLayoutX(1);
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			Image photo = new Image("file:PhotoProject3.jpg");
			ImageView image1 = new ImageView(photo);
			image1.setFitHeight(650);
			image1.setFitWidth(360);
			startStage.getIcons().add(photo);
			startStage.setTitle("GPS Project");
			
			startPane.getChildren().addAll(image1, anotherPane);
			
			//////////////////////////////////////////////////////////////////////////////////////////////////
			
			//Read data from file
			readDataFromFile();
			
			/////////////////////////////////////////////////////////////////////////////////////////////////
			
			Font font1 = new Font("Forte",30);
			Font font2 = new Font("Arial (Body CS)", 20);
			Font ownFont = new Font("Arial Black", 20);
			
			Label lbl1 = new Label("Palestine Map");
			lbl1.setFont(font1);
			lbl1.setLayoutX(20);
			lbl1.setLayoutY(10);
			lbl1.setTextFill(Color.BLACK);
			
			////////////////////////////////////////////////////////////////////////////////////////////////
		
			Label lbl2 = new Label("Source:");
			lbl2.setFont(ownFont);
			
			ComboBox<String> selectSourceCB = new ComboBox<>();
			selectSourceCB.setPromptText("             Select the source");
			selectSourceCB.setMaxHeight(90);
			selectSourceCB.setMinWidth(200);
			
			HBox sourceBox = new HBox(10);
			sourceBox.getChildren().addAll(lbl2, selectSourceCB);
			
			////////////////////////////////////////////////////////////////////////////////////////////////
			
			Label lbl3 = new Label("Target:");
			lbl3.setFont(ownFont);
			
			ComboBox<String> selectTargetCB = new ComboBox<>();
			selectTargetCB.setPromptText("             Select the target");
			selectTargetCB.setMaxHeight(90);
			selectTargetCB.setMinWidth(200);
	
			HBox targetBox = new HBox(15);
			targetBox.getChildren().addAll(lbl3, selectTargetCB);
			
			/////////////////////////////////////////////////////////////////////////////////////////////////
			
			Button runBtn = new Button(" Run ");
			runBtn.setFont(ownFont);
			runBtn.setTextFill(Color.RED);
			runBtn.setMinHeight(20);
			runBtn.setMinWidth(230);
			runBtn.setLayoutX(510);
			runBtn.setLayoutY(250);
			
			runBtn.setOnMouseEntered(ee->{
				
				runBtn.setTextFill(Color.RED);
			});
			
			runBtn.setOnMouseExited(ee->{

				runBtn.setTextFill(Color.GREEN);
			});
			
			///////////////////////////////////////////////////////////////////////////////////////////////////
			
			//to set error message
			Label resultLbl = new Label("");
			
			VBox vbox = new VBox(30);
			vbox.getChildren().addAll(sourceBox, targetBox, resultLbl);
			vbox.setLayoutX(450);
			vbox.setLayoutY(100);
			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			
			//Run button action
			runBtn.setOnAction(e ->{
				
				circleTarget.setVisible(false);
				circleSource.setVisible(false);
				
				if(selectSourceCB.getValue() != null && selectTargetCB.getValue() != null)
				{
					circleSource.setLayoutX(arrButtons[selectSourceCB.getItems().indexOf(selectSourceCB.getValue())].getLayoutX()+5);
					circleSource.setLayoutY(arrButtons[selectSourceCB.getItems().indexOf(selectSourceCB.getValue())].getLayoutY()+10);
					circleSource.setVisible(true);
					
					circleTarget.setLayoutX(arrButtons[selectTargetCB.getItems().indexOf(selectTargetCB.getValue())].getLayoutX()+5);
					circleTarget.setLayoutY(arrButtons[selectTargetCB.getItems().indexOf(selectTargetCB.getValue())].getLayoutY()+10);
					circleTarget.setVisible(true);
					
					arrButtons[selectSourceCB.getItems().indexOf(selectSourceCB.getValue())].fire();
					arrButtons[selectTargetCB.getItems().indexOf(selectTargetCB.getValue())].fire();
					
					resultLbl.setText("");
					
				}
				else
				{
					resultLbl.setText("      Please select the Source and Target!");
					resultLbl.setFont(font2);
					resultLbl.setTextFill(Color.RED);
					anotherPane.getChildren().clear();
				}
			});
			
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Label lbl4 = new Label("Path: ");
			lbl4.setFont(ownFont);
			
			pathArea = new TextArea("");
			pathArea.setFont(Font.font("Arial", FontWeight.BOLD, 14));
			pathArea.setMaxHeight(100);
			pathArea.setMaxWidth(350);
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Label lbl5 = new Label("Distance: ");
			lbl5.setFont(ownFont);
			
			distanceArea = new TextArea("");
			distanceArea.setFont(Font.font("Arial", FontWeight.BOLD, 15));
			
			distanceArea.setMaxHeight(50);
			distanceArea.setMaxWidth(350);
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			VBox vbox2 = new VBox(15);
			vbox2.getChildren().addAll(lbl4, pathArea, lbl5, distanceArea);
			
			vbox2.setLayoutX(450);
			vbox2.setLayoutY(320);
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Button closeBtn = new Button(" Exit ");
			closeBtn.setFont(ownFont);
			closeBtn.setTextFill(Color.RED);
			closeBtn.setLayoutX(390);
			closeBtn.setLayoutY(600);
			
			//Exit button action
			closeBtn.setOnAction(ee -> {
				
				startStage.close();
			});
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////

			
			for(int i = 0 ; i < arrNode.length ; i++)
			{
				selectSourceCB.getItems().add(arrNode[i].name) ;
				selectTargetCB.getItems().add(arrNode[i].name);
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////

			anotherGroup.getChildren().addAll(circleTarget, circleSource, lbl1);
			circleTarget.setVisible(false);
			circleSource.setVisible(false);
			circleSource.setFill(Color.WHITE);
			circleTarget.setFill(Color.BLUE);
			circleSource.setRadius(5);
			circleTarget.setRadius(5);
			
			startStage.setScene(scene);
			startGroup.getChildren().addAll(startPane, vbox, runBtn, vbox2, closeBtn, anotherGroup);
			startStage.show();
			
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////

		} 
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
		
	}//end start method
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////

	int num;
	Node[] arrNode;
	String tempNameC;
	Button[] arrButtons ;
	
	int check = 0;
	
	//read data from file method
	public void readDataFromFile() throws Exception
	{

		File file = new File("C:\\Users\\ghass\\OneDrive\\Desktop\\Folders\\p3\\input.txt");
		Scanner scan = new Scanner(file);
		
		int numOfCites = scan.nextInt();
		int numOfAdjacents = scan.nextInt();

		arrNode = new Node[numOfCites];
		arrButtons = new Button[numOfCites];
		
		//read all cites and put on map
		for( num = 0 ; num < numOfCites ; num++)
		{
			String name = scan.next();
			double y = scan.nextDouble();
			double x = scan.nextDouble();
			
			Node newNode = new Node(name, false, Integer.MAX_VALUE, new Node(), x, y);
			arrNode[num] = newNode;
			
			arrButtons[num] = new Button(name);
			arrButtons[num].setTextFill(Color.BLACK);
			arrButtons[num].setStyle("-fx-background-color:transparent;");
			arrButtons[num].setLayoutX(((x-34.11840)/0.0050230263) - 13);
			arrButtons[num].setLayoutY((33.34777-y)/0.0058763300);
			
			arrButtons[num].setFont(Font.font(null, FontWeight.BOLD, 11));
			
			int index = num;			

			arrButtons[num].setOnAction(e -> {
				
				anotherPane.getChildren().clear();

				if(check == 0)
				{
					circleSource.setVisible(false);
					circleTarget.setVisible(false);
					
					try 
					{
						readDataFromFile();
					} 
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}
					
					dijkstraJob(index);
					tempNameC = arrButtons[index].getText();
					check = 1;
					
					pathArea.setText("");
					circleSource.setVisible(true);
					circleSource.setLayoutX(arrButtons[index].getLayoutX()+5);
					circleSource.setLayoutY(arrButtons[index].getLayoutY()+10);
					
					//System.out.println(circle2.isVisible());
				}
				else 
				{
					String str = endPoint(arrNode[index]);
						
					circleTarget.setVisible(true);
					circleTarget.setLayoutX(arrButtons[index].getLayoutX()+5);
					circleTarget.setLayoutY(arrButtons[index].getLayoutY()+10);
					
					//System.out.println(str.length() + " " + tempNameC.length() + " " + str + " " + tempNameC);
					
					if(arrButtons[index].getText().length()+5 < str.length()) 
					{
						pathArea.setText(str);
						distanceArea.setText((int)(arrNode[index].distance+3.5)+ " km");
					}	
					else 
					{
						pathArea.setText("Doesn't exist any road between the two cities!");
						distanceArea.setText("");
					}

					check = 0;
				}
				
			});
			
			//System.out.println((x-34.11839)/0.0050230263);
			//System.out.println((33.34777-y)/0.0048763291);
			
			startPane.getChildren().add(arrButtons[num]);
			
		}//end for loop
		
		for(int i = 0 ; i < numOfAdjacents ; i++)
		{
			String nameCity = scan.next();
			String nameAd = scan.next();
			
			Node tempCity = null;
			Node tempAd = null;
			
			for(int j=0 ; j < numOfCites ; j++)
			{
				if(arrNode[j].name.equalsIgnoreCase(nameCity))
				{
					tempCity = arrNode[j];
					
				}
				if(arrNode[j].name.equalsIgnoreCase(nameAd))
				{
					tempAd = arrNode[j];
				}
			}//end for
			
			
			tempCity.list.add(new Adjacent(tempAd, (100*Math.sqrt((Math.abs(tempCity.x-tempAd.x)*Math.abs(tempCity.x-tempAd.x))+
														(Math.abs(tempCity.y-tempAd.y)*Math.abs(tempCity.y-tempAd.y))))));
			
		}//end for
		
		
	}//end read data method
	

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Dijkstra process
	public void dijkstraJob(int startPoint)
	{
		arrNode[startPoint].distance = 0;
		
		//As Min-heap
		PriorityQueue<Node> pQ = new PriorityQueue<>();
		pQ.add(arrNode[startPoint]);
		
		
		while(!pQ.isEmpty())
		{
			
			Node tempNode = pQ.poll();
			
			tempNode.known = true;
			
			for(int i = 0 ; i < tempNode.list.size() ; i++)
			{
				if(tempNode.list.get(i).node.known == false)
				{
					if(tempNode.list.get(i).weight + tempNode.distance < tempNode.list.get(i).node.distance)
					{
						tempNode.list.get(i).node.distance = tempNode.list.get(i).weight + tempNode.distance;
						tempNode.list.get(i).node.path = tempNode;
						pQ.add(tempNode.list.get(i).node);
					}
				}
			}//end for

		}
	}//end Dijkstra process
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Pane for line
	Pane anotherPane = new Pane();

	public String endPoint(Node node) {

		int count = 1;
		String str = "";
		
		while(node != null)
		{
			
			if(node.name != null) 
			{
				str =  " >>> " + node.name + str; 
				
				if(count %3 == 0)
				{
					str+="\n";
				}
				
				if(node.path.path != null)
				{
					Line line = new Line((((node.x-34.11840)/0.0050230263)), ((33.34877-node.y)/0.0058763300),
							(((node.path.x-34.11840)/0.0050230263)),((33.34777-node.path.y)/0.0058763300) );
					
					line.setFill(Color.YELLOW);
					
					line.setStrokeWidth(2);
					anotherPane.getChildren().add(line);
				}
					
			
			}//end if
			
			count++;
			node = node.path;
			
		}//end while
		
		
		return str;

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	public void reset()
	{
		for(int i = 0 ; i < arrNode.length ; i++)
		{
			arrNode[i].distance = Integer.MAX_VALUE;
			arrNode[i].known = false;
		}
	}
	
	
	
}//end class
