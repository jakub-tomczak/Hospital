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

        SessionFactory factory  = null;
        try
        {
            factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        }catch (Throwable ex)
        {
            System.err.println(ex);
            System.err.println("Factory is null");

        }
        factory.openSession();

        ManagePeople peopleManager = new ManagePeople(factory);
        peopleManager.listPeople();




    }


    public static void main(String[] args) {
        launch(args);
    }
}
