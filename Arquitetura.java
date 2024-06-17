import CPU.Escalar;
import CPU.SuperEscalar;

public class Arquitetura{
    public static void main(String[] args)
    {
         Interface i = new Interface();
         i.criarInterface();
        String[] pathProcessos = { "./processo1.txt", "./processo3.txt"};
        Escalar escalar = new Escalar(pathProcessos.length, pathProcessos);
        //SuperEscalar superescalar = new SuperEscalar(pathProcessos.length, pathProcessos);
        //superescalar.rodarCodigo("IMT");
        //superescalar.rodarCodigo("IMT");
        //superescalar.printUnidades();
    }
}