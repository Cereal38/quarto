package src.structures;

public class SlotFile {
    private int id;
    private String filename;
    private long lastModified;

    public SlotFile(int id, String filename, long lastModified) {
        this.id = id;
        this.filename = filename;
        this.lastModified = lastModified;
    }

    public int getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}