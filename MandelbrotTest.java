import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.Color;
import java.awt.MouseInfo;
public class MandelbrotTest extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
	}
	double zoom=1;
	double shiftX =0;
	double shiftY =0;
	public void start(Stage primaryStage) throws IOException
	{
		int width=800;
		int height = 600;
		int max = 500;
		int black = 0;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] colors = Mandelbrot.Colors(max);

		BufferedImage bufferedImage = (Mandelbrot.set(1, 0, height, width, max, colors, image, black, 0, 0));
		Image image2 = SwingFXUtils.toFXImage(bufferedImage, null);
		final ImageView imv = new ImageView();
		imv.setImage(image2);
		Group root = new Group(); 

		GridPane gridpane = new GridPane();
		Scene scene = new Scene(root, 800, 600);
		final HBox pictureRegion = new HBox();

		pictureRegion.getChildren().add(imv);
		gridpane.add(pictureRegion, 1, 1);


		root.getChildren().add(gridpane);
		primaryStage.setScene(scene);
		
		scene.setOnMousePressed(event -> {
			shiftX += (event.getSceneX()-400)/400;
			shiftY += (event.getSceneY()-300)/300;
            
			//BufferedImage bufferedImage = (Mandelbrot.set(1, 50, height, width, max, colors, image, black));
			Image image3 = null;
			try {
				image3 = SwingFXUtils.toFXImage(Mandelbrot.set(1, zoom, height, width, max, colors, image, black, shiftX, shiftY), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			zoom=zoom*1.5;
			imv.setImage(image3);
			 //System.out.println("x "+event.getSceneX());
			 //System.out.println("y "+event.getSceneY());
			 
		 });
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

	        if (event.getCode() == KeyCode.DOWN) {
	        	zoom=zoom*2;
	        	Image image3 = null;
				try {
					image3 = SwingFXUtils.toFXImage(Mandelbrot.set(1, zoom, height, width, max, colors, image, black, shiftX, shiftY), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				imv.setImage(image3);
	            System.out.println("down");
	            event.consume();
	        }
	        if (event.getCode() == KeyCode.UP) {
	        	zoom=zoom/2;
	        	Image image3 = null;
				try {
					image3 = SwingFXUtils.toFXImage(Mandelbrot.set(1, zoom, height, width, max, colors, image, black, shiftX, shiftY), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				imv.setImage(image3);
	        	System.out.println("up");
	            event.consume();
	        }
	        if (event.getCode() == KeyCode.LEFT) {
	        	shiftX -=0.1;
				
	        	Image image3 = null;
				try {
					image3 = SwingFXUtils.toFXImage(Mandelbrot.set(1, zoom, height, width, max, colors, image, black, shiftX, shiftY), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				imv.setImage(image3);
	        	System.out.println("left");
	            event.consume();
	        }
	        if (event.getCode() == KeyCode.RIGHT) {
	        	shiftX +=0.1;
	        	
	        	Image image3 = null;
				try {
					image3 = SwingFXUtils.toFXImage(Mandelbrot.set(1, zoom, height, width, max, colors, image, black, shiftX, shiftY), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				imv.setImage(image3);
	        	System.out.println("right");
	            event.consume();
	        }
	        
	       
	});
		 scene.widthProperty().addListener(new ChangeListener<Number>() {
	            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
	                System.out.println("Width: " + newSceneWidth);
	                Image image3 = null;
					try {
						image3 = SwingFXUtils.toFXImage(Mandelbrot.set(1, zoom, newSceneWidth.intValue() /16*9, newSceneWidth.intValue(), max, colors, image, black, shiftX, shiftY), null);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					imv.setImage(image3);
	            }
	        });
	        
		primaryStage.setTitle("Mandelbrot browser");
		primaryStage.show();
	}
}