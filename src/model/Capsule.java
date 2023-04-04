package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * This class represents the capsules in the phases that the user can create.
 */
public class Capsule {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    private String ID;
    private String description;
    private String capsuleType;
    private Employee collaborator;
    private String learnings;
    private boolean approved;
    private Calendar approvalDate;
    private boolean published;
    private ArrayList<String> hastags;

    public Capsule(String id, String description, String capsuleType, String learnings, Employee collaborator) {
        this.ID = id;
        this.approved = false;
        this.description = description;
        this.capsuleType = capsuleType;
        this.learnings = learnings;
        this.collaborator = collaborator;
        this.published = false;
        this.hastags = new ArrayList<String>();
        addHasTags(description);
        addHasTags(learnings);
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getID() {
        return ID;
    }

    public void setCollaborator(Employee collaborator) {
        this.collaborator = collaborator;
    }

    public Employee getCollaborator() {
        return collaborator;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean getApproved() {
        return this.approved;
    }

    public Calendar getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Calendar approvalDate) {
        this.approvalDate = approvalDate;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean getPublished() {
        return this.published;
    }

    public String getCapsuleType() {
        return capsuleType;
    }

    public void setCapsuleType(String capsuleType) {
        this.capsuleType = capsuleType;
    }

    public String getDescription() {
        return description;
    }

    public String getLearnings() {
        return learnings;
    }

    public ArrayList<String> getHastags() {
        return hastags;
    }

    // -----METHODS-----
    /**
     * 
     * Adds hashtags from given text to the list of hashtags in the capsule.
     * Hashtags must be in the format #hashtag#, and will be extracted and added
     * without the # characters.
     * 
     * @param text the text to extract hashtags from
     */
    public void addHasTags(String text) {
        Pattern p = Pattern.compile("#([^#]+)#");
        Matcher m = p.matcher(text);
        while (m.find()) {
            hastags.add(m.group(1).replaceAll("#", ""));
        }
    }

    /**
     * 
     * Creates the URL for the capsule using the capsule's ID.
     * 
     * @return the URL for the capsule
     */
    public String createCapsuleURL() {
        String url = "https://greensqa.com/en/capsuless/" + this.ID;
        return url;
    }

    /**
     * 
     * Creates an HTML file for the capsule containing all the information about it.
     * 
     * The file will be saved in a "capsulesHTML" directory in the project
     * directory.
     */
    public void createCapsuleHTML() {
        try {
            // Create the file if it does not exist
            File directory = new File("capsulesHTML");
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Create the html file
            String filename = "capsule_" + this.ID + ".html";
            File file = new File("capsulesHTML/" + filename);
            FileWriter writer = new FileWriter(file);

            // Write to the html file all the information
            writer.write("<html>\n");
            writer.write("<head>\n");
            writer.write("<title>Capsule " + this.ID + "</title>\n");
            writer.write("</head>\n");
            writer.write("<body>\n");
            writer.write("<h2>Capsule " + this.ID + "</h2>\n");
            writer.write("<p>Description: " + this.description + "</p>\n");
            writer.write("<p>Capsule Type: " + this.capsuleType + "</p>\n");
            writer.write("<p>Employee: " + this.collaborator.toString() + "</p>\n");
            writer.write("<p>Learnings: " + this.learnings + "</p>\n");
            writer.write("<p>Approved: " + this.approved + "</p>\n");
            writer.write("<p>Approval Date: " + sdf.format(this.approvalDate.getTime()) + "</p>\n");
            writer.write("</body>\n");
            writer.write("</html>\n");

            // Close the file
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * Determines if the given query matches any of the hashtags in the list of
     * hashtags.
     * 
     * @param query the query to match against hashtags
     * 
     * @return true if the query matches any of the hashtags, false otherwise
     */
    public boolean matchQueryWithHastag(String query) {
        boolean found = false;

        for (int i = 0; i < hastags.size(); i++) {
            if (hastags.get(i).equalsIgnoreCase(query)) {
                found = true;
            }
        }
        return found;
    }

    @Override
    public String toString() {
        String result = "ID: " + this.ID +
                "\nDescription: " + this.description +
                "\nCapsule Type: " + this.capsuleType +
                "\nEmployee: " + this.collaborator.toString() +
                "\nLearnings: " + this.learnings +
                "\nAPPROVED: " + this.approved;

        if (this.approved) {
            result += "\nApproval Date: " + sdf.format(this.approvalDate.getTime()) + "\n";
        }

        return result + "\n";
    }
}
