package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class MainStream {

	
	ArrayList<ArrayList<String>> dadosGerais = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> dadosBans = new ArrayList<ArrayList<String>>();
	ArrayList<ArrayList<String>> dadosPicks = new ArrayList<ArrayList<String>>();

	public static void main(String[] args) {

		MainStream obj = new MainStream();
		obj.run();

		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafoBan = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafoPick = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		obj.adicionandoVertice(grafoBan, obj.dadosBans);
		obj.adicionandoAresta(grafoBan, obj.dadosBans);

		obj.adicionandoVertice(grafoPick, obj.dadosPicks);
		obj.adicionandoAresta(grafoPick, obj.dadosPicks);

		System.out.println(grafoBan);
		System.out.println("---------------------------------------------------------------------");
		System.out.println(grafoPick);

		for (DefaultWeightedEdge e : grafoBan.edgeSet()) {
			System.out.println(grafoBan.getEdgeSource(e) + " | " + grafoBan.getEdgeWeight(e) + " | " + " --> "
					+ grafoBan.getEdgeTarget(e));
		}
	}

	public void adicionandoVertice(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafo,
			ArrayList<ArrayList<String>> dados) {
		
		// começa a partir do indice 2 pelo motivo que as duas primeiras linhas são os
		// nomes das colunas
		
		for (int i = 2; i < dados.size(); i++) {
			for (int j = 0; j < dados.get(i).size(); j++) {
				String vertice = dados.get(i).get(j);

				if (!grafo.containsVertex(vertice)) {
					grafo.addVertex(dados.get(i).get(j));
				}
			}
		}
	}

	public void adicionandoAresta(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafo,
			ArrayList<ArrayList<String>> dados) {
		
		// começa a partir do indice 2 pelo motivo que as duas primeiras linhas são os
		// nomes das colunas
		
		for (int i = 2; i < dados.size(); i++) {
			for (int j = 0; j < dados.get(i).size() - 1; j++) {
				String vertice = dados.get(i).get(j);
				String verticePosterior = dados.get(i).get(j + 1);

				if (grafo.containsEdge(vertice, verticePosterior)) {
					grafo.setEdgeWeight(grafo.getEdge(vertice, verticePosterior),
							grafo.getEdgeWeight(grafo.getEdge(vertice, verticePosterior)) + 1);
				} else {
					grafo.addEdge(vertice, verticePosterior);
				}
			}
		}

	}

	// Pegando informações do CSV e Arquivando
	
	public void run() {
		
		String arquivoCSV = "data/ALL_Games.csv";
		BufferedReader br = null;
		String linha = "";
		String csvDivisor = ",";
		
		try {

			br = new BufferedReader(new FileReader(arquivoCSV));
			while ((linha = br.readLine()) != null) {

				String[] dados = linha.split(csvDivisor);
				ArrayList<String> picksBlue = new ArrayList<>();
				ArrayList<String> picksRed = new ArrayList<>();
				ArrayList<String> BansBlue = new ArrayList<>();
				ArrayList<String> BansRed = new ArrayList<>();

				picksBlue.add(0, dados[10].trim());
				picksBlue.add(1, dados[13].trim());
				picksBlue.add(2, dados[14].trim());
				picksBlue.add(3, dados[21].trim());
				picksBlue.add(4, dados[23].trim());
				picksRed.add(0, dados[11].trim());
				picksRed.add(1, dados[12].trim());
				picksRed.add(2, dados[15].trim());
				picksRed.add(3, dados[20].trim());
				picksRed.add(4, dados[23].trim());

				BansBlue.add(0, dados[4].trim());
				BansBlue.add(1, dados[6].trim());
				BansBlue.add(2, dados[8].trim());
				BansBlue.add(3, dados[17].trim());
				BansBlue.add(4, dados[19].trim());
				BansRed.add(0, dados[5].trim());
				BansRed.add(1, dados[7].trim());
				BansRed.add(2, dados[9].trim());
				BansRed.add(3, dados[16].trim());
				BansRed.add(4, dados[18].trim());

				dadosGerais.add(BansBlue);
				dadosBans.add(BansBlue);
				dadosGerais.add(BansRed);
				dadosBans.add(BansRed);
				dadosGerais.add(picksBlue);
				dadosPicks.add(picksBlue);
				dadosGerais.add(picksRed);
				dadosPicks.add(picksRed);

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