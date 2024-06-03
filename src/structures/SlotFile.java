/**
 * Represents a slot file with an ID, filename, and last modified timestamp.
 */
package src.structures;

public class SlotFile {
    private int id;
    private String filename;
    private long lastModified;

    /**
     * Constructs a new SlotFile instance with the specified ID, filename, and last modified timestamp.
     *
     * @param id            the ID of the slot file
     * @param filename      the filename of the slot file
     * @param lastModified  the last modified timestamp of the slot file
     */
    public SlotFile(int id, String filename, long lastModified) {
        this.id = id;
        this.filename = filename;
        this.lastModified = lastModified;
    }

    /**
     * Retrieves the ID of the slot file.
     *
     * @return the ID of the slot file
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the filename of the slot file.
     *
     * @return the filename of the slot file
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Retrieves the last modified timestamp of the slot file.
     *
     * @return the last modified timestamp of the slot file
     */
    public long getLastModified() {
        return lastModified;
    }

    /**
     * Sets the filename of the slot file.
     *
     * @param filename the new filename of the slot file
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Sets the last modified timestamp of the slot file.
     *
     * @param lastModified the new last modified timestamp of the slot file
     */
    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }
}
