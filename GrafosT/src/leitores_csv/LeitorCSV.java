package leitores_csv;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;

public class LeitorCSV {

	private final String VIRGULA = ";";
	
	public static void main(String[] args) {
		
		LeitorCSV d = new LeitorCSV();
		List<List<String>> dadosFinais = new ArrayList<List<String>>();
		
		dadosFinais = d.leArquivoCSV("data/ALL_Games.csv");
	}
	
	public List<List<String>> leArquivoCSV(String arquivo) {
		String line = null;
		List<List<String>> csvData = new ArrayList<List<String>>();
		List<List<String>> csvFinal = new ArrayList<List<String>>();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo)));

			while ((line = reader.readLine()) != null) {
				String[] splitted = line.split(VIRGULA);
				List<String> dataLine = new ArrayList<String>(splitted.length);
				for (String data : splitted) {
					dataLine.add(data.trim());
					csvData.add(dataLine);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<String> result = new ArrayList<>();
		List<String> aux = new ArrayList<>();
		
		for (int i = 0; i < csvData.size(); i++) { /// csvData.length = 223.
			aux = csvData.get(i); // Pega uma única linha...
			
			String[] splitter = aux.get(0).split(","); // Uma linha com partições...
			result = Arrays.asList(splitter);
			csvFinal.add(result);
		}
		return csvFinal;
	}

}
