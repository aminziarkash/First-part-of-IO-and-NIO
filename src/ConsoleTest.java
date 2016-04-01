import java.io.Console;

public class ConsoleTest {
    
    public static void main(String[] args) {
        ConsoleTest app = new ConsoleTest();
        app.workingWithConsoleExample();
    }
    
    private void workingWithConsoleExample() {
        System.out.println("\nUSING readPassword() and readLine() METHODS FROM Console CLASS..\n");
        String name = "";
        Console console = System.console();
        char[] pw;
        
        if (console != null) {
            
            MyUtility mu = new MyUtility();
            while (true) {
                // using readPassword() method
                pw = console.readPassword("%s", "\nEnter password\t:\t");
                System.out.print("Output\t\t:\t");
                for (char ch : pw) {
                    console.format("%c", ch);
                }
                console.format("\n\n");
                
                // using readLine() method
                name = console.readLine("%s", "Enter string\t:\t");
                console.format("Output\t\t:\t%s\n\n", mu.doStuff(name));
            }
        }
    }
}

class MyUtility {
    String doStuff(String arg) {
        return arg;
    }
}