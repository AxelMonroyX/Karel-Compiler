/*
 Creado: 31-may-2015 , Hora: 12:56:33
 */
package karel.compilador;

import javax.swing.JOptionPane;

/**
 *
 * @author Axel Monroy <xaxelmonroyx@gmail.com>
 */
class Sintaxis {

    nodo p;
    String errores[][] = {
        // 0              						 1   <------numero de columna
        /*0*/{"Se esperaba *", "500"},
        /*1*/ {"Se esperaba * ó /", "501"},
        /*2*/ {"Fin de archivo inesperado", "502"},
        /*3*/ {"Simbolo no valido", "503"},
        /*4*/ {"Se esperaba la palabra beginning-of-program", "504"},
        /*5*/ {"Sintaxis de MethodDeclaration erronea", "505"},
        /*6*/ {"Sintaxis de LinkDeclaration erronea", "506"},
        /*7*/ {"Se esperaba la palabra define-new-instruction", "507"},
        /*8*/ {"Se esperaba un identificador", "508"},
        /*9*/ {"Se esperaba un Statement despues del idenficador", "509"},
        /*10*/ {"Se esperaba la palabra  end-of-program", "510"},
        /*11*/ {"Sintaxis de MainDeclaration", "511"},
        /*12*/ {"Se esperaba la palabra end-of-execution", "512"},
        /*13*/ {"Statement mal formado", "513"},
        /*14*/ {"Se esperaba la palabrabeggining-of-execution", "514"},
        /*15*/ {"Se esperaba un ; ", "515"},
        /*16*/ {"Se esperaba la palabra if ", "516"},
        /*17*/ {"Expresion mal formada ", "517"},
        /*18*/ {"Se espera una o mas sentencias dentro del bloque begin  ", "518"},
        /*19*/ {"Block statement mal formado  ", "519"},
        /*20*/ {"If statement mal formado  ", "520"},
        /*21*/ {"While statement mal formado  ", "521"},
        /*22*/ {"Iterate statement mal formado  ", "522"},
        /*23*/ {"Call statement mal formado  ", "523"}

    };

    private void ImprimirError(int errorIdentificado) {

        for (int i = 0; i < errores.length; i++) {
            if (errorIdentificado == Integer.valueOf(errores[i][1])) {
                String color = "\u001B[31m";
                String mensaje=( "Error:     " + errores[i][0] + "    " + errores[i][1] + "  EN EL RENGLON     " + p.num_renglon);
                System.out.println(color+mensaje);
                JOptionPane.showMessageDialog(null, mensaje, "Sintaxis",1);
                //error_encontrado=true;
            }
        }

    }

    Sintaxis(nodo cabeza) {
        p = cabeza;
        System.out.println("----------------------------------------");
        //System.out.println("Primer Nodo: \t\t\t" + p.lexema + " \t\t" + p.token + " \t\t" + p.num_renglon);
        if (identificarProgramDeclaration()) {
            System.out.println("Sintaxis correcta");
            JOptionPane.showMessageDialog(null, "Sintaxis Correcta");
        }else{
           
            JOptionPane.showMessageDialog(null, "Sintaxis Incorrecta");
        }

        //System.out.println("Ultimo Nodo: \t\t\t" + p.lexema + " \t\t" + p.token + " \t\t" + p.num_renglon);

    }

    private boolean identificarProgramBody() {
        if (p.token == 203 || p.token == 205) {
            if (idetificarMethod_Link_Declarations()) {
                if (p.token == 207) { //beggining-of-execution
                    //p = p.sig;
                    if (identificarMainDeclaration()) {

                        return true;
                    } else {
                        ImprimirError(511);
                    }
                } else {
                    ImprimirError(514);
                }
            }
        } else if (p.token == 207) { //beggining-of-execution
            if (identificarMainDeclaration()) {
                //p = p.sig;
                return true;
            } else {
                ImprimirError(511);
            }
        }
        return false;
    }

    private boolean identificarProgramDeclaration() {
        if (p.token == 201) {// beginning-of-program
            p = p.sig;

            if (identificarProgramBody()) {
                if (p.token == 202) {//end-of-program
                    // p = p.sig;
                    return true;
                } else {
                    ImprimirError(510);
                }
            }
        } else {
            ImprimirError(504);
            return false;
        }
        return false;

    }

