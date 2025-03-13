package msm;
import java.io.*;
import java.util.*;


public class Main {
	public static void main(String[] args) {
    	HashTable hashTable = new HashTable(100); 
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
	private List<Integer>[] table; // Tabela de hash
    private int tamanho; // Tamanho da tabela
    private int count; // Número de elementos na tabela
    private int numeroDeColisoes; // Contador de colisões

    public HashTable(int tamanhoInicial) {
        this.tamanho = tamanhoInicial;
        this.table = new ArrayList[tamanho];
        this.count = 0;
        this.numeroDeColisoes = 0;

        // Inicializa as listas vazias
        for (int i = 0; i < tamanho; i++) {
            table[i] = new ArrayList<>();
        }
    }
    public boolean isIndexEmpty(int hash) {
        return table[hash].isEmpty();  // Retorna verdadeiro se a lista estiver vazia, caso contrário, falso
    }
    // Função para adicionar uma chave na tabela hash
    public void add(int chave) {
        // Usando a classe Mid para o cálculo do índice de hash
        int hash = MidSquareMethodComAjuste.hashingMidSquare(chave, tamanho); // Corrigido para usar hashingMidSquare
            if (isIndexEmpty(hash)) {
                System.out.println("Chave " + chave + " será adicionada no hash " + hash);
            } else {
            	numeroDeColisoes++;  // Se houver algo na lista, incrementa o contador de colisões
                System.out.println("Chave " + chave + " será adicionada no hash. " + hash + " Colisão detectada!");
            }

            // Adiciona a chave na lista
            table[hash].add(chave);
            count++;  // Incrementa o contador de elementos

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
            novaTabela[i] = new ArrayList<>();  // Inicializa a lista no índice i
        }

        // Reinsere os elementos na nova tabela
        for (int i = 0; i < tamanho; i++) {
            for (int key : table[i]) {
                // Calculando o índice com o novo tamanho da tabela
                int hashNovo = MidSquareMethodComAjuste.hashingMidSquare(key, novoTamanho);  // Agora o hash é calculado com o novo tamanho

                // Garantir que o índice esteja dentro dos limites da nova tabela
                hashNovo = hashNovo % novoTamanho;  // Ajusta para garantir que o índice seja válido

                // O índice é calculado corretamente e fica dentro dos limites do novo tamanho
                novaTabela[hashNovo].add(key);
            }
        }

        // Substitui a tabela antiga pela nova
        this.table = novaTabela;
        this.tamanho = novoTamanho;
    }
    // Função para obter o número total de colisões
    public int getNumeroDeColisoes() {
        return numeroDeColisoes;
    }
}


