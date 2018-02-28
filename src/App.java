import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.regex.Pattern;

public class App extends Application
{
    private FileChooser svgFCH, fxmlFCH, toolFCH;
    private TextField svgTF, fxmlTF;
    private Button btn1,btn2,btn3;
    private String nazov_pre_save, cesta_tool;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("SVG2FXML");

        initKomponenty();

        GridPane GL = new GridPane();

        GL.setVgap(10);
        GL.setHgap(2);

        GL.add(svgTF, 0, 0);
        GL.add(btn1, 1,0);
        GL.add(fxmlTF, 0, 1);
        GL.add(btn2, 1,1);

        GL.add(btn3, 0, 2);

        GL.add(new Label("By: D.Kolibár"), 1,2);

        primaryStage.setScene(new Scene(GL));

        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        primaryStage.show();

        btn1.setOnAction(event ->
        {
            String cesta = "";

            try
            {
                cesta = svgFCH.showOpenDialog(primaryStage).getAbsolutePath();
            }
            catch (Exception err)
            {
                System.out.println(err);
            }

            if(!cesta.isEmpty())
            {
                String separator = "\\";
                String exploded[] = cesta.split(Pattern.quote(separator));

                nazov_pre_save = exploded[exploded.length - 1];

                String exploded2[] = nazov_pre_save.split("\\.");
                nazov_pre_save = exploded2[0];

                btn2.setDisable(false);

                svgTF.setText(cesta);
            }
        });

        btn2.setOnAction(event ->
        {
            String cesta = "";

            if(!nazov_pre_save.isEmpty())
            {
                fxmlFCH.setInitialFileName(nazov_pre_save);
                try
                {
                    cesta = fxmlFCH.showSaveDialog(primaryStage).getAbsolutePath();
                }
                catch (Exception err)
                {
                    System.out.println(err);
                }
            }

            if(!cesta.isEmpty())
            {
                fxmlTF.setText(cesta);
                btn3.setDisable(false);
            }
        });

        btn3.setOnAction(event ->
        {
            if(cesta_tool == null)
            {
                cesta_tool = toolFCH.showOpenDialog(primaryStage).getAbsolutePath();
                doMagic();
            }
            else
                doMagic();
        });
    }

    private void doMagic()
    {
        try
        {
            String command = "java -jar \"" + cesta_tool + "\" \"" + svgTF.getText() + "\" \"" + fxmlTF.getText() + "\"";
            System.out.println(command);
            Process proc = Runtime.getRuntime().exec(command);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void initKomponenty()
    {
        //file choosre
        svgFCH = new FileChooser();
        svgFCH.getExtensionFilters().add(new FileChooser.ExtensionFilter("SVG", "*.svg"));
        svgFCH.setTitle("Select your SVG file...");

        fxmlFCH = new FileChooser();
        fxmlFCH.getExtensionFilters().add(new FileChooser.ExtensionFilter("FXML", "*.fxml"));
        fxmlFCH.setTitle("Choose where FXML will be saved...");

        toolFCH = new FileChooser();
        toolFCH.getExtensionFilters().add(new FileChooser.ExtensionFilter("JAR", "*.jar"));
        toolFCH.setTitle("Locate the SVG2FXML.jar tool by e(fx)clipse.org community!");
        //

        Font font = new Font(25);

        svgTF = new TextField();
        svgTF.setEditable(false);
        svgTF.setPrefWidth(350);
        svgTF.setFocusTraversable(false);

        btn1 = new Button("SVG...");
        btn1.setFont(font);

        fxmlTF = new TextField();
        fxmlTF.setEditable(false);
        fxmlTF.setPrefWidth(350);
        fxmlTF.setFocusTraversable(false);

        btn2 = new Button("...FXML");
        btn2.setDisable(true);
        btn2.setFont(font);

        btn3 = new Button("SVG2FXML!");
        btn3.setDisable(true);
        btn3.setFont(font);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
