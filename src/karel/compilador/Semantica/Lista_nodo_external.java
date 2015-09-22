/*
 Creado: 19/09/2015 , Hora: 01:27:58 PM
 */
package karel.compilador.Semantica;

/**
 *
 * @author axelmonroyx
 */
public class Lista_nodo_external {

    //Atributos de la lista
    nodo_external Pri;
    nodo_external Ult;
    String Nom;

    //Constructor de la lista
    public Lista_nodo_external(String n) {
        Pri = Ult = null;
        Nom = n;
    }

    //Constructor
    public boolean Lista_nodo_externalVacia() {//EN CASO DE  QUE LA LISTA ESTE VACIA
        if (Pri == null) {
            return true;
        } else {
            return false;
        }
    }

    //Nombre de la lista
    public Lista_nodo_external() {
        this("Lista_nodo_external");
    }

    //Metodo para insertar por el frente de la lista
    public void Instertar_Nodo_Inicio(String lexema, boolean parametro, boolean using_string) {
        if (Lista_nodo_externalVacia()) {
            Pri = Ult = new nodo_external(lexema, parametro, using_string);
        } else {
            Pri = new nodo_external(lexema, parametro, using_string);
        }
    }

    //Metodo para insertar por la parte posterior de la lista
    public void Insertar_Nodo_Final(String lexema, boolean parametro, boolean using_string) {
        if (Lista_nodo_externalVacia()) {
            Pri = Ult = new nodo_external(lexema, parametro, using_string);
        } else {
            Ult = Ult.sig = new nodo_external(lexema, parametro, using_string);
        }
    }

    //Metodo para mostrar los datos de a lista
    public void Mostrar() {
        nodo_external Actual = Pri;
        if (Lista_nodo_externalVacia()) {
            System.out.println("La " + Nom + " esta vacia");
        }
        System.out.println("Lexema"+"\t"+"Parametro"+"\t"+"Using");
        while (Actual != null) {
            System.out.print(Actual.lexema);
            System.out.print("\t");
            System.out.print(Actual.parametro);
            System.out.print("\t");
            System.out.println(Actual.using_string);
            Actual = Actual.sig;
        }
    }

    //Metodo para eliminar el frente de la lista
    public void Eliminar_Nodo_Inicio() {
        if (Lista_nodo_externalVacia()) {
            Pri = Pri.sig;
        } else if (Pri.equals(Ult)) {
            Pri = Ult = null;
        } else {
            Pri = Pri.sig;
        }
    }

    //Metodo para eliminar el posterior de la lista
    public void Eliminar_Nodo_Final() {
        if (Lista_nodo_externalVacia()) {
            Pri = Pri.sig;
        } else if (Pri.equals(null)) {
            Ult = null;
        } else {
            Pri = Pri.sig;
        }
    }

    //Metodo para buscar en la lista de inicion a fin
    public boolean nodoEncontrado(String xlexema, boolean xparametro, boolean xusing_string) {
        boolean encontrado = false;
        if (!Lista_nodo_externalVacia()) {
            nodo_external Actual = Pri;

            while (Actual != null) {

                if (Actual.lexema.equals(xlexema)) {

                    encontrado = true;
                    break;

                }
                Actual = Actual.sig;
            }

        }
        return encontrado;
    }
}
