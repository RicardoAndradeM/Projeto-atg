

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LeiaCVS {

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

            String[] jogo = linha.split(csvDivisor);

            System.out.println("Ban 1 Blue:" + jogo[4] + " | " + "Ban 1 Red:" + jogo[5] 
            		+ " | " + "Ban 2 Blue:" + jogo[6] + " | " + "Ban 2 Red:" + jogo[7] 
            		+ " | " + "Ban 3 Blue:" + jogo[8] + " | " + "Ban 3 Red:" + jogo[9] 
            		+ " | " + "Ban 4 Blue: " + jogo[17] + " | " + "Ban 4 Red:  " + jogo[16]
            		+ " | " + "Ban 5 Blue: " + jogo[19] + " | " + "Ban 5 Red:  " + jogo[18]);

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

}