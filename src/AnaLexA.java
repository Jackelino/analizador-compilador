import java.io.*;
public class AnaLexA {
    static int a_a = 0;
    static int a_i = 0;
    static int filesize = 0;
    static boolean fin_archivo = false;
    public static char[] linea = null;
    static int diag = 0;
    static int ESTADO = 0;
    static int c = 0;
    static String lex = "";

    public static void main(String argumento[]) {

        linea = abreLeeCierra("prueba.txt");
        for (int i = 0; i <= 5; i++) {
            System.out.println("linea [" + i+ "] = "+ linea[i]);
            pausa();
        }
        System.out.println("Inicio");
    }


    public static int diagrama() {
        a_a = a_i;
        switch (diag){
            case 1:
                diag = 4;
                break;
            case 4:
                diag = 9;
                break;
            case 9:
                diag = 13;
                break;
            case 13:
                error();
        }
        return (diag);
    }

    public static String token() {
        int c = 0;
        do {
            switch (ESTADO){
                case 1:
                    c = lee_car();
                    if(c == 'x'){
                        ESTADO = 2;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 2:
                    switch (c){
                        case 'x':
                            ESTADO = 2;
                            break;
                        case 'y':
                            ESTADO = 1;
                            break;
                        case 'z':
                            ESTADO = 3;
                            break;
                        default:
                            ESTADO = diagrama();
                    }
                    break;
                case 3:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("tok1");
                case 4:
                    c = lee_car();
                    // codigo acii
                    if(c >= 46 && c <= 57){
                        ESTADO = 6;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 5:
                    c = lee_car();
                    // codigo acii
                    if(c == '+'){
                        ESTADO = 7;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 6:
                    c = lee_car();
                    // codigo acii
                    if(c >= 46 && c <= 57){
                        ESTADO = 6;
                    } else {
                        ESTADO = 8;
                    }
                    break;
                case 7:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("tok2");
                case 8:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("tok3");
                case 9:
                    switch (c){
                        case 'n':
                            ESTADO = 9;
                            break;
                        case 'p':
                            ESTADO = 10;
                            break;
                        default:
                            ESTADO = diagrama();
                    }
                    break;
                case 10:
                    switch (c){
                        case 'p':
                            ESTADO = 10;
                            break;
                        case '.':
                            ESTADO = 11;
                            break;
                        default:
                            ESTADO = 12;
                    }
                    break;
                case 11:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("tok4");
                case 12:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("tok5");
                case 13:
                    c = lee_car();
                    // codigo acii
                    if(c == 255){
                        ESTADO = 14;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 14:
                    a_i = a_a;
                    return ("eof");
            }
        }while(true);
    }

    public static String ob_lex() {
        String x = "";
        for(int i = 0; i< linea.length; i++){
            x = x + linea[i];
        }
        return (x);
    }
    public static int lee_car() {
        if (a_a <= filesize - 1) {
            return (linea[a_a++]);
        } else {
            fin_archivo = true;
            return 255;
        }
    }

    public static char[] abreLeeCierra(String xName){
        File xFile = new File(xName);
        char[] data;
        try {
            FileReader fin = new FileReader(xFile);
            filesize = (int)xFile.length();
            data = new char[filesize+1];
            fin.read(data, 0, filesize);
            data[filesize] = ' ';
            filesize++;
            return (data);
        } catch (FileNotFoundException exc) {
            System.out.println("Inicio");
        } catch(IOException exc) {
            System.out.println("Inicio");

        }
        return null;
    }

    public static String pausa() {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nada = null;
        try {
            nada = entrada.readLine();
        } catch (Exception e){
            System.err.println(e);
        }
        return("");
    }

    public static void error() {
        System.out.println("ERROR: es usted un bruto!!!");
        System.exit(4);
    }

}
