import java.io.*;
public class AnaLexA {
    /**
     * Apuntador de avance
     */
    static int a_a = 0;
    /**
     * Aountador de inicio
     */
    static int a_i = 0;
    static int filesize = 0;
    static boolean fin_archivo = false;
    /**
     * linea del archivo leida
     */
    public static char[] linea = null;
    /**
     * diagrama del analozis
     */
    static int diag = 0;
    /**
     * estado del diagrama
     */
    static int ESTADO = 0;
    static int c ;
    /**
     * Lexema de la exprecion
     */
    static String lex = "";
    /**
     * Nombre del archivo para el Analisis
     */
    static String entrada = "";
    /**
     * Nombre del archivo para la salida del analisis.
     */
    static String salida = "";
    /**
     * Token encontrado de los estados.
     */
    static String miToken = "";
    /**
     * Número del renglon leido del archivo.
     */
    static int renglon = 1;

    public static void main(String argumento[]) {
        entrada = argumento[0] + ".txt";
        salida = argumento[0] + ".anl";
        if(!xArchivo(entrada).exists()) {
            System.out.println("El archivo: " + entrada + " no existe,");
            System.exit(4);
        }
        linea = abreLeeCierra(entrada);
        do {
            ESTADO = 1;
            diag = 1;
            miToken = token();
            if (!miToken.equals("nosirve")){
                creaArchivo(xArchivo(salida), miToken);
                creaArchivo(xArchivo(salida), lex);
                creaArchivo(xArchivo(salida), Integer.toString(renglon));
            }

            System.out.println("Este es el token hallado r(" + miToken + ")");
            System.out.println("Este es el lexema hallado {" + lex + "}");
            //pausa();
            a_i = a_a;
        }while (!fin_archivo);
        System.out.println("Analisis lexicografico exitoso.");
    }

    public static Boolean creaArchivo(File xFile, String mensaje) {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(xFile, true));
            fileOut.println(mensaje);
            fileOut.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    /**
     * determina si es un espacio, enter o tabulador
     * @param x
     * @return
     */
    public static Boolean es_delim(int x){
        if(x == 9 || x == 10 || x == 13 || x==32){
            return (true);
        }
        return (false);
    }

    /**
     * Crea un archivo
     *
     * @param archivo nombre del archivo
     * @return
     */
    public static File xArchivo(String archivo){
        return (new File(archivo));
    }

    /**
     * Determina el diagrama para realizar el analizis
     *
     * @return int diagrama que utilizara para el analisis
     */
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
                diag = 15;
                break;
            case 15:
                error();
        }
        return (diag);
    }

    /**
     * Determina el token del analisis de los estados en los diagramas
     *
     * @return String token de la exprecion identificado.
     */
    public static String token() {
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
                    c = lee_car();
                    switch (c){
                        case 'x':
                            ESTADO = 1;
                            break;
                        case 'y':
                            ESTADO = 2;
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
                    } else if (c == 'm') {
                        ESTADO = 5;
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
                    c = lee_car();
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
                    c = lee_car();
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
                case 15:
                    c = lee_car();
                    if(es_delim(c)){
                        ESTADO = 16;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 16:
                    c = lee_car();
                    if(es_delim(c)){
                        ESTADO = 16;
                    } else {
                        ESTADO = 17;
                    }
                    break;
                case 17:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("nosirve");
            }
        }while(true);
    }

    /**
     * Ontiene el lexema que encuentra
     *
     * @return String lexema encontrado.
     */
    public static String ob_lex() {
        String lexema = "";
        for(int i = a_i; i< a_a; i++){
            lexema = lexema + linea[i];
        }
        return (lexema);
    }

    /**
     * Determina si no existe el fin del archivo para seguir
     * con la leectura del archivo.
     *
     * @return int
     */
    public static int lee_car() {
        if (a_a <= filesize - 1) {
            if(linea[a_a] == 10){
                renglon++;
            }
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
        System.out.println("ERROR: en el carácter [ " + (char)c + "] cerca del renglon: " +  renglon);
        System.exit(4);
    }

}
