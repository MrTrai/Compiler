import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class frontEnd {

    public static void main(String[] args) throws IOException, InterruptedException {
        //CALL: grammarFilePath, nameOfMasterToken, tigerFilePath, -gui (optional)
        String grammarFile = args[0];
        String masterToken = args[1];
        String tigerFile = args[2];
        Runtime terminal = Runtime.getRuntime();
        String command = "antlr4.bat " + grammarFile;
        System.out.println(command);
        System.out.println("DONE");
        ExecuteWaitForAndPrintResults(terminal, command);
        String[] nameSplit = grammarFile.split("\\\\");
        String name = nameSplit[nameSplit.length - 1];
        String nameNoExtend = name.split("\\.")[0];
        command = "javac " + nameNoExtend + "*.java";
        System.out.println(command);
        ExecuteWaitForAndPrintResults(terminal, command);
        System.out.println("DONE");
        if (args.length == 3) {
            command = "grun.bat " + nameNoExtend + " " + masterToken + " " + tigerFile;
        } else {
            command = "grun.bat " + nameNoExtend + " " + masterToken + " -gui " + tigerFile;
        }
        System.out.println(command);
        ExecuteWaitForAndPrintResults(terminal, command);
        System.out.println("DONE");
    }

    private static void ExecuteWaitForAndPrintResults(Runtime theRunTime, String command) throws IOException, InterruptedException {
        Process execution = theRunTime.exec(command);
        BufferedReader error = new BufferedReader(new InputStreamReader(execution.getErrorStream()));
        String line = null;
        while ((line = error.readLine()) != null) {
            System.out.println(line);
        }
        execution.waitFor();
    }
}