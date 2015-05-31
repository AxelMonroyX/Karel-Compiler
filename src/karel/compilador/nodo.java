/*
 Creado: 31-may-2015 , Hora: 12:54:46
 */
package karel.compilador;

/**
 *
 * @author Axel Monroy <xaxelmonroyx@gmail.com>
 */
class nodo {

    String lexema;
    int num_renglon;
    int token;
    nodo sig = null;

    nodo(String lexema, int token, int num_renglon) {
        this.lexema = lexema;
        this.token = token;
        this.num_renglon = num_renglon;
    }
}
