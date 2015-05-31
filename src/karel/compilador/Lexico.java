/*
 Creado: 31-may-2015 , Hora: 12:53:21
 */
package karel.compilador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
/**
 *
 * @author Axel Monroy <xaxelmonroyx@gmail.com>
 */

class Lexico {
    nodo cabeza=null,p;
    int estado=0,columna,valorMT,num_renglon=0;
    char caracter;
    String lexema="";
     String archivo="C:\\Users\\AxelMonroyX\\Downloads\\COMPI-MILLAN\\COMPI MILLAN\\codigo.txt";
     boolean bandReservada;
    
    boolean error_encontrado=false;
    
    int  matriz[][]={
    	//Matriz de Transicion
    //		0	1	2	3	4	5	6	7	8	9	10	11	12	13	14      15
	//Q	L	D	-	/	*	(	)	;	"	eb	eol	nl	tab	eof	oc      rt
	/*0*/  {1,      2,	503,	3,	503,	103,	104,	105,	7,	0,	0,	0,	0,	0,	503,    0},
	/*1*/  {1,	1,	1,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,	101,    101},
	/*2*/  {102,	2,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,	102,    102},
	/*3*/  {501,	501,	501,	6,	4,	501,	501,	501,	501,	501,	501,	501,	501,	501,	501,    501},
	/*4*/  {4,	4,	4,	4,	5,	4,	4,	4,	4,	4,	4,	4,	4,	500,	4,      4},
	/*5*/  {4,	4,	4,	0,	4,	4,	4,	4,	4,	4,	4,	4,	4,	500,	4,      4},
	/*6*/  {6,	6,	6,	6,	6,	6,	6,	6,	6,	6,	0,	0,	6,	0,	6,      0},
	/*7*/  {7,	7,	7,	7,	7,	7,	7,	7,	106,	7,	106,	106,	7,	502,	7,      106}
} ;
    String  errores[][]={
	// 0              						 1   <------numero de columna
	/*0*/  {"Se esperaba *", 				"500"},
	/*1*/  {"Se esperaba * ó /",			"501"},
	/*2*/  {"Fin de archivo inesperado", 	"502"},
	/*3*/  {"Simbolo no valido", 			"503"}
} ;
   
String palReservadas[][]={
	// 0              					 1   <------numero de columna
	/*0*/  {"beginning-of-program",		"201"},
	/*1*/  {"end-of-program",			"202"},
	/*2*/  {"define-new-instruction",	"203"},
	/*3*/  {"as",						"204"},
	/*4*/  {"external",					"205"},
	/*5*/  {"using",					"206"},
	/*6*/  {"beginning-of-execution",	"207"},
	/*7*/  {"end-of-execution",			"208"},
	/*8*/  {"begin",					"209"},
	/*9*/  {"end",						"210"},
	/*10*/ {"pred",						"211"},
	/*11*/ {"succ",						"212"},
	/*12*/ {"or",						"213"},
	/*13*/ {"and",						"214"},
	/*14*/ {"not",						"215"},
	/*15*/ {"iszero",					"216"},
	/*16*/ {"if",						"217"},
	/*17*/ {"then",						"218"},
	/*18*/ {"else",						"219"},
	/*19*/ {"while",					"220"},
	/*20*/ {"do",						"221"},
	/*21*/ {"iterate",					"222"},
	/*22*/ {"times",					"223"},
	/*23*/ {"turnoff",					"224"},
	/*24*/ {"turnleft",					"225"},
	/*25*/ {"move",						"226"},
	/*26*/ {"pickbeeper",                           	"227"},
	/*27*/ {"putbeeper",                                    "228"},
        /*27*/ {"front-is-clear",				"229"},
        /*27*/ {"front-is-blocked",				"230"},
        /*27*/ {"left-is-clear",				"231"},
        /*27*/ {"left-is-blocked",				"232"},
        /*27*/ {"right-is-clear",				"233"},
        /*27*/ {"right-is-blocked",				"234"},
        /*27*/ {"next-to-a-beeper",				"235"},
        /*27*/ {"not-next-to-a-beeper",				"236"},
        /*27*/ {"any-beepers-in-beeper-bag",			"237"},
        /*27*/ {"no-beepers-in-beeper-bag",			"238"},
        /*27*/ {"facing-north",                                 "239"},
        /*27*/ {"facing-south",                                 "240"},
        /*27*/ {"facing-east",                                  "241"},
        /*27*/ {"facing-west",                                  "242"},
        /*27*/ {"not-facing-north",                             "243"},
        /*27*/ {"not-facing-south",                             "244"},
        /*27*/ {"not-facing-east",                              "245"},
        /*27*/ {"not-facing-west",                              "246"}
        /*



| "notfacingnorth"
| "notfacingsouth"
| "notfacingeast"
| "notfacingwest"}*/
} ;

