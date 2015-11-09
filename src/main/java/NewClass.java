/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class NewClass {

    public String metodoRAro () {
        int[] lista = {8, 9, 3};
        int n = lista.length;
        String[] simbolo = new String[n];
        int i, j, aux, izq, der, m;
        for (i = 1; i < n; i++) {
            aux = lista[i];
            izq = 0;
            der = i - 1;
            while (izq <= der) {
                m = ((izq + der) / 2);
                if (aux < lista[m]) {
                    der = m - 1;
                    simbolo[i - 1] = "-";
                } else {
                    izq = m + 1;
                    simbolo[i - 1] = "+";
                }
            }
            j = i - 1;
            while (j >= izq) {
                lista[j + 1] = lista[j];
                j = j - 1;
            }
            lista[izq] = aux;
        }
        simbolo[i - 1] = "$";
        String salida = "";
        for (i = 0; i < n; i++) {
            salida += lista[i] + simbolo[i];
        }
        return salida;
    }
}
