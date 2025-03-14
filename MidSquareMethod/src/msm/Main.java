package msm;
import java.io.*;
import java.util.*;
public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable("saida.txt");
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
                        hashTable.writeToFile("Token inválido: " + token);
                    }
                }
            }
            hashTable.writeToFile("\nNúmero total de colisões: " + hashTable.getNumeroDeColisoes());
            reader.close();
        } catch (IOException ioe) {
            System.out.println("Erro ao ler o arquivo: " + ioe.getMessage());
        } finally {
            hashTable.closeWriter();  // Fechar o writer ao final
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
    private BufferedWriter writer;
    
    public HashTable(String arquivoSaida) {
        this.tamanho = 997; // Número primo para minimizar colisões
        this.table = new ArrayList[tamanho];
        this.numeroDeColisoes = 0;
        this.count = 0;
        this.fatorDeCarga = FATOR_DE_CARGA_DEFAULT;
        try {
            this.writer = new BufferedWriter(new FileWriter(arquivoSaida, true)); // true para append
        } catch (IOException e) {
            System.out.println("Erro ao abrir arquivo de saída: " + e.getMessage());
        }        for (int i = 0; i < this.tamanho; i++) {
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
            try {
                if (colisao) {
                    numeroDeColisoes++;
                    writeToFile("Chave " + chave + " adicionada ao hash " + (hash % this.tamanho) + " - Colisão!");
                } else {
                    writeToFile("Chave " + chave + " adicionada ao hash " + (hash % this.tamanho));
                }
            } catch (IOException e) {
                System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
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
    public void writeToFile(String message) throws IOException {
        writer.write(message);
        writer.newLine();
    }
    public void closeWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
        }
    }
}