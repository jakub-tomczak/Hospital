package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        try
        {
            Configuration cfg = new Configuration()
                    .addResource("/src/sample/Person.hbm.xml");

        }catch(HibernateException e)
        {
            System.out.println(e.getMessage() + "\n" + e.getStackTrace());
        }
        SessionFactory factory = null;

        /*
        return;
        try
        {
            factory = cfg.buildSessionFactory();

        }catch (Throwable e)
        {
            System.err.println("Failed to create SessionFactory object");
        }
        if(factory == null)
        {
            System.err.println("Factory is null");
            return;
        }

        ManagePeople peopleManager = new ManagePeople(factory);
        peopleManager.listPeople();
*/




    }


    public static void main(String[] args) {
        launch(args);
    }
}
