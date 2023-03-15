import java.io.*;

public class GloboAL {
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

    static String[] palabraReservada = new String[15];

    public static void main(String argumento[]) {
        entrada = argumento[0] + ".txt";
        salida = argumento[0] + ".anl";
        palabraReservada[0]= "print";
        palabraReservada[1] = "else";
        if(!xArchivo(entrada).exists()) {
            System.out.println("El archivo: " + entrada + " no existe,");
            System.exit(4);
        }
        linea = abreLeeCierra(entrada);
        do {
            ESTADO = 0;
            diag = 0;
            miToken = token();
            if (!miToken.equals("basura")){
                creaArchivo(xArchivo(salida), miToken);
                creaArchivo(xArchivo(salida), lex);
                creaArchivo(xArchivo(salida), Integer.toString(renglon));
            }
            /*
            System.out.println("Este es el token hallado r(" + miToken + ")");
            System.out.println("Este es el lexema hallado {" + lex + "}");
            System.out.println("Renglon (" + renglon + ")");
            */
            //pausa();
            a_i = a_a;
        }while (!fin_archivo);
        creaArchivo(xArchivo(salida), "eof");
        creaArchivo(xArchivo(salida), "eof");
        creaArchivo(xArchivo(salida), "666");
        System.out.println("Analisis lexicografico exitoso.");
    }

    public static boolean isReservada(String x){
        for (int i=0; i <=1; i++){
            if (palabraReservada[i].equals(x)){
                return true;
            }
        }
        return false;
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
        return x == 9 || x == 10 || x == 13 || x == 32;
    }

    public static Boolean es_delim_espacio(int x){
        return x == 9 || x == 10 || x == 13;
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
            case 0:
                diag = 5;
                break;
            case 5:
                diag = 9;
                break;
            case 9:
                diag = 13;
                break;
            case 13:
                diag = 16;
                break;
            case 16:
                diag = 21;
                break;
            case 21:
                diag = 24;
                break;
            case 24:
                diag = 30;
                break;
            case 30:
                diag = 32;
                break;
            case 32:
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
            switch ( ESTADO ) {
                case 0:
                    c = lee_car();
                    switch (c) {
                        case '(':
                            ESTADO = 1;
                            break;
                        case ')':
                            ESTADO = 2;
                            break;
                        case '{':
                            ESTADO = 3;
                            break;
                        case '}':
                            ESTADO = 4;
                            break;
                        default:
                            ESTADO = diagrama();
                    }
                    break;
                case 1:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("(");
                case 2:
                    lex = ob_lex();
                    a_i = a_a;
                    return (")");
                case 3:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("{");
                case 4:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("}");
                case 5:
                    c = lee_car();
                    if (c == 'i') {
                        ESTADO = 6;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 6:
                    c = lee_car();
                    if (c == 'f') {
                        ESTADO = 7;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 7:
                    c = lee_car();
                    ESTADO = 8;
                    break;
                case 8:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("if");
                case 9:
                    c = lee_car();
                    if (c >= 65 && c <= 90) {
                        ESTADO = 10;
                    } else if (c >= 97 && c <= 112) {
                        ESTADO = 10;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 10:
                    c = lee_car();
                    if (c >= 65 && c <= 90) {
                        ESTADO = 10;
                    } else if (c >= 97 && c <= 122) {
                        ESTADO = 10;
                    } else if (c >= 48 && c <= 57) {
                        ESTADO = 10;
                    } else if (c == '_') {
                        ESTADO = 11;
                    } else {
                        ESTADO = 12;
                    }
                    break;
                case 11:
                    c = lee_car();
                    if (c >= 65 && c <= 90) {
                        ESTADO = 10;
                    } else if (c >= 97 && c <= 122) {
                        ESTADO = 10;
                    } else if (c >= 48 && c <= 57) {
                        ESTADO = 10;
                    } else if (c == '_') {
                        ESTADO = 11;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 12:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    if(isReservada(lex)) {
                        return (lex);
                    }
                    return ("id");
                case 13:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 14;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 14:
                    c = lee_car();
                    if (es_delim(c)) {
                        ESTADO = 14;
                    } else {
                        ESTADO = 15;
                    }
                    break;
                case 15:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("basura");
                case 16:
                    c = lee_car();
                    if (c >= 46 && c <= 57) {
                        ESTADO = 17;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 17:
                    c = lee_car();
                    if (c >= 46 && c <= 57) {
                        ESTADO = 17;
                    } else if (c == '.') {
                        ESTADO = 18;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 18:
                    c = lee_car();
                    if (c >= 46 && c <= 57) {
                        ESTADO = 19;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 19:
                    c = lee_car();
                    if (c >= 46 && c <= 57) {
                        ESTADO = 19;
                    } else {
                        ESTADO = 20;
                    }
                    break;
                case 20:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("num");
                case 21:
                    c = lee_car();
                    if (c >= 46 && c <= 57) {
                        ESTADO = 22;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 22:
                    c = lee_car();
                    if (c >= 46 && c <= 57) {
                        ESTADO = 22;
                    } else {
                        ESTADO = 23;
                    }
                    break;
                case 23:
                    a_a--;
                    lex = ob_lex();
                    a_i = a_a;
                    return ("num");
                case 24:
                    c = lee_car();
                    switch (c) {
                        case '>':
                            ESTADO = 25;
                            break;
                        case '<':
                            ESTADO = 26;
                            break;
                        case '=':
                            ESTADO = 28;
                            break;
                        default:
                            ESTADO = diagrama();
                    }
                    break;
                case 25:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("mayor");
                case 26:
                    c = lee_car();
                    if (c == '=') {
                        ESTADO = 27;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 27:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("menor_ig");
                case 28:
                    c = lee_car();
                    if (c == '=') {
                        ESTADO = 29;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 29:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("igual");
                case 30:
                    c = lee_car();
                    if (c == 255) {
                        ESTADO = 31;
                    } else {
                        ESTADO = diagrama();
                    }
                    break;
                case 31:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("eof");
                case 32:
                    c = lee_car();
                    if (c == '"') {
                        ESTADO = 33;
                    } else {
                        ESTADO = diagrama();
                    }
                case 33:
                    c = lee_car();
                    if (c == '"') {
                        ESTADO = 34;
                    } else {
                        if (!es_delim_espacio(c)) {
                            ESTADO = 33;
                        } else {
                            ESTADO = diagrama();
                        }
                    }
                    break;
                case 34:
                    lex = ob_lex();
                    a_i = a_a;
                    return ("srt");
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
        for(int i = a_i; i < a_a; i++){
            lexema += linea[i];
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
            if(c == 10) {
                System.out.println(c);
                renglon++;
            }
            return linea[a_a++];
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
            System.out.println(exc);
        } catch(IOException exc) {
            System.out.println(exc);

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
