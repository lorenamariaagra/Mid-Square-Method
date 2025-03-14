package msm;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable = new HashTable(); 
        String arquivo = "random_numbers_0...10000.txt"; // ou o caminho completo, como "/caminho/do/arquivo/random_numbers.txt"
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line = "";
            
            // Lê linha por linha do arquivo
            while ((line = reader.readLine()) != null) {
                // Quebra a linha em tokens separados por espaço
                String[] tokens = line.split(" ");
                
                // Medindo tempo de execução
                long start = System.nanoTime();

                // Loop para adicionar os números na tabela de hash
                for (String token : tokens) {
                    try {
                        int chave = Integer.parseInt(token); // Converte o token para número inteiro
                        hashTable.add(chave); // Adiciona à tabela de hash
                    } catch (NumberFormatException e) {
                        // Ignora caso algum token não seja um número válido
                        System.out.println("Token inválido: " + token);
                    }
                }

            }
            
            // Exibe o número total de colisões após o processamento do arquivo
            System.out.println("Número total de colisões: " + hashTable.getNumeroDeColisoes());

            // Fecha o BufferedReader após ler o arquivo
            reader.close();

        } catch (IOException ioe) {
            System.out.println("Erro ao ler a entrada do arquivo: " + ioe.getMessage());
        }
    }
}

class HashTable {
    private ArrayList<Integer>[] table; // Tabela de hash
    private int tamanho; // Tamanho da tabela
    private int numeroDeColisoes; // Contador de colisões
    private int count; // Contador de elementos inseridos
    private double fatorDeCarga; // Fator de carga
    
    public static final double FATOR_DE_CARGA_DEFAULT = 0.85;

    public HashTable() {
        this.tamanho = 1000; // Tamanho inicial da tabela
        this.table = new ArrayList[tamanho];
        this.numeroDeColisoes = 0;
        this.count = 0;
        this.fatorDeCarga = FATOR_DE_CARGA_DEFAULT;
    }

    // Função para adicionar uma chave na tabela hash
    public void add(int chave) {
        if ((double) this.count / this.tamanho > this.fatorDeCarga) {
            resize(); // Se a tabela atingir o fator de carga, redimensiona
        }

        int hash = MidSquareMethodComAjuste.hashingMidSquare(chave, this.tamanho); // Correção no cálculo do hash
        hash = hash % this.tamanho; // Garantir que o valor do hash está dentro do intervalo válido

        ArrayList<Integer> bucket = this.table[hash];

        // Se o bucket estiver vazio, cria uma nova lista para o bucket
        if (bucket == null) {
            bucket = new ArrayList<>();
            this.table[hash] = bucket;
            System.out.println("Chave " + chave + " adicionando ao hash " + hash); // Imprime a chave sendo adicionada
        } else {
            // Caso contrário, adiciona a chave e verifica colisão
            System.out.print("Chave " + chave + " adicionando ao hash " + hash); // Imprime que está adicionando
            bucket.add(chave);
            
            // Verifica se houve colisão (mais de uma chave no mesmo bucket)
            if (bucket.size() > 1) {
                numeroDeColisoes++;
                System.out.println(" - Colisão!"); // Imprime mensagem de colisão
            } else {
                System.out.println(); // Não há colisão, apenas uma nova linha
            }
        }

        this.count++;
    }

    // Função para redimensionar a tabela hash
    private void resize() {
        // Dobra o tamanho da tabela
        this.count = 0; // Recomeça o contador de elementos
        int novoTamanho = this.tamanho * 2;
        ArrayList<Integer>[] novaTabela = new ArrayList[novoTamanho];

        // Reinsere os elementos na nova tabela
        for (int i = 0; i < this.tamanho; i++) {
            if (this.table[i] != null) {
                for (int chave : this.table[i]) {
                    int hashNovo = MidSquareMethodComAjuste.hashingMidSquare(chave, novoTamanho); // Usar o novo tamanho para o cálculo do hash
                    hashNovo = hashNovo % novoTamanho; // Garantir que o hash esteja dentro do novo tamanho
                    ArrayList<Integer> bucket = novaTabela[hashNovo];
                    if (bucket == null) {
                        bucket = new ArrayList<>();
                        novaTabela[hashNovo] = bucket;
                    }
                    bucket.add(chave);
                    this.count++;
                }
            }
        }

        // Substitui a tabela antiga pela nova
        this.table = novaTabela;
        this.tamanho = novoTamanho;
    }

    // Função para obter o número total de colisões
    public int getNumeroDeColisoes() {
        return this.numeroDeColisoes;
    }
}
