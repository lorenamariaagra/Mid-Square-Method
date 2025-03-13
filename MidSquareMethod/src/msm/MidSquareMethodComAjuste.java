package msm;

public class MidSquareMethodComAjuste {
	// Função para contar a quantidade de dígitos de um número
    public static int contarDigitos(long chave) {
        int contagem = 0;
        while (chave != 0) {
            chave /= 10;
            ++contagem;
        }
        return contagem;
    }

    // Função para pegar o N-ésimo dígito de um número
    public static int pegarDigitoN(long numero, int pos) {
        int digito = 0;
        int tamanho = contarDigitos(numero);
        int fração = (int) (numero / Math.pow(10, (tamanho - pos))); // Pegando a fração que corresponde ao dígito na posição
        digito = fração % 10; // Pegando o dígito da posição
        return digito;
    }

    // Função para pegar um intervalo de dígitos de um número
    public static int pegarIntervaloDeDigitos(long numero, int posInicial, int quantidade) {
        int numeroResultado = 0;
        int digito;
        int tamanho = contarDigitos(numero);

        // Verifica se a quantidade de dígitos que queremos pegar está dentro do número
        if ((posInicial + quantidade) - 1 <= tamanho) {
            for (int i = 0; i < quantidade; i++) {
                // Pegando o dígito da posição
                digito = pegarDigitoN(numero, posInicial);
                posInicial++;
                // Criando o número com os dígitos selecionados
                numeroResultado = numeroResultado * 10 + digito;
            }
        }

        return numeroResultado;
    }

    // Função de hashing utilizando o método do quadrado do meio
    public static int hashingMidSquare(int chave, int tamanhoTabela) {
        // Calculando o quadrado da chave
        long quadradoChave = (long) chave * chave;

        // Pegando a posição central do quadrado da chave
        int tamanhoQuadrado = contarDigitos(quadradoChave);
        int posicaoMeio = tamanhoQuadrado / 2;

        // Pegando os 3 dígitos centrais do quadrado
        int digitosCentrais = pegarIntervaloDeDigitos(quadradoChave, posicaoMeio, 3); // Pegando 3 dígitos centrais

        // Garantir que o valor do hash esteja dentro dos limites da tabela (tamanho da tabela)
        if(digitosCentrais > tamanhoTabela) {
        	return digitosCentrais % tamanhoTabela;
        }
    	return  digitosCentrais;
    }
	}

