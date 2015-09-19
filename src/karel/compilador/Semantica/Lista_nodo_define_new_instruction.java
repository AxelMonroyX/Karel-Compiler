/*
 Creado: 19/09/2015 , Hora: 11:36:24 AM
 */
package karel.compilador.Semantica;

/**
 *
 * @author axelmonroyx
 */
public class Lista_nodo_define_new_instruction {

    //Atributos de la lista
    nodo_define_new_instruction Pri;
    nodo_define_new_instruction Ult;
    String Nom;

    //Constructor de la lista
    public Lista_nodo_define_new_instruction(String n) {
        Pri = Ult = null;
        Nom = n;
    }

    //Constructor
    public boolean Lista_nodo_define_new_instructionVacia() {//EN CASO DE  QUE LA LISTA ESTE VACIA
        if (Pri == null) {
            return true;
        } else {
            return false;
        }
    }

    //Nombre de la lista
    public Lista_nodo_define_new_instruction() {
        this("Lista_nodo_define_new_instruction");
    }

    //Metodo para insertar por el frente de la lista
    public void Instertar_Nodo_Inicio(String lexema, boolean parametro) {
        if (Lista_nodo_define_new_instructionVacia()) {
            Pri = Ult = new nodo_define_new_instruction(lexema, parametro);
        } else {
            Pri = new nodo_define_new_instruction(lexema, parametro);
        }
    }

    //Metodo para insertar por la parte posterior de la lista
    public void Insertar_Nodo_Final(String lexema, boolean parametro) {
        if (Lista_nodo_define_new_instructionVacia()) {
            Pri = Ult = new nodo_define_new_instruction(lexema, parametro);
        } else {
            Ult = Ult.sig = new nodo_define_new_instruction(lexema, parametro);
        }
    }

    //Metodo para mostrar los datos de a lista
    public void Mostrar() {
        nodo_define_new_instruction Actual = Pri;
        if (Lista_nodo_define_new_instructionVacia()) {
            System.out.println("La " + Nom + " esta vacia");
        }

        while (Actual != null) {
            System.out.print(Actual.lexema);
            System.out.print("\t");
            System.out.println(Actual.parametro);
            Actual = Actual.sig;
        }
    }

    //Metodo para eliminar el frente de la lista
    public void Eliminar_Nodo_Inicio() {
        if (Lista_nodo_define_new_instructionVacia()) {
            Pri = Pri.sig;
        } else if (Pri.equals(Ult)) {
            Pri = Ult = null;
        } else {
            Pri = Pri.sig;
        }
    }

    //Metodo para eliminar el posterior de la lista
    public void Eliminar_Nodo_Final() {
        if (Lista_nodo_define_new_instructionVacia()) {
            Pri = Pri.sig;
        } else if (Pri.equals(null)) {
            Ult = null;
        } else {
            Pri = Pri.sig;
        }
    }

    //Metodo para buscar en la lista de inicion a fin
    public boolean nodoEncontrado(String xlexema, boolean xparametro) {
        boolean encontrado=false;
        if (!Lista_nodo_define_new_instructionVacia()) {
            nodo_define_new_instruction Actual = Pri;
            
            while (Actual != null ) {
                
                if (Actual.lexema.equals(xlexema)) {
                    if (Actual.parametro==xparametro) {
                        encontrado=true;
                        break;
                    }
                }
                Actual = Actual.sig;
            }

        }
        return encontrado;
    }
}
