package karel.compilador;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Axel Monroy <xaxelmonroyx@gmail.com>
 */
public class KarelCompilador {

    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

//        
//        Principal _Principal;
//        _Principal = new Principal();
//       _Principal.setVisible(true);
        Lexico _Lexico = new Lexico();

        _Lexico.p = _Lexico.cabeza;
        System.out.println("");
        while (_Lexico.p.sig != null) {
            if ((int) _Lexico.p.token >= 200) {
                String color = "\u001B[34m";
                System.out.println(color + "Imprimir nodo: \t\t\t" + _Lexico.p.lexema + " \t\t\t\t\t" + _Lexico.p.token + " \t\t" + _Lexico.p.num_renglon);
            } else {
                System.out.println("Imprimir nodo: \t\t\t" + _Lexico.p.lexema + " \t\t\t\t\t" + _Lexico.p.token + " \t\t" + _Lexico.p.num_renglon);
            }
            _Lexico.p = _Lexico.p.sig;
        }
        //Sintaxis
        if (!_Lexico.error_encontrado) {
            JOptionPane.showMessageDialog(null, "Lexico Correcto");
            System.out.println("\033[32mLEXICO TERMINADO");
            Sintaxis _Sintaxis = new Sintaxis(_Lexico.cabeza);
        }
        if (_Lexico.error_encontrado) {
            System.out.println("LEXICO NO TERMINADO");
            System.out.println("\033[31mLEXICO NO TERMINADO");
        }

    }

}
