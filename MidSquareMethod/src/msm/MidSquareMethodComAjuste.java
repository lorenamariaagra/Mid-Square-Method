package msm;

public class MidSquareMethodComAjuste {
	
	public static int contarDigitos(long numero) {
        return Long.toString(numero).length() ;
    }

    // Função para pegar a parte central do número
    public static int getCentralPart(long number, int numDigits) {
        // Converte o número para string para fácil manipulação
        String numStr = Long.toString(number);
        
        // Se o número de dígitos a serem extraídos for maior que o número de dígitos do número original, 
        // retornamos o número inteiro como central
        if (numStr.length() <= numDigits) {
            return (int) number;
        }

        // Encontra a posição central
        int start = (numStr.length() - numDigits) / 2;
        int end = start + numDigits;
        
        // Pega a parte central da string
        String centralPartStr = numStr.substring(start, end);
        
        // Converte a parte central para número e retorna
        return Integer.parseInt(centralPartStr);
    }

    // Função hashing usando o método Mid Square
    public static int hashingMidSquare(int chave, int tamanhoTabela) {
        int tamanho = tamanhoTabela - 1;
        int digitosTamanhoTable = contarDigitos(tamanho);
        
        // Calculando o quadrado da chave
        long quadradoChave = (long) chave * chave;

        // Pegando a parte central do quadrado da chave
        // Aqui pegamos o mesmo número de dígitos que o tamanho da tabela
        int digitosCentrais = getCentralPart(quadradoChave, digitosTamanhoTable);

        // Garantir que o valor do hash esteja dentro dos limites da tabela (tamanho da tabela)
        int hash = digitosCentrais % tamanhoTabela;
        return hash;
    }


}

