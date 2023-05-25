import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Compilador utilizando parser recursivo desendennte
 */
public class globoPRD {
    static int Posicion = 0;
    static String CAB = "";
    static String LEX = "";
    static String RENGLON = "";
    /**
     * Nombre del archivo para el Analisis
     */
    static String entrada = "";
    /**
     * Nombre del archivo para la salida del analisis.
     */
    static String salida = "";

    public static void main(String argumento[]){
        entrada = argumento[0] + ".anl";
        if(!xArchivo(entrada).exists()) {
            System.out.println("El archivo: " + entrada + " no existe,");
            System.exit(4);
        }
        sig_cab(xArchivo(entrada));
        IF();
        if(!CAB.equals("eof")) {
            error();
            System.exit(4);
        }
        System.out.println("Analisis sintatico Exitoso!!!");

    }

    public static void asocia(String token) {
        if (CAB.equals(token)) {
            sig_cab(xArchivo(entrada));
        } else {
            error();
        }
    }

    public static void E() {
        switch (CAB) {
            case "id":
                asocia("id");
                break;
            case "num":
                asocia("num");
                break;
            default:
                error();
        }
    }

    public static void E2() {
        switch (CAB) {
            case "id":
                E();
                break;
            case "num":
                E();
                break;
            case "str":
                asocia("str");
                break;
            default:
                error();
        }
    }

    public static void BLQ_PRIMA() {
        if (CAB.equals("else")) {
            asocia("else");
            asocia("{");
            CINS();
            asocia("}");
        }
    }

    public static void BLQ() {
        if (CAB.equals("{")) {
            asocia("{");
            CINS();
            asocia("}");
            BLQ_PRIMA();
        } else {
            error();
        }
    }

    public static void EXP() {
        if (CAB.equals("id") || CAB.equals("num")) {
            E();
            OP();
            E();
        } else {
            error();
        }
    }

    public static void OP() {
        switch (CAB) {
            case "mayor":
                asocia("mayor");
                break;
            case "menor_ig":
                asocia("menor_ig");
                break;
            case "igual":
                asocia("igual");
                break;
            default:
                error();
        }
    }

    public static void IF() {
        if (CAB.equals("if")) {
            asocia("if");
            asocia("(");
            EXP();
            asocia(")");
            BLQ();
        } else {
            error();
        }
    }

    public static void I() {
        switch (CAB) {
            case "print":
                asocia("print");
                asocia("(");
                E2();
                asocia(")");
                break;
            case "if":
                IF();
                break;
            default:
                error();
        }
    }

    public static void CINS() {
        if (CAB.equals("print") || CAB.equals("if")) {
            I();
            CINS();
        }
    }

    public static void sig_cab(File xFile) {
        try {
            FileReader fr = new FileReader(xFile);
            BufferedReader br = new BufferedReader(fr);
            long NoSirve = br.skip(Posicion);
            String linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            CAB = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            LEX = linea;
            linea = br.readLine();
            Posicion = Posicion + linea.length() + 2;
            RENGLON = linea;
            fr.close();
            System.out.println(".");
        } catch (IOException e) {
            System.out.println("Errorsote");
        }

    }

    public static void error() {
        System.out.println("ERROR: en el  [ " + CAB + "] cerca del renglon: " + RENGLON);
        System.exit(4);
    }

    public static File xArchivo(String archivo){
        return (new File(archivo));
    }
}
