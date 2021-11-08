import com.sun.javafx.application.LauncherImpl;
import controller.InitPreloader;
import controller.LauncherPreloader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AppInitializer extends Application {

    public static Stage primaryStage = null;
    public static Scene primaryScene = null;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(AppInitializer.class, LauncherPreloader.class, args);

    }

    @Override
    public void init() throws Exception {
        InitPreloader init = new InitPreloader();
        init.checkFunctions();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        AppInitializer.primaryStage = primaryStage;

    }
}