    public Lexico() throws FileNotFoundException, IOException {
 trabajarLexico();
    }//public LEXICO

    private void trabajarLexico() throws FileNotFoundException, IOException {
        FileReader file = new FileReader(archivo);
        BufferedReader buff = new BufferedReader(file);
        boolean enf = false;
        String linea = "";
        String linea2 = " ";
        int aux = 0;
        //caracter!=-1
        while ( !enf && linea != null) {
            num_renglon++;
            linea = buff.readLine();
            linea2=linea+"\n \r ";
            if (linea==null) {
                
            }
            if (linea == null) {
                insertarNodo();
               
            } else {
                for (int i = 0; i < linea2.length(); i++) {
                    caracter = linea2.charAt(i);

                    if (Character.isLetter(caracter)) {
                        columna = 0;
                    } else if (Character.isDigit(caracter)) {
                        columna = 1;
                    } else {
                        switch (caracter) {

                            case '-':
                                columna = 2;
                                break;
                            case '/':
                                columna = 3;
                                break;
                            case '*':
                                columna = 4;
                                break;
                            case '(':
                                columna = 5;
                                break;
                            case ')':
                                columna = 6;
                                break;
                            case ';':
                                columna = 7;
                                break;
                            case '"':
                                columna = 8;
                                break;
                            case '\r':
                                columna = 15;//retorno de carro
                                break;
                            case '\n':
                                columna = 11;//nueva linea
                                break;
                            case ' ':
                                columna = 9; //Espacio en Blanco
                                break;
                            case '\t':
                                columna = 12;//tab
                                break;
                            case 3:
                                columna = 13;//fin de texto
                                break;
                            default:
                                columna = 14;
                        }//switch
                    }//else character

                    valorMT = matriz[estado][columna];
                    //System.out.println("caracter leido: " + caracter);
                    //System.out.println("Lexema  " + lexema);

                    if (valorMT < 101) {
                        estado = valorMT;
                        //lexema+=caracter;

                        if (estado == 0) {
                            lexema = "";
                        } else {
                            lexema = lexema + caracter;
                        }
                    } else if (valorMT >= 100 && valorMT < 500) {//estado final
                        if (valorMT == 101) {
                            ValidarPalabraReservada();
                            i--;
                            if (bandReservada) {
                                lexema+=" ";
                            }
                        } else if (valorMT == 102) {
                            i--;

                        } else {
                            lexema += caracter;
                        }
                        //lexema += caracter;
                        
                        insertarNodo();
                        //System.out.println("Imprimir nodo: " + lexema + " " + valorMT + " " + num_renglon);
                        estado = 0;
                        lexema = "";
                        
                    } else {//estado de error
                        //valorMT=matriz[estado][columna];

                        ImprimirError();
                        error_encontrado=true;
                        //break;
                        // estado=0;
                    }
                }//for 
            }//if del for.
            
        }//while        
    }

    private void ValidarPalabraReservada() {
        bandReservada=false;
        for (int i = 0; i < palReservadas.length; i++) {
            if (lexema.equals(palReservadas[i][0])) {
                valorMT = Integer.valueOf(palReservadas[i][1]);
                bandReservada = true;
            } 
        }
    }

    private void ImprimirError() {
        for (int i = 0; i < errores.length; i++) {
            if (valorMT == Integer.valueOf(errores[i][1])) {
                String color = "\u001B[31m";
                String mensaje=("Error:     " + errores[i][1]+ "    " +errores[i][0]   + "  EN EL RENGLON     " + num_renglon);
                System.out.println(color+mensaje);
                JOptionPane.showMessageDialog(null, mensaje, "Léxico",1);
                //JOptionPane.showMessageDialo
                error_encontrado = true;
            }
        }
    }

    private void insertarNodo() {
        nodo nodo = new nodo(lexema, valorMT, num_renglon);
        if (cabeza == null) {
            cabeza = nodo;
            p = cabeza;
        } else {
            p.sig = nodo;
            p = nodo;
        }
    }
}

