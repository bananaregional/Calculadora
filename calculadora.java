import java.util.Scanner;

public class calculadora {

    static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {

        int opcao = 0;

        do {
            menu();
            opcao = reader.nextInt();
            verificarOpcao(opcao);
            executarOpcao(opcao);

        } while(opcao!=0);
        reader.close();

    }

    public static void menu(){
      System.out.println("Insira uma das seguintes opcoes:");
      System.out.println("1 - Adicao");
      System.out.println("2 - Subtracao");
      System.out.println("3 - Multiplicacao");
      System.out.println("4 - Divisao");
      System.out.println("0 - Sair");
    }

    public static void verificarOpcao (int opcao) {
      if(opcao < 0 || opcao > 4){
        System.out.println("Opcao invalida!");
      }
    }

    public static void executarOpcao(int opcao) {
      switch(opcao) {
          case 1: 
            System.out.println("Resultado: "+(inserirValor1()+inserirValor2()));
            break;

          case 2:
            System.out.println("Resultado: "+(inserirValor1()-inserirValor2()));
            break;
          case 3:
            System.out.println("Resultado: "+(inserirValor1()*inserirValor2()));
            break;
          case 4:
            System.out.println("Resultado: "+(inserirValor1()/inserirValor2()));
            break;

          default:
          
      }

  }
    public static double inserirValor1(){
        System.out.println("Introduza o primeiro numero");
        double num1 = reader.nextDouble();
        return num1;
    }

    public static double inserirValor2(){
        System.out.println("Introduza o segundo numero");
        double num2 = reader.nextDouble();
        return num2;
  }
    

}
