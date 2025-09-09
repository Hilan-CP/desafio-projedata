package com.hdev;

import com.hdev.entities.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private static final String SEPARADOR_LOG = "\n--------------------\n";
    private static List<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {
        inicializarListaDeFuncionarios();
        removerJoao();
        imprimirTodosFuncionarios();
        darAumento();
        Map<String, List<Funcionario>> funcionariosAgrupados = agruparPorFuncao();
        imprimirFuncionariosAgrupadosPorFuncao(funcionariosAgrupados);
        imprimirFuncionariosNascidosNosMeses10Ou12();
        imprimirFuncionarioMaisVelho();
        ordenarAlfabeticamenteEImprimir();
        imprimirSomaSalarios();
        imprimirQuantidadeSalariosMinimos();
    }

    private static void inicializarListaDeFuncionarios() {
        System.out.println("Inicializando a lista de funcionários");
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), BigDecimal.valueOf(2009.44), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), BigDecimal.valueOf(2284.38), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), BigDecimal.valueOf(9836.14), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), BigDecimal.valueOf(19119.88), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), BigDecimal.valueOf(2234.68), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), BigDecimal.valueOf(1582.72), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), BigDecimal.valueOf(4071.84), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), BigDecimal.valueOf(3017.45), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), BigDecimal.valueOf(1606.85), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), BigDecimal.valueOf(2009.44), "Gerente"));
        System.out.println(SEPARADOR_LOG);
    }

    private static void removerJoao() {
        System.out.println("Removendo João");
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));
        System.out.println(SEPARADOR_LOG);
    }

    private static void imprimirTodosFuncionarios() {
        System.out.println("Exibindo todos os funcionários");
        funcionarios.forEach(funcionario -> System.out.println(formatarDadosFuncionario(funcionario)));
        System.out.println(SEPARADOR_LOG);
    }

    private static String formatarDadosFuncionario(Funcionario funcionario) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.of("pt", "BR"));
        StringBuilder dadosFuncionario = new StringBuilder();
        dadosFuncionario.append("Nome: " + funcionario.getNome() + " - ");
        dadosFuncionario.append("Data de nascimento: " + dateFormatter.format(funcionario.getDataNascimento()) + " - ");
        dadosFuncionario.append("Salário: " + numberFormat.format(funcionario.getSalario()) + " - ");
        dadosFuncionario.append("Função: " + funcionario.getFuncao());
        return dadosFuncionario.toString();
    }

    private static void darAumento() {
        System.out.println("Aplicando aumento de 10% no salário");
        double percentualAumento = 0.1;
        BigDecimal multiplicadorAumento = BigDecimal.valueOf(1 + percentualAumento);
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(multiplicadorAumento).setScale(2, RoundingMode.HALF_UP);
            funcionario.setSalario(novoSalario);
        });
        System.out.println(SEPARADOR_LOG);
    }

    private static Map<String, List<Funcionario>> agruparPorFuncao() {
        System.out.println("Agrupando funcionários por função");
        Map<String, List<Funcionario>> gruposFuncionarios = new HashMap<>();
        funcionarios.forEach(funcionario -> {
            if(gruposFuncionarios.containsKey(funcionario.getFuncao())){
                gruposFuncionarios.get(funcionario.getFuncao()).add(funcionario);
            }
            else{
                gruposFuncionarios.put(funcionario.getFuncao(), new ArrayList<>(List.of(funcionario)));
            }
        });
        System.out.println(SEPARADOR_LOG);
        return gruposFuncionarios;
    }

    private static void imprimirFuncionariosAgrupadosPorFuncao(Map<String, List<Funcionario>> funcionariosAgrupados){
        System.out.println("Exibindo funcionários agrupados por função\n");
        funcionariosAgrupados.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Exibindo funcionários da função " + funcao);
            listaFuncionarios.forEach(funcionario -> System.out.println(formatarDadosFuncionario(funcionario)));
            System.out.println();
        });
        System.out.println(SEPARADOR_LOG);
    }

    private static void imprimirFuncionariosNascidosNosMeses10Ou12(){
        System.out.println("Funcionários nascidos nos meses 10 ou 12");
        funcionarios.forEach(funcionario -> {
            int mes = funcionario.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12){
                System.out.println(formatarDadosFuncionario(funcionario));
            }
        });
        System.out.println(SEPARADOR_LOG);
    }

    private static void imprimirFuncionarioMaisVelho(){
        Funcionario maisVelho = funcionarios.get(0);
        for (Funcionario funcionario : funcionarios){
            if(maisVelho.getDataNascimento().isAfter(funcionario.getDataNascimento())){
                maisVelho = funcionario;
            }
        }
        long idade = Duration.between(maisVelho.getDataNascimento().atStartOfDay(), LocalDateTime.now()).toDays() / 365;
        System.out.println("Funcionário mais velho");
        System.out.println("Nome: " + maisVelho.getNome() + " - Idade: " + idade);
        System.out.println(SEPARADOR_LOG);
    }

    private static void ordenarAlfabeticamenteEImprimir(){
        System.out.println("Ordenando funcionários\n");
        funcionarios.sort((funcionario1, funcionario2) -> funcionario1.getNome().compareTo(funcionario2.getNome()));
        imprimirTodosFuncionarios();
    }

    private static void imprimirSomaSalarios(){
        BigDecimal soma = funcionarios.stream().map(funcionario -> funcionario.getSalario()).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Soma de todos os salários atuais: " + soma);
        System.out.println(SEPARADOR_LOG);
    }

    private static void imprimirQuantidadeSalariosMinimos(){
        System.out.println("Exibindo quantidade de salários mínimos por funcionário");
        BigDecimal salarioMinimo = BigDecimal.valueOf(1212.00);
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidade = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Nome: " + funcionario.getNome() + " - Quantidade de salários: " + quantidade);
        });
        System.out.println(SEPARADOR_LOG);
    }
}