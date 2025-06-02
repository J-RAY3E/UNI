package org.example.UserUtils;

import org.example.UserUtils.UserManager;
import org.example.Enums.RequestState;
import org.example.ReaderManager.Inputs.InputManagerRegistry;
import org.example.ReaderManager.Inputs.Response;
import org.example.ReaderManager.ResponseHandler;
import org.example.connection.NotificationManager;



public class ValidationPassword {

    public ValidationPassword(){

    }

    public String MainPanel(UserManager userManager, ResponseHandler responseHandler){

            System.out.println("===================================");
            System.out.println("     🌐 Welcome to My Application");
            System.out.println("===================================");
            System.out.println("Please select an option:");
            System.out.println("  1. 🔐 Register");
            System.out.println("  2. 👤 Login");
            System.out.println("  3. ❌ Exit");
            System.out.print(">> ");

            String statement = InputManagerRegistry.getInstance().getCurrentInput().nextLine();
            switch (statement.toLowerCase()) {
                case "register" -> {
                    return "registration user password";
                }
                case "login" -> {
                    return "authorization user password";
                }
                case "exit" -> {
                    return "exit";
                }

                default -> {
                    System.out.println("===================================");
                    System.out.println("        🔐 Login Required");
                    System.out.println("===================================");
                    return null;
                }
            }

    }

}
