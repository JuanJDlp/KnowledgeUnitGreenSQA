package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Capsule {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    private String ID;
    private String description;
    private String capsuleType;
    private Employee collaborator;
    private String learnings;
    private boolean approved;
    private Calendar approvalDate;
    private ArrayList<String> hastags;

    public Capsule(String id, String description, String capsuleType, String learnings, Employee collaborator) {
        this.ID = id;
        this.approved = false;
        this.description = description;
        this.capsuleType = capsuleType;
        this.learnings = learnings;
        this.collaborator = collaborator;
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

    public ArrayList<String> getHastags() {
        return hastags;
    }

    // -----METHODS-----
    public void addHasTags(String text) {
        Pattern p = Pattern.compile("#([^#]+)#");
        Matcher m = p.matcher(text);
        while (m.find()) {
            hastags.add(m.group(1).replaceAll("#", ""));
        }
    }

    public String createCapsuleURL() {
        String url = "https://greensqa.com/en/capsuless/" + this.ID;
        return url;
    }

    public boolean createCapsuleHTML() {
        boolean htmlISCreated = false;
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
            htmlISCreated = false;
        }
        return htmlISCreated;
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

        return result;
    }
}
