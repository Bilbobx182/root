package root;

import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.*;

public class Menu extends Application
{
    Stage mainmenu;
    Scene mm,pm,em;


    boolean auto=false;
    boolean active;
    String piepro;
    Label activepro = new Label();
    int minbuttonpress=6;


    //Classes
    public static Profile pro1 = new Profile();
    public static Xbox box = new Xbox();
    public static Fileio fd = new Fileio();
    public static Process pr = new Process();
    Pbox pb = new Pbox();

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        mainmenu = primaryStage;
        mainmenu.setOnCloseRequest(lam -> mainmenu.close());
        box.setup();
        mainmenu.setTitle("Controller Home Screen");

        //-------------------------------------------------------MAIN MENU BUTTONS
        Pie pie=new Pie();
        //Quit button
        Button quit = new Button("Quit");
        quit.setOnAction(aquit->
                {
                    mainmenu.close();
                    System.exit(0);
                }
        );

        Button promenu = new Button("Profile Menu");
        promenu.setOnAction(proif ->
                {
                    mainmenu.setScene(pm);
                    mainmenu.setTitle("Controller Profile Screen");
                }
        );

        Button etc = new Button("Other options");
        etc.setOnAction(pm ->
                {
                    // mainmenu.setScene(mm);
                }
        );

        Button b2m = new Button("Back to main menu");
        b2m.setOnAction(pm ->
                {
                    mainmenu.setScene(mm);
                    mainmenu.setTitle("Controller Home Screen");


                }
        );

        //START
        Button begin = new Button("Start");
        begin.setMaxWidth(900000000);
        begin.setMaxHeight(50);
        begin.setOnAction(prof ->
                {
                    Gameopen();
                    if (active == true)
                    {
                        box.setvars();
                        box.timer();
                        box.polling();


                        if(box.total > minbuttonpress)
                        {
                            if (auto == true) {
                                pie.render(piepro);
                                box.setvars();
                            } else {
                                pie.render(pro1.ofn);
                                box.setvars();
                            }
                        }
                        else
                        {
                            pb.warning("BUTTON ERROR","Please press the buttons more for better graphs :( ");
                        }
                    }
                    else
                    {
                        activepro.setText("NO ACTIVE PROFILE");
                    }
                }
        );

        //-------------------------------------------------------PROFILE MENU BUTTONS
        //new profile
        Button profile = new Button("Create new profile");
        profile.setOnAction(prof ->
                {
                    pro1.title();
                }
        );

        //select
        Button select = new Button("Select profile");
        select.setOnAction(sel ->
        {
            pro1.selector();
            String keeper=pro1.ofn;

            if(pro1.test==true)
            {
                fd.inputgetter();
                activepro.setText("Active profile:"+keeper);
                active=true;
            }
            else
            {
                activepro.setText("NO ACTIVE PROFILE");
            }

        });

        //-----------------------------------------------------------ETC MENU BUTTONS
        StackPane mmlayout = new StackPane();
        mmlayout.getChildren().addAll(begin,promenu,etc, quit,activepro);
        mm = new Scene(mmlayout, 600, 600);

        StackPane pmlayout = new StackPane();
        pm = new Scene(pmlayout, 600, 600);
        pmlayout.getChildren().addAll(select,profile,b2m);


        //------------------------------------------------------MAIN MENU ALLIGNMENTS
        StackPane.setAlignment(begin, Pos.TOP_CENTER);
        StackPane.setAlignment(activepro,Pos.CENTER);
        StackPane.setAlignment(profile, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(select, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(quit, Pos.BOTTOM_RIGHT);

        //------------------------------------------------------PROFILE ALLIGNMENTS
        StackPane.setAlignment(promenu, Pos.BOTTOM_LEFT);
        StackPane.setAlignment(etc, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(b2m, Pos.BOTTOM_RIGHT);


        mm.getStylesheets().add("style.css");
        pm.getStylesheets().add("style.css");

        mainmenu.setScene(mm);
        mainmenu.show();
    }


    void Gameopen()
    {
        int i=0;
        int j=0;
        fd.wrpcheck();
        pr.tasklist();
        int size=fd.profiles.size();

        while(i<size)
        {
            for (String value : pr.prolist)
            {
                if(value.contains(fd.profiles.get(i)))
                {
                    piepro=fd.profiles.get(i);
                    System.out.println(piepro);
                    activepro.setText("AUTO-DETECTED: "+piepro);
                    fd.read(piepro+".txt");
                    active=true;
                    System.out.println("found it");
                    box.setvars();
                    auto=true;
                    break;
                }
            }
            i++;
        }
    }

}//END OF CLASS