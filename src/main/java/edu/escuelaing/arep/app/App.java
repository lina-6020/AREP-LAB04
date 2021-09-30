package edu.escuelaing.arep.app;

import static spark.Spark.port;
import static spark.Spark.get;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String... args){
        port(getPort());
        get("hello", (req,res) -> "Hello Docker!");
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }
}
