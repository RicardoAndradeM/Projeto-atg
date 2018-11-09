package leitores_csv;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeiaCVS {

	String[] dados;
	
  public static void main(String[] args) {

    LeiaCVS obj = new LeiaCVS();
    obj.run();

  }

  public void run() {

    String arquivoCSV = "C:\\Users\\Jonas\\Documents\\ALL_Games.csv";
    BufferedReader br = null;
    String linha = "";
    String csvDivisor = ",";
    try {

        br = new BufferedReader(new FileReader(arquivoCSV));
        while ((linha = br.readLine()) != null) {

            dados = linha.split(csvDivisor);
            
            System.out.println("Time Blue:" + dados[1] + "	| " + "Time Red:" + dados[2]);
            System.out.println("Ban 1 Blue:" + dados[4] + " | " + "Ban 1 Red:" + dados[5] 
            		+ " | " + "Ban 2 Blue:" + dados[6] + " | " + "Ban 2 Red:" + dados[7] 
            		+ " | " + "Ban 3 Blue:" + dados[8] + " | " + "Ban 3 Red:" + dados[9] 
            		+ " | " + "Ban 4 Blue: " + dados[17] + " | " + "Ban 4 Red:  " + dados[16]
            		+ " | " + "Ban 5 Blue: " + dados[19] + " | " + "Ban 5 Red:  " + dados[18]);
            
            System.out.println("Pick 1 Blue:" + dados[10] + " | " + "Pick 1 Red:" + dados[11] 
            		+ " | " + "Pick 2 Blue:" + dados[12] + " | " + "Pick 2 Red:" + dados[13] 
            		+ " | " + "Pick 3 Blue:" + dados[14] + " | " + "Pick 3 Red:" + dados[15] 
            		+ " | " + "Pick 4 Blue: " + dados[21] + " | " + "Pick 4 Red:  " + dados[20]
            		+ " | " + "Pick 5 Blue: " + dados[23] + " | " + "Pick 5 Red:  " + dados[22]);
            System.out.println();
        }

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  }
  
  public String[] getDados() {
	  return this.dados;
  }
  
  
  

}