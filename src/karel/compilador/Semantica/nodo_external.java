/*
 Creado: 19/09/2015 , Hora: 01:23:39 PM
 */
package karel.compilador.Semantica;

/**
 *
 * @author axelmonroyx
 */
public class nodo_external {

    String lexema;
    boolean parametro;
    boolean using_string;
    nodo_external sig = null;

    public nodo_external(String lexema, boolean parametro, boolean using_string) {
        this.lexema = lexema;
        this.parametro = parametro;
        this.using_string = using_string;
    }

}
