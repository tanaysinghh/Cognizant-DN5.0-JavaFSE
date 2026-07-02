// Base factory. Each concrete factory decides which document to build.
public abstract class DocumentFactory {
    public abstract Document createDocument();
}
