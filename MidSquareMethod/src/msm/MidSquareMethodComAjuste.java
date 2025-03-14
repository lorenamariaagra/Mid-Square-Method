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
    public static int hashingMidSquare(int chave, int tamanho) {
        // Método da média quadrada para gerar o hash
        int hash = chave * chave; // Calculando o quadrado da chave
        // Garantir que o valor do hash esteja dentro do intervalo da tabela
        int hashAjustado = Math.abs(hash) % tamanho; // Ajustando para garantir que está dentro dos limites da tabela
        // Verificando se o hash calculado é válido
        System.out.println("Chave: " + chave + " -> Hash calculado: " + hash + " -> Hash ajustado: " + hashAjustado);
        return hashAjustado;
    }
}