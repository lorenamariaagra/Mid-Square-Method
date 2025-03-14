package msm;
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable();
        String arquivo = "random_numbers_0...10000.txt"; // Caminho do arquivo com números
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                for (String token : tokens) {
                    try {
                        int chave = Integer.parseInt(token);
                        hashTable.add(chave);
                    } catch (NumberFormatException e) {
                        System.out.println("Token inválido: " + token);
                    }
                }
            }
            System.out.println("\nNúmero total de colisões: " + hashTable.getNumeroDeColisoes());
            reader.close();
        } catch (IOException ioe) {
            System.out.println("Erro ao ler o arquivo: " + ioe.getMessage());
        }
    }
}
class HashTable {
    private ArrayList<Integer>[] table;
    private int tamanho;
    private int numeroDeColisoes;
    private int count;
    private double fatorDeCarga;
    private static final double FATOR_DE_CARGA_DEFAULT = 0.75;
    public HashTable() {
        this.tamanho = 997; // Número primo para minimizar colisões
        this.table = new ArrayList[tamanho];
        this.numeroDeColisoes = 0;
        this.count = 0;
        this.fatorDeCarga = FATOR_DE_CARGA_DEFAULT;
        for (int i = 0; i < this.tamanho; i++) {
            this.table[i] = new ArrayList<>();
        }
    }
    private int simpleHash(int chave) {
        return Math.abs((chave * 31) % this.tamanho); // Multiplicação por número primo melhora dispersão
    }
    public void add(int chave) {
        if ((double) this.count / this.tamanho > this.fatorDeCarga) {
            resize();
        }
        int hash = simpleHash(chave);
        if (!this.table[hash].contains(chave)) { // Evita inserir chave duplicada
            boolean colisao = !this.table[hash].isEmpty();
            this.table[hash].add(chave);
            if (colisao) {
                numeroDeColisoes++;
                System.out.println("Chave " + chave + " adicionada ao hash " + hash + " - Colisão!");
            } else {
                System.out.println("Chave " + chave + " adicionada ao hash " + hash);
            }
            this.count++;
        }
    }
    private void resize() {
        int novoTamanho = findNextPrime(this.tamanho * 2);
        ArrayList<Integer>[] novaTabela = new ArrayList[novoTamanho];
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new ArrayList<>();
        }
        for (ArrayList<Integer> bucket : this.table) {
            if (bucket != null) {
                for (int chave : bucket) {
                    int novoHash = Math.abs((chave * 31) % novoTamanho);
                    novaTabela[novoHash].add(chave);
                }
            }
        }
        this.table = novaTabela;
        this.tamanho = novoTamanho;
    }
    private int findNextPrime(int num) {
        while (!isPrime(num)) {
            num++;
        }
        return num;
    }
    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
    public int getNumeroDeColisoes() {
        return this.numeroDeColisoes;
    }
}