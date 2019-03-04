
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		 if(args.length != 2){
             System.err.println("Informe dois argumentos");
             System.err.println("java Main <nome_de_arquivo_origem> <nome_de_arquivo_destino>");
             System.exit(0);
       }
		long time = 0;
        int v[] = new int[256];
        FileInputStream fis = null;
        FileOutputStream fos = null;
        
        try {
        	fis = new FileInputStream(args[0]);
            fos = new FileOutputStream(args[1]);
            byte[] buffer = new byte[512];
            int noOfBytes = 0;
            System.out.println("Copiando arquivo...");
            time = System.currentTimeMillis();
            while ((noOfBytes = fis.read(buffer)) != -1)
            {
                fos.write(buffer, 0, noOfBytes);               
                for(int q=0;q<buffer.length;q++)
                {                                	        	               
                	v[buffer[q]&0xFF] = v[buffer[q]&0xFF]+1;
                }                
            }
            time = System.currentTimeMillis() - time;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado" + e);
        } catch (IOException ioe) {
            System.out.println("Erro: " + ioe);
        } finally {            
            try {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                System.out.println("Erro: " + ioe);
            }
        }
        System.out.println("Arquivo copiado com sucesso!");
        System.out.println("Tempo de execucao:"+time+" Milisegundos");
        BufferedWriter bf = new BufferedWriter(new FileWriter(new File("Data.txt")));
        for(int i=0;i<v.length;i++) {
            bf.write("Byte "+ i+","+v[i]+"\r\n");
        }
        bf.close();

	}
}