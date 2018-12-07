package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class MainStream {

	private static ArrayList<ArrayList<String>> dadosGerais = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> dadosBans = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<String>> dadosPicks = new ArrayList<ArrayList<String>>();

	private static final int INDICE_BANS_BLUE = 0;
	private static final int INDICE_BANS_RED = 1;
	private static final int INDICE_PICKS_BLUE = 2;
	private static final int INDICE_PICKS_RED = 3;

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

		System.out.println("* GRAFO DE BANS:");
		System.out.println();
		System.out.println(grafoBan);

		System.out.println("---------------------------------------------------------------------");
		System.out.println();
		System.out.println("* GRAFO DE PICKS:");
		System.out.println();
		System.out.println(grafoPick);
		System.out.println();

		System.out.println("********** ARESTAS COM PESOS DO GRAFO DE BANS **********");
		System.out.println();
		imprimeArestasGrafo(grafoBan);
		System.out.println();

		System.out.println("Melhor Composicao:");
		System.out.println(imprimeMelhorComposicao(grafoBan));
		System.out.println("Valor da soma das arestas da melhor composicao:");
		System.out.println(imprimeMaiorSomaArestas(grafoBan));
		
		imprimePrioridade(getPreferenciaDraft(grafoBan));
		
		imprimeAfinidade(grafoPick);

		/*
		System.out.println("********** ARESTAS COM PESOS DO GRAFO DE PICKS **********");
		System.out.println();
		imprimeArestasGrafo(grafoPick);
		System.out.println();
		
		System.out.println("Melhor Composicao:");
		System.out.println(imprimeMelhorComposicao(grafoPick));
		System.out.println("Valor da soma das arestas da melhor composicao:");
		System.out.println(imprimeMaiorSomaArestas(grafoPick));
		*/

	}

	private static void imprimeAfinidade(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafoPick) {
		DefaultWeightedEdge maiorAresta = null;
		for (DefaultWeightedEdge aresta : grafoPick.edgeSet()) {
			if(maiorAresta == null) {
				maiorAresta = aresta;
			} else if (grafoPick.getEdgeWeight(aresta) > grafoPick.getEdgeWeight(maiorAresta)) {
				maiorAresta = aresta;
			}
		}
		System.out.println("\nCampeoes que aparecem juntos com mais frequencia:");
		System.out.printf("[%s %.0f-> %s]",grafoPick.getEdgeSource(maiorAresta),
				grafoPick.getEdgeWeight(maiorAresta),grafoPick.getEdgeTarget(maiorAresta));
	}

	private static void imprimePrioridade(ArrayList preferenciaDraft) {
		System.out.println("\nCampeoes e seus prioridades:");
		HashMap<String, Integer> prioridades = new HashMap<>();
		for (int i = 1; i < preferenciaDraft.size(); i += 2) {
			prioridades.put((String) preferenciaDraft.get(i), (Integer) preferenciaDraft.get(i-1));
		}
		
		System.out.println(prioridades.toString().replaceAll(",", "\n"));
	}

	public static void imprimeArestasGrafo(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafo) {
		float aux = 1;
		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			System.out.println(
					grafo.getEdgeSource(e) + " | " + grafo.getEdgeWeight(e) + " | " + " --> " + grafo.getEdgeTarget(e));
			if (aux % 4 == 0)
				System.out.println("##########################");
			aux++;
		}
	}

	public static ArrayList<String> imprimeMelhorComposicao(
			SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafo) {
		int indice = 1;
		float maiorSoma = 0;

		ArrayList<String> melhorComposicao = null;
		ArrayList<String> aux = new ArrayList<>();

		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			float contador = 0;
			contador += grafo.getEdgeWeight(e);

			// Vou adicionando os elementos num array auxiliar...
			aux.add(grafo.getEdgeSource(e));

			if (indice % 4 == 0 && contador > maiorSoma) {
				aux.add(grafo.getEdgeTarget(e)); // Adiciona o quinto heroi...
				maiorSoma = contador;
				contador = 0;

				// Se eu entrei aqui, os cinco ultimos adicionados sao os melhores...
				melhorComposicao = new ArrayList<>();

				for (int i = aux.size() - 1; i >= aux.size() - 5; i--) {
					melhorComposicao.add(aux.get(i));
				}

			}
			indice++;
		}
		return melhorComposicao;
	}

	public static float imprimeMaiorSomaArestas(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafo) {
		int indice = 1;
		float maiorSoma = 0;

		ArrayList<String> melhorComposicao = null;
		ArrayList<String> aux = new ArrayList<>();

		for (DefaultWeightedEdge e : grafo.edgeSet()) {
			float contador = 0;
			contador += grafo.getEdgeWeight(e);

			// Vou adicionando os elementos num array auxiliar...
			aux.add(grafo.getEdgeSource(e));

			if (indice % 4 == 0 && contador > maiorSoma) {
				aux.add(grafo.getEdgeTarget(e)); // Adiciona o quinto heroi...
				maiorSoma = contador;
				contador = 0;

				// Se eu entrei aqui, os cinco ultimos adicionados sao os melhores...
				melhorComposicao = new ArrayList<>();

				for (int i = aux.size() - 1; i >= aux.size() - 5; i--) {
					melhorComposicao.add(aux.get(i));
				}

			}
			indice++;
		}
		return maiorSoma;
	}

	public void adicionandoVertice(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafo,
			ArrayList<ArrayList<String>> dados) {

		// come�a a partir do indice 2 pelo motivo que as duas primeiras linhas s�o os
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

		// come�a a partir do indice 2 pelo motivo que as duas primeiras linhas s�o os
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

	// Pegando informa��es do CSV e Arquivando

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
	public static ArrayList getPreferenciaDraft(ArrayList<ArrayList<String>> dadosPicks, SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafoPick) {
		ArrayList preferencia = new ArrayList();
		int maiorGrau = 0;
		String campeao = "";
		
		for (int i = 2; i < dadosPicks.size(); i++) {
	    	for (int j = 0; j < dadosPicks.get(i).size(); j++) {
				if (grafoPick.degreeOf(dadosPicks.get(i).get(j)) > maiorGrau) {
					campeao = dadosPicks.get(i).get(j) ;
					maiorGrau = grafoPick.degreeOf(dadosPicks.get(i).get(j));
				}
			}
	    	preferencia.add(0, maiorGrau);
			preferencia.add(1, campeao);
		}
		return preferencia;
	}
	
	// Pega os personagens do grafo
	public static ArrayList getPreferenciaDraft(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> grafoPick) {
		int maiorGrau = 0;
		String campeao = "";
		ArrayList preferencia = new ArrayList();
		
		for (DefaultWeightedEdge e : grafoPick.edgeSet()) {
			if (grafoPick.degreeOf(grafoPick.getEdgeSource(e)) > grafoPick.degreeOf(grafoPick.getEdgeTarget(e))) {
				maiorGrau = grafoPick.degreeOf(grafoPick.getEdgeSource(e));
				campeao = grafoPick.getEdgeSource(e);
			} else {
				maiorGrau = grafoPick.degreeOf(grafoPick.getEdgeTarget(e));
				campeao = grafoPick.getEdgeTarget(e);
			}
			preferencia.add(0, maiorGrau);
			preferencia.add(1, campeao);
		}
		return preferencia;
	}
}