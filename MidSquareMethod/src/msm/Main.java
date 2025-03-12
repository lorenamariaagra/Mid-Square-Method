package msm;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
    	HashTable hashTable = new HashTable(300); 
        String arquivo = "random_numbers.txt"; // ou o caminho completo, como "/caminho/do/arquivo/random_numbers.txt"
        
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
    private List<Integer>[] table; // Tabela de hash
    private int tamanho; // Tamanho da tabela
    private int count; // Número de elementos na tabela
    private int numeroDeColisoes; // Contador de colisões

    // Construtor da tabela hash
    public HashTable(int tamanhoInicial) {
        this.tamanho = tamanhoInicial;
        this.table = new ArrayList[tamanho];
        this.count = 0;
        this.numeroDeColisoes = 0; // Inicializa o contador de colisões

        // Inicializa as listas vazias
        for (int i = 0; i < tamanho; i++) {
            table[i] = new ArrayList<>();
        }
    }

    // Função para adicionar uma chave na tabela hash
    public void add(int chave) {
        int indice = MidSquareMethodComAjuste.hashingQuadradoDoMeio(chave, tamanho);

        // Verifica se o índice já contém uma lista e se a chave já não está lá
        if (!table[indice].contains(chave)) {
            if (!table[indice].isEmpty()) { // Se já houver uma chave no índice, houve uma colisão
                numeroDeColisoes++; // Incrementa o contador de colisões
            }
            table[indice].add(chave);
            count++;
            System.out.println("Chave " + chave + " adicionada no índice " + indice);
        } else {
            System.out.println("Chave " + chave + " já existe no índice " + indice);
        }

        // Se a tabela estiver mais de 75% cheia, realiza o redimensionamento
        if (count > tamanho * 0.75) {
            resize();
        }
    }

    // Função para redimensionar a tabela hash
    private void resize() {
        System.out.println("Redimensionando a tabela...");

        // Dobra o tamanho da tabela
        int novoTamanho = tamanho * 2;
        List<Integer>[] novaTabela = new ArrayList[novoTamanho];

        // Inicializa as novas listas
        for (int i = 0; i < novoTamanho; i++) {
            novaTabela[i] = new ArrayList<>();
        }

        // Reinsere os elementos na nova tabela
        for (int i = 0; i < tamanho; i++) {
            for (int chave : table[i]) {
                int indiceNovo = MidSquareMethodComAjuste.hashingQuadradoDoMeio(chave, novoTamanho);
                novaTabela[indiceNovo].add(chave);
            }
        }

        // Substitui a tabela antiga pela nova
        this.table = novaTabela;
        this.tamanho = novoTamanho;

        System.out.println("Tabela redimensionada para tamanho " + novoTamanho);
    }

    // Função para obter o número total de colisões
    public int getNumeroDeColisoes() {
        return numeroDeColisoes;
    }
}