    private boolean identificarMethodDeclaration() {
        if (p.token == 203) {//define-new-instruction
            p = p.sig;
            if (p.token == 101) { // id
                p = p.sig;
                if (p.token == 204) {// as
                    p = p.sig;
                    if (identificarStatement()) {
                        //p = p.sig;
                        return true;
                    } else {
                        ImprimirError(509);
                    }
                } else {
                    if (p.token == 103) { //(
                        p = p.sig;
                        if (p.token == 101) { // id
                            p = p.sig;
                            if (p.token == 104) {//)
                                p = p.sig;
                                if (p.token == 204) {// as
                                    p = p.sig;
                                    if (identificarStatement()) {
                                        //p = p.sig;
                                        return true;
                                    } else {
                                        ImprimirError(509);
                                    }
                                }
                            }
                        }

                    }
                }
            } else {
                ImprimirError(508);
            }
        } else {
            ImprimirError(507);
        }
        return false;
    }

    private boolean idetificarMethod_Link_Declarations() {
        if (p.token == 203) { //define-new-instruction
            if (identificarMethodDeclaration()) {
                if (p.token == 105) {  // ;
                    nodo aux = p.sig;
                    if (aux.token == 203 || aux.token == 205) {
                        p = p.sig;
                    }

                    if (p.token == 203 || p.token == 205) {// define-new-instruction   external
                        if (idetificarMethod_Link_Declarations()) {
                            //p = p.sig;
                            return true;
                        }
                    } else {
                        p = p.sig;
                        return true;
                    }
                } else {
                    ImprimirError(515);
                }
            } else {
                ImprimirError(505);
            }
        }
        if (p.token == 205) { //external
            //p = p.sig;
            if (identificarLinkDeclaration()) {
                if (p.token == 105) {  // ;
                    p = p.sig;
                    if (p.token == 203 || p.token == 205) {// define-new-instruction   external
                        if (idetificarMethod_Link_Declarations()) {
                            //p = p.sig;
                            return true;
                        }
                    } else {
                        p = p.sig;
                        return true;
                    }
                }
            } else {
                ImprimirError(506);
            }
        }

        return false;

    }

