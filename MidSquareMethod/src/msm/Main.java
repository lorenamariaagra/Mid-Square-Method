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

                // Loop para adicionar os números na tabela de hash
                for (String token : tokens) {
                    try {
                        int chave = Integer.parseInt(token); // Converte o token para número inteiro
                        hashTable.put(chave); // Adiciona à tabela de hash
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
    private ArrayList<Integer>[] table;
    private int CAPACIDADE_DEFAULT = 1000;
    private int count; // Número de elementos na tabela
    private int numeroDeColisoes; // Contador de colisões

    public HashTable() {
        this.table = new ArrayList[CAPACIDADE_DEFAULT];
        this.count = 0;
        this.numeroDeColisoes = 0;
    }
    public HashTable(int capacidade) {
        this.table = new ArrayList[capacidade];
    }
    
    private int hash(Integer key) {
    	return MidSquareMethodComAjuste.hashingMidSquare(key, CAPACIDADE_DEFAULT);
    }
    // Função para adicionar uma chave na tabela hash
    public void put(int key) {
    	int hash = hash(key);
        ArrayList<Integer> keys = this.table[hash];
        
        if (keys == null) {
        	System.out.println("Chave " + key + " será adicionada no hash " + hash);
        	keys = new ArrayList<Integer>();
        	keys.add(key);
        	count++;
        } else {
        	keys.add(key);
        	numeroDeColisoes++;  // Se houver algo na lista, incrementa o contador de colisões
        	System.out.println("Chave " + key + " será adicionada no hash " + hash + ". Colisão detectada!");
            }

            // Se a tabela estiver mais de 85% cheia, realiza o redimensionamento
            if (count > CAPACIDADE_DEFAULT * 0.85) {
                resize();
            }
        }
    

    // Função para redimensionar a tabela hash
    private void resize() {
        System.out.println("Redimensionando a tabela...");

        // Dobra o tamanho da tabela
        int novoTamanho = CAPACIDADE_DEFAULT * 2;
        
        ArrayList<Integer>[] novaTabela = new ArrayList[novoTamanho];

        // Reinsere os elementos na nova tabela
        for (int i = 0; i < CAPACIDADE_DEFAULT; i++) {
        	if(!(table[i] == null))
        		for (int key : table[i]) {
            	
                // Calculando o índice com o novo tamanho da tabela
        			int hashNovo = MidSquareMethodComAjuste.hashingMidSquare(key, CAPACIDADE_DEFAULT);  
        			novaTabela[hashNovo].add(key);
            }
        }

        // Substitui a tabela antiga pela nova
        this.table = novaTabela;
        this.CAPACIDADE_DEFAULT = novoTamanho;
    }
    // Função para obter o número total de colisões
    public int getNumeroDeColisoes() {
        return numeroDeColisoes;
    }
}


