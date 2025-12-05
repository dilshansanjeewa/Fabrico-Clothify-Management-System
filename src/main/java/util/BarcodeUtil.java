package util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import javafx.print.PageLayout;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Scale;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BarcodeUtil {

    private static final String barcodeDir = "barcodes";

    private static final int IMG_WIDTH = 300;
    private static final int IMG_HEIGHT = 100;
    private static final int TEXT_HEIGHT = 30;

    private static void createDirectory(){
        File dir = new File(barcodeDir);

        if(!dir.exists()){
            dir.mkdir();
            System.out.println("created directory "+barcodeDir);
        }
    }

    public static String generateBarcode(String content){
        createDirectory();

        String fileName = content+"_"+LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))+".png";

        Path filePath = Path.of(barcodeDir + File.separator + fileName);

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128,IMG_WIDTH,IMG_HEIGHT);

            MatrixToImageWriter.writeToPath(bitMatrix,"PNG", filePath);

            return filePath.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String generateBarcodeWithText(String data){
        try {

            // Barcode generation
            Code128Writer writer = new Code128Writer();
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128, IMG_WIDTH, IMG_HEIGHT);

            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(matrix);

            // Create final image with space for text
            BufferedImage finalImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT + TEXT_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = finalImage.createGraphics();

            // White background
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT + TEXT_HEIGHT);

            // Draw barcode
            g.drawImage(barcodeImage, 0, 0, null);

            // Draw text under barcode
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.PLAIN, 20));

            // Center the text
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(data);
            int x = (IMG_WIDTH - TEXT_HEIGHT) / 2;
            int y = IMG_HEIGHT + ((TEXT_HEIGHT - fm.getHeight()) / 2) + fm.getAscent();

            g.drawString(data, x, y);
            g.dispose();

            // Save barcode image
            String fileName = "BAR_" + data + "_" + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".png";

            File outputFile = new File(barcodeDir, fileName);
            ImageIO.write(finalImage, "png", outputFile);

            return outputFile.getCanonicalPath();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static boolean printBarcode(String barcodePath) throws FileNotFoundException{

        // check
        File file = new File(barcodePath);
        if (!file.exists()) {
            throw new  FileNotFoundException(barcodePath);
        }

        // Load the saved barcode image
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);


        Printer printer = Printer.getDefaultPrinter();
        if (printer == null) {
            System.out.println("No printer found!");
            return false;
        }

        PrinterJob job = PrinterJob.createPrinterJob(printer);
        if (job == null) {
            System.out.println("Cannot create printer job");
            return false;
        }

        PageLayout pageLayout = printer.getDefaultPageLayout();

        double scaleX = pageLayout.getPrintableWidth() / imageView.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / imageView.getBoundsInParent().getHeight();
        double scale = Math.min(scaleX, scaleY);

        imageView.getTransforms().add(new Scale(scale, scale));

        boolean printed = job.printPage(pageLayout, imageView);

        if (printed) job.endJob();

        return printed;
    }

}