    private boolean identificarLinkDeclaration() {
        if (p.token == 205) { //external
            p = p.sig;
            if (p.token == 101) { // id
                p = p.sig;
                if (p.token == 105) { //;
                    return true;
                } else {
                    if (p.token == 103) {//(
                        p = p.sig;
                        if (p.token == 101) { //id
                            p = p.sig;
                            if (p.token == 104) { // )
                                p = p.sig;
                                if (p.token == 105) { //;
                                    return true;
                                } else {
                                    if (p.token == 206) { //using
                                        p = p.sig;
                                        if (p.token == 106) { //string
                                            p = p.sig;
                                            if (p.token == 105) { //;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (p.token == 206) { //using
                            p = p.sig;
                            if (p.token == 106) {//string
                                p = p.sig;
                                if (p.token == 105) { //;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean identificarStatement() {
        if (p.token == 209) { //begin   del BLOCK
            //p = p.sig;
            if (identificarBlock_Statement()) {
                return true;
            }else{
                ImprimirError(519);
            }
        }
        if (p.token == 217) { //if   del ifstatement
            //p = p.sig;
            if (identificarIf_Statement()) {
                return true;
            }else{
                ImprimirError(520);
            }
        }
        if (p.token == 220) { //while del whilestatement
            //p = p.sig;
            if (identificarWhile_Statement()) {
                return true;
            }else{
                ImprimirError(521);
            }
        }
        if (p.token == 222) { //iterate   del iterate statement
            //p = p.sig;
            if (identificarIterate_Statement()) {
                return true;
            }else{
                ImprimirError(522);
            }
        }
        if (p.token == 224) { //turnoff   del turnoff statement
            p = p.sig;
            if (identificarTurnoff_Statement()) {
                return true;
            }
        }
        if (p.token == 225) { //turnleft  del turnleft statement
            p = p.sig;
            if (identificarTurnleft_Statement()) {
                return true;
            }
        }
        if (p.token == 226) { //move  del move statement
            p = p.sig;
            if (identificarMove_Statement()) {
                return true;
            }
        }
        if (p.token == 227) { //pickbeeper  del pickbeeper statement
            p = p.sig;
            if (identificarPickbeeper_Statement()) {
                return true;
            }
        }
        if (p.token == 228) { //putbeeper  del putbeeper statement
            //p = p.sig;
            if (identificarPutbeeper_Statement()) {
                return true;
            }
        }
        if (p.token == 101) { //call staement
            //p = p.sig;
            if (identificarCall_Statement()) {
                return true;
            }else{
                ImprimirError(523);
            }
        }
        return false;
    }

    private boolean identificarMainDeclaration() {
        if (p.token == 207) { //beggining-of-execution
            p = p.sig;
            if (identificarStatements()) {

                //p = p.sig;
                if (p.token == 208) { //end-of-execution
                    p = p.sig;
                    return true;
                } else {
                    ImprimirError(512);
                }

            }
        }
        return false;
    }

    private boolean identificarStatements() {
        if (identificarStatement()) {
            //p=p.sig;
            if (p.token == 105) { //;) 
                p = p.sig;
                if (p.token == 208) {
                    //p=p.sig;
                    return true;
                } else if (identificarStatements()) {
                    return true;
                }
            } else {
                ImprimirError(515);
            }
        } else {
            ImprimirError(513);
        }
        return false;
    }

    private boolean identificarBlock_Statement() {
        if (p.token == 209) { //begin
            p = p.sig;
            if (identificarStatements_Block()) {

                if (p.token == 210) { //end
                    p = p.sig;
                    return true;
                } else {
                    ImprimirError(512);
                }

            }
            ImprimirError(518);
        }
        return false;
    }

    private boolean identificarIf_Statement() {
        if (p.token == 217) { //if
            p = p.sig;
            if (identificarExpresion()) {
                if (p.token == 218) {//then
                    p = p.sig;
                    if (identificarStatement()) {
                        if (p.token == 105) {
                            nodo aux = p.sig;

                            if (aux.token == 219) {//else
                                p = p.sig;
                            }

                            if (p.token == 219) { //else
                                p = p.sig;
                                if (identificarStatement()) {
                                    return true;
                                }
                            } else {
                                
                                return true;
                            }
                        }

                    }
                }
            } else {
                ImprimirError(517);
            }

        } else {
            ImprimirError(516);
        }
        return false;
    }

    private boolean identificarWhile_Statement() {
        if (p.token == 220) { //while
            p = p.sig;
            if (identificarExpresion()) {
                if (p.token == 221) { //do
                    p = p.sig;
                    if (identificarStatement()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean identificarIterate_Statement() {
        if (p.token == 222) { //iterate
            p = p.sig;
            if (p.token == 102) { // num
                p = p.sig;
                if (p.token == 223) { //times
                    p = p.sig;
                    if (identificarStatement()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean identificarTurnoff_Statement() {
        return true;
    }

    private boolean identificarTurnleft_Statement() {
        return true;
    }

    private boolean identificarMove_Statement() {
        return true;
    }

    private boolean identificarPickbeeper_Statement() {
        return true;
    }

    private boolean identificarPutbeeper_Statement() {
        return true;
    }

    private boolean identificarStatements_Block() {
        if (identificarStatement()) {
            if (p.token == 105) { //;) 
                p = p.sig;
                if (p.token == 210) { //end
                    //p = p.sig;
                    return true;
                } else if (identificarStatements_Block()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean identificarExpresion() {
        if (identificarAndClause()) {
            if (p.token == 213) { //or
                p = p.sig;
                if (identificarExpresion()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean identificarAndClause() {
        if (identificarNotClause()) {
            if (p.token == 214) { //and
                p = p.sig;
                if (identificarAndClause()) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean identificarNotClause() {
        if (p.token == 215) { //not
            p = p.sig;
            if (identificarAtomClause()) {
                return true;
            }

        } else {
            if (identificarAtomClause()) {
                return true;
            }
        }
        return false;
    }

    private boolean identificarAtomClause() {
        if (p.token == 216) { //iszero
            p = p.sig;
            if (p.token == 103) { // (
                p = p.sig;
                if (p.token == 102) { // num
                    p = p.sig;
                    if (p.token == 104) { // )
                        p = p.sig;
                        return true;
                    }
                }
            }
        } else {
            if (p.token == 103) { // (
                p = p.sig;
                if (identificarExpresion()) {
                    if (p.token == 104) { // )
                        p = p.sig;
                        return true;
                    }
                }
            } else {
                if (identificarBooleanFunction()) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean identificarBooleanFunction() {
        if (p.token >= 229 && p.token < 246) {
            p = p.sig;
            return true;
        }

        return false;
    }

    private boolean identificarCall_Statement() {
        if (p.token == 101) { //id
            p = p.sig;
            if (p.token == 105) { // ;
                return true;
            } else {
                if (p.token == 103) { // (
                    p = p.sig;
                    if (p.token == 102) { //num
                        p = p.sig;
                        if (p.token == 104) { // )
                            p = p.sig;
                            if (p.token == 105) { //;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

}

