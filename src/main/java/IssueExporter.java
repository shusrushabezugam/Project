
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class IssueExporter {

    private static Scanner scanner;

    public void exportIssues() throws FileNotFoundException {
        // public String getCredentials(){
        System.out.println("Please enter GitHub username");
        scanner = new Scanner(System.in);
        String gn = scanner.next();
        System.out.println("Please enter GitHub password");
        String pwd = scanner.next();
        PrintStream out = null;
        try{
        out = new PrintStream(new FileOutputStream("issues.txt"));
        GitHubRestClient client = new GitHubRestClient();
        String openjson = client.requestIssues(gn, pwd, "open");
        String closedjson = client.requestIssues(gn, pwd, "closed");
        IssueParser issuesparser = new IssueParser();
        List<Issue> openissues = issuesparser.parseIssues(openjson);
        List<Issue> closedissues = issuesparser.parseIssues(closedjson);
        openissues.addAll(closedissues);
        Collections.sort(openissues);
        out.println(openissues);
        System.out.print("Total no of issues " + openissues.size());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                out.close();

            }
        }
        out.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        IssueExporter ie = new IssueExporter();
        ie.exportIssues();

    }
}
