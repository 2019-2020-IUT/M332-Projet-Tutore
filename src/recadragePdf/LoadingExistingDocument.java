
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.util.Matrix;


public class LoadingExistingDocument {

   public static void main(String args[]) throws IOException {
   
      //Loading an existing document 
      File file = new File("P:\\Bureau\\ptut s3\\eclipse\\image\\DOC-corrige.pdf"); 
      PDDocument document = PDDocument.load(file); 
      PDPage page = document.getDocumentCatalog().getPages().get(0);
      PDPageContentStream cs = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.PREPEND, false, false);
      cs.transform(Matrix.getRotateInstance(Math.toRadians(45), 0, 0));
      System.out.println("PDF loaded"); 
      cs.close();
      
      //Adding a blank page to the document 
      document.addPage(new PDPage());  

      //Saving the document 
      document.save("P:\\Bureau\\ptut s3\\eclipse\\image\\sample.pdf");
      document.
      //Closing the document  
      document.close(); 
 
   }  
}




