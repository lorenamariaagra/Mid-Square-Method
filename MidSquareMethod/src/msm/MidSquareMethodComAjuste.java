package msm;

public class MidSquareMethodComAjuste {
	   // Função para contar o número de dígitos de um número
	   public static int contarDigitos(long chave) {
	       int contagem = 0;
	       while (chave != 0) {
	           chave /= 10;
	           contagem++;
	       }
	       return contagem;
	   }
	   // Função para pegar o número central do quadrado de um número
	   public static int obterNumeroCentral(long numero) {
	       int comprimento = contarDigitos(numero);
	       int posicaoMeio = comprimento / 2; // Posição central do número
	       // Definir a quantidade de dígitos centrais a serem extraídos
	       int numDigitosCentrais = (comprimento % 2 == 0) ? 2 : 1; // 2 dígitos se par, 1 se ímpar
	       int inicio = posicaoMeio - (numDigitosCentrais / 2); // Posição inicial
	       // Calculando o número central
	       int resultado = 0;
	       for (int i = 0; i < numDigitosCentrais; i++) {
	           // Pegando o dígito da posição calculada
	           resultado = resultado * 10 + (int) (numero / Math.pow(10, (comprimento - inicio - 1))) % 10;
	           inicio++;
	       }
	       return resultado; // Retorna o número central completo
	   }
	   // Função de hashing usando o método do quadrado do meio
	   public static int hashingQuadradoDoMeio(int chave, int tamanhoTabela) {
	       long quadradoChave = (long) chave * chave; // Quadrado da chave
	       // Pegando o número central do quadrado da chave
	       int valorHash = obterNumeroCentral(quadradoChave);
	       // Garantir que o índice de hash esteja dentro do tamanho da tabela
	       valorHash = valorHash % tamanhoTabela; // Ajusta para caber na tabela
	       return valorHash; // Retorna o valorHash que já está dentro do tamanho da tabela
	   }
	   public static void main(String[] args) {
	       int chave = 5;
	       int tamanhoTabela = 100;
	       int indiceHash = hashingQuadradoDoMeio(chave, tamanhoTabela);
	       System.out.println("Índice de hash gerado: " + indiceHash); // Agora o índice é o número central
	   }
	}

