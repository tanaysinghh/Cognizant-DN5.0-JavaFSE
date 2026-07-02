// Shows how each factory hands back a different document type,
// while the calling code only ever talks to the Document interface.
public class FactoryMethodTest {
    public static void main(String[] args) {
        DocumentFactory wordFactory = new WordDocumentFactory();
        DocumentFactory pdfFactory = new PdfDocumentFactory();
        DocumentFactory excelFactory = new ExcelDocumentFactory();

        Document doc1 = wordFactory.createDocument();
        Document doc2 = pdfFactory.createDocument();
        Document doc3 = excelFactory.createDocument();

        doc1.open();
        doc2.open();
        doc3.open();
    }
}
