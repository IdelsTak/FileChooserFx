package com.chainstaysoftware.filebrowser;

import com.chainstaysoftware.filebrowser.preview.ImagePreviewWindow;
import com.chainstaysoftware.filebrowser.preview.PreviewWindow;
import com.chainstaysoftware.filebrowser.preview.HeadPreviewWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.nio.charset.Charset;
import java.util.HashMap;

public class FileChooserDemo extends Application {
   @Override
   public void start(Stage primaryStage) throws Exception {
      final HashMap<String, PreviewWindow> previewHandlers = new HashMap<>();
      previewHandlers.put("png", new ImagePreviewWindow());
      previewHandlers.put("jpg", new ImagePreviewWindow());
      previewHandlers.put("txt", new HeadPreviewWindow(Charset.forName("UTF-8"), 100));

      final TextFlow textFlow = new TextFlow();

      final Button fileChooserButton = new Button("Choose File");
      fileChooserButton.setOnAction(event -> {
         final FileChooser fileChooser = new FileChooserImpl();
         fileChooser.setTitle("File Chooser");
         fileChooser.setShowHiddenFiles(false);
         fileChooser.getPreviewHandlers().putAll(previewHandlers);
         fileChooser.setHelpCallback(() -> System.out.println("Help invoked"));

         fileChooser.showOpenDialog(primaryStage,
               fileOptional -> textFlow.getChildren()
                     .add(new Text("File to open - " + fileOptional.toString() + "\r\n")));
      });

      final Button fileSaveButton = new Button("Save File");
      fileSaveButton.setOnAction(event -> {
         final FileChooser fileChooser = new FileChooserImpl();
         fileChooser.setTitle("File Save");
         fileChooser.setShowHiddenFiles(false);
         fileChooser.getPreviewHandlers().putAll(previewHandlers);
         fileChooser.setHelpCallback(() -> System.out.println("Help invoked"));

         fileChooser.showSaveDialog(primaryStage,
               fileOptional -> textFlow.getChildren()
                     .add(new Text("File to save - " + fileOptional.toString() + "\r\n")));
      });

      final Button dirChooserButton = new Button("Choose Directory");
      dirChooserButton.setOnAction(event -> {
         final DirectoryChooser dirChooser = new DirectoryChooserImpl();
         dirChooser.setTitle("Directory Chooser");
         dirChooser.setHelpCallback(() -> System.out.println("Help invoked"));

         dirChooser.showDialog(primaryStage,
               fileOptional -> textFlow.getChildren()
                     .add(new Text("Directory to open - " + fileOptional.toString() + "\r\n")));
      });

      final ToolBar toolBar = new ToolBar();
      toolBar.getItems().addAll(fileChooserButton, fileSaveButton, dirChooserButton);

      final BorderPane borderPane = new BorderPane();
      borderPane.setTop(toolBar);
      borderPane.setCenter(textFlow);

      primaryStage.setScene(new Scene(borderPane, 800, 600));
      primaryStage.show();
   }

   public static void main(String[] args) {
      Application.launch(args);
   }
}
