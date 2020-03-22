package src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main  extends Application {

    //JavaFX
    Stage window;
    Scene scene1, scene2;
    Button button;
    public static Circle[] diskActive = new Circle[2];
    public static Circle[] printerActive = new Circle[3];
    public static Text[] diskStatusText = new Text[2];
    public static Text[] printerStatusText = new Text[3];
    public static Text[] userStatusText = new Text[4];

    public static double timerMultiplier = 0;
    public static OperatingSystem os;
    //public static GraphicalUserInterface gui;
    public static boolean simulationRunning = false;
    public static boolean runGUI = false;


    public static void main(String[] args) {

        //Command Line Input:  //TODO change to command line
        String firstArgument ="";
        String secondArgument="";
        String thirdArgument="";
        String fourthArgument="";
        List<String> userList = new ArrayList<String>();
        int argCount = 0;
        boolean noGUI = false;

        for(int i=0; i<args.length; i++){
            System.out.println("args[" + i + "]: " + args[i]);
            String arg = args[i];

            if(arg.contains("-ng")){
                System.out.println("-ng detected");
                noGUI = true;
            }
            else if(arg.startsWith("-")){
                argCount++;
                if(argCount==1){
                    firstArgument = arg.substring(arg.indexOf("-")+1);
                } else if (argCount==2){
                    secondArgument = arg.substring(arg.indexOf("-")+1);
                } else if (argCount==3){
                    thirdArgument = arg.substring(arg.indexOf("-")+1);
                } else if (argCount==4){
                    fourthArgument = arg.substring(arg.indexOf("-")+1);
                }
            }
            else {
                userList.add(arg);
            }
        }


        //DEBUG
            /*secondArgument = "2";
            thirdArgument ="3";
            userList.add("USER1");
            userList.add("USER2");
            userList.add("USER3");
            userList.add("USER4");*/

            /*int numOfDisk = 2;
            int numOfPrinters = 3;
            List<String> userList = new ArrayList<String>();
            userList.add("USER1");
            userList.add("USER2");
            userList.add("USER3");
            userList.add("USER4");*/

        int numOfDisk = Integer.parseInt(secondArgument);
        int numOfPrinters = Integer.parseInt(thirdArgument);

        os = OperatingSystem.getInstance(numOfDisk, numOfPrinters, userList);

        if(noGUI){
            runGUI=false;
            try {
                os.begin();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else { //Launch GUI version
            runGUI=true;
            System.out.println("noGUI = false");
            launch(args);
            //GraphicalUserInterface.begin(args,os);
            //gui = GraphicalUserInterface.getInstance(os);
            //gui.begin(args);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Tutorial - 4: Switching Scene
        //tutorial4(stage);

        //Start GUI Tester
        //startGUITester(stage);

        //Start GUI
        startGUI(stage);
    }

    /*
        Tutorial 4 - Switching Scenes
     */
    private void tutorial4(Stage primaryStage){

        window = primaryStage;

        //Button 1
        Label label1 = new Label("Welcome to the first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2));

        //Layout 1 - children are laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 200, 200);

        //Button 2
        Button button2= new Button("Go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        //Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        window.setScene(scene1);
        window.setTitle("Title here");
        window.show();
    }

    private void startGUITester(Stage primaryStage){
        window = primaryStage;

        //layout1
        Label label1 = new Label("By: Roman Geluz  UCInetID: geluzr");
        Button button1 = new Button("Click Me!");
        VBox layout1 = new VBox();
        layout1.getChildren().addAll(label1, button1);

        scene1 = new Scene(layout1, 700, 700);
        window.setScene(scene1);
        window.setTitle("141OS Assignment");
        window.show();

    }

    private void startGUI(Stage primaryStage){
        window = primaryStage;
        //window.setTitle("Grid Pane");
        window.setTitle("141OS Assignment");


        /*GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);*/

        Image usernameImage = null;
        Image userImage = null;
        Image diskImage = null;
        Image printerImage = null;
        Image fileImage = null;


        try {

            //username image
            usernameImage = new Image(new FileInputStream("resources/username.png"));

            //user image
            userImage = new Image(new FileInputStream("resources/userLogo.png"));

            //disk image
            diskImage = new Image(new FileInputStream("resources/databaseLogo.png"));

            //printer image
            printerImage = new Image(new FileInputStream("resources/printerLogo.png"));

            //file image
            fileImage = new Image(new FileInputStream("resources/fileLogo.png"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Add username and ucinetid image
        ImageView userNameView = new ImageView(usernameImage);
        userNameView.setX(0);
        userNameView.setY(0);

        //Users
        ImageView userView1 = new ImageView(userImage);
        userView1.setX(100);
        userView1.setY(100);
        userView1.setFitHeight(50);
        userView1.setFitWidth(50);
        Text user1Text = new Text("User 1");
        user1Text.setX(100);
        user1Text.setY(170);
        Text user1StatusText = new Text("");
        user1StatusText.setX(100);
        user1StatusText.setY(190);
        userStatusText[0] = user1StatusText;

        ImageView userView2 = new ImageView(userImage);
        userView2.setX(100);
        userView2.setY(200);
        userView2.setFitHeight(50);
        userView2.setFitWidth(50);
        Text user2Text = new Text("User 2");
        user2Text.setX(100);
        user2Text.setY(270);
        Text user2StatusText = new Text("");
        user2StatusText.setX(100);
        user2StatusText.setY(290);
        userStatusText[1] = user2StatusText;


        ImageView userView3 = new ImageView(userImage);
        userView3.setX(100);
        userView3.setY(300);
        userView3.setFitHeight(50);
        userView3.setFitWidth(50);
        Text user3Text = new Text("User 3");
        user3Text.setX(100);
        user3Text.setY(370);
        Text user3StatusText = new Text("");
        user3StatusText.setX(100);
        user3StatusText.setY(390);
        userStatusText[2] = user3StatusText;

        ImageView userView4 = new ImageView(userImage);
        userView4.setX(100);
        userView4.setY(400);
        userView4.setFitHeight(50);
        userView4.setFitWidth(50);
        Text user4Text = new Text("User 4");
        user4Text.setX(100);
        user4Text.setY(470);
        Text user4StatusText = new Text("");
        user4StatusText.setX(100);
        user4StatusText.setY(490);
        userStatusText[3] = user4StatusText;

        //Disks
        ImageView diskView1 = new ImageView(diskImage);
        diskView1.setX(400);
        diskView1.setY(100);
        diskView1.setFitHeight(50);
        diskView1.setFitWidth(50);
        Text disk1Text = new Text("Disk 1");
        disk1Text.setX(400);
        disk1Text.setY(170);
        Circle disk1ActiveStatus = new Circle();
        disk1ActiveStatus.setCenterX(450);
        disk1ActiveStatus.setCenterY(150);
        disk1ActiveStatus.setRadius(10);
        disk1ActiveStatus.setFill(Color.LIGHTGREEN);
        diskActive[0] = disk1ActiveStatus;
        Text disk1StatusText = new Text("Idle");
        //disk1StatusText.setFont(Font.);
        disk1StatusText.setX(400);
        disk1StatusText.setY(190);
        diskStatusText[0] = disk1StatusText;

        ImageView diskView2 = new ImageView(diskImage);
        diskView2.setX(400);
        diskView2.setY(200);
        diskView2.setFitHeight(50);
        diskView2.setFitWidth(50);
        Text disk2Text = new Text("Disk 2");
        disk2Text.setX(400);
        disk2Text.setY(270);
        Circle disk2ActiveStatus = new Circle();
        disk2ActiveStatus.setCenterX(450);
        disk2ActiveStatus.setCenterY(250);
        disk2ActiveStatus.setRadius(10);
        disk2ActiveStatus.setFill(Color.LIGHTGREEN);
        diskActive[1] = disk2ActiveStatus;
        Text disk2StatusText = new Text("Idle");
        disk2StatusText.setX(400);
        disk2StatusText.setY(290);
        diskStatusText[1] = disk2StatusText;

        //Printers
        ImageView printerView1 = new ImageView(printerImage);
        printerView1.setX(700);
        printerView1.setY(100);
        printerView1.setFitHeight(50);
        printerView1.setFitWidth(50);
        Text printer1Text = new Text("Printer 1");
        printer1Text.setX(700);
        printer1Text.setY(170);
        Circle printer1ActiveStatus = new Circle();
        printer1ActiveStatus.setCenterX(750);
        printer1ActiveStatus.setCenterY(150);
        printer1ActiveStatus.setRadius(10);
        printer1ActiveStatus.setFill(Color.LIGHTGREEN);
        printerActive[0] = printer1ActiveStatus;
        Text printer1StatusText = new Text("Idle");
        printer1StatusText.setX(700);
        printer1StatusText.setY(190);
        printerStatusText[0] = printer1StatusText;


        ImageView printerView2 = new ImageView(printerImage);
        printerView2.setX(700);
        printerView2.setY(200);
        printerView2.setFitHeight(50);
        printerView2.setFitWidth(50);
        Text printer2Text = new Text("Printer 2");
        printer2Text.setX(700);
        printer2Text.setY(270);
        Circle printer2ActiveStatus = new Circle();
        printer2ActiveStatus.setCenterX(750);
        printer2ActiveStatus.setCenterY(250);
        printer2ActiveStatus.setRadius(10);
        printer2ActiveStatus.setFill(Color.LIGHTGREEN);
        printerActive[1] = printer2ActiveStatus;
        Text printer2StatusText = new Text("Idle");
        printer2StatusText.setX(700);
        printer2StatusText.setY(290);
        printerStatusText[1] = printer2StatusText;

        ImageView printerView3 = new ImageView(printerImage);
        printerView3.setX(700);
        printerView3.setY(300);
        printerView3.setFitHeight(50);
        printerView3.setFitWidth(50);
        Text printer3Text = new Text("Printer 3");
        printer3Text.setX(700);
        printer3Text.setY(370);
        Circle printer3ActiveStatus = new Circle();
        printer3ActiveStatus.setCenterX(750);
        printer3ActiveStatus.setCenterY(350);
        printer3ActiveStatus.setRadius(10);
        printer3ActiveStatus.setFill(Color.LIGHTGREEN);
        printerActive[2] = printer3ActiveStatus;
        Text printer3StatusText = new Text("Idle");
        printer3StatusText.setX(700);
        printer3StatusText.setY(390);
        printerStatusText[2] = printer3StatusText;

        //Files
        ImageView fileView1 = new ImageView(fileImage);
        fileView1.setX(1000);
        fileView1.setY(100);
        fileView1.setFitHeight(50);
        fileView1.setFitWidth(50);
        Text file1Text = new Text("File 1");
        file1Text.setX(1000);
        file1Text.setY(170);

        ImageView fileView2 = new ImageView(fileImage);
        fileView2.setX(1000);
        fileView2.setY(200);
        fileView2.setFitHeight(50);
        fileView2.setFitWidth(50);
        Text file2Text = new Text("File 2");
        file2Text.setX(1000);
        file2Text.setY(270);

        ImageView fileView3 = new ImageView(fileImage);
        fileView3.setX(1000);
        fileView3.setY(300);
        fileView3.setFitHeight(50);
        fileView3.setFitWidth(50);
        Text file3Text = new Text("File 3");
        file3Text.setX(1000);
        file3Text.setY(370);

        //Button
        button = new Button();
        button.setLayoutX(500);
        button.setLayoutY(20);
        button.setText("Start Simulation");
        button.setOnMouseClicked(e -> {
            if(!simulationRunning){ //Start Simulation
                simulationRunning = true;
                System.out.println("Simulation Started!");
                button.setText("Stop Simulation");
                try {
                    os.begin();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            } else { //Stop Simulation
                System.out.println("Simulation Ended!");
                System.exit(0);
            }
        });

        //Slider
        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(100);
        slider.setValue(40);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setMinorTickCount(5);
        slider.setBlockIncrement(10);
        slider.setLayoutX(700);
        slider.setLayoutY(20);
        slider.setOnDragDetected(e ->{
            System.out.println("slider value: " + slider.getValue());
        });

        Group group = new Group();
        group.getChildren().add(userNameView);

        //Add Users
        group.getChildren().add(userView1);
        group.getChildren().add(user1Text);
        group.getChildren().add(userStatusText[0]);

        group.getChildren().add(userView2);
        group.getChildren().add(user2Text);
        group.getChildren().add(userStatusText[1]);

        group.getChildren().add(userView3);
        group.getChildren().add(user3Text);
        group.getChildren().add(userStatusText[2]);

        group.getChildren().add(userView4);
        group.getChildren().add(user4Text);
        group.getChildren().add(userStatusText[3]);

        //Add Disks
        group.getChildren().add(diskView1);
        group.getChildren().add(disk1Text);
        group.getChildren().add(diskActive[0]);
        group.getChildren().add(diskStatusText[0]);

        group.getChildren().add(diskView2);
        group.getChildren().add(disk2Text);
        group.getChildren().add(diskActive[1]);
        group.getChildren().add(diskStatusText[1]);

        //Add Printers
        group.getChildren().add(printerView1);
        group.getChildren().add(printer1Text);
        group.getChildren().add(printerActive[0]);
        group.getChildren().add(printerStatusText[0]);

        group.getChildren().add(printerView2);
        group.getChildren().add(printer2Text);
        group.getChildren().add(printerActive[1]);
        group.getChildren().add(printerStatusText[1]);

        group.getChildren().add(printerView3);
        group.getChildren().add(printer3Text);
        group.getChildren().add(printerActive[2]);
        group.getChildren().add(printerStatusText[2]);

        //Add Files
        group.getChildren().add(fileView1);
        group.getChildren().add(file1Text);

        group.getChildren().add(fileView2);
        group.getChildren().add(file2Text);

        group.getChildren().add(fileView3);
        group.getChildren().add(file3Text);

        //Add Button
        group.getChildren().add(button);

        //Add Slider
        group.getChildren().add(slider);

        Scene scene = new Scene(group, 1200, 600);
        window.setScene(scene);
        window.show();
    } //end of startGUI()


} //end of main
