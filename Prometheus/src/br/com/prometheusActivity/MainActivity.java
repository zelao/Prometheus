package br.com.prometheusActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import br.com.prometheus.Teatro;

public class MainActivity extends Activity {

	private ListView lista;
	private ArrayList<Teatro> teatros = new ArrayList<Teatro>();
	private Spinner spnGeneros;
	private List<String> generos = new ArrayList<String>();
	private String genero;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		Marker frameworkSystem = map.addMarker(new MarkerOptions().position(
				frameworkSystemLocation).title("Framework System"));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
				frameworkSystemLocation, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);*/

		generos.add(getString(R.string.generos));
		generos.add(getString(R.string.comedia));
		generos.add(getString(R.string.drama));
		generos.add(getString(R.string.standUp));
		generos.add(getString(R.string.musical));
		generos.add(getString(R.string.sarau));

		//Identifica o Spinner no layout
		spnGeneros = (Spinner) findViewById(R.id.genero_artistico);
		
		//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, generos);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spnGeneros.setAdapter(spinnerArrayAdapter);
		
		//Método do Spinner para capturar o item selecionado
		spnGeneros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
 
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega nome pela posição
				genero = parent.getItemAtPosition(posicao).toString();
				
				new TeatrosTask().execute();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
		
		lista = (ListView) findViewById(R.id.listaTeatros);
		
		lista.setOnItemClickListener(new OnItemClickListener() {
			
			   @Override
			   public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
				
				Teatro elemento = teatros.get(posicao);
				
				elemento.getIdTeatro();
				
				Intent i = new Intent(MainActivity.this, TeatroActivity.class);
			//	i.putExtra("teatro", elemento);				
                startActivity(i);
			} 
		}); 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inicio, menu);
		return true;
	}
	
	public String getRESTFileContent(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);

		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = new String(getBytes(instream));
				instream.close();
				return result;
			}
		} catch (Exception e) {
			Log.e("PROMETHEUS", "Falha ao acessar Web service", e);
		}
		return null;
	}
	
	public byte[] getBytes(InputStream is) {
		try {
			int bytesLidos;
			ByteArrayOutputStream bigBuffer = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];

			while ((bytesLidos = is.read(buffer)) > 0) {
				bigBuffer.write(buffer, 0, bytesLidos);
			}

			return bigBuffer.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private class TeatrosTask extends AsyncTask<String, Void, String[]> {

		ProgressDialog dialog;
		
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(MainActivity.this);
			dialog.show();
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			if(result != null){
					ArrayAdapter<String> adapter =	new ArrayAdapter<String>(getBaseContext(),	android.R.layout.simple_list_item_1, result);
					lista.setAdapter(adapter);
			}
			dialog.dismiss();
		}
		
		@Override
		protected String[] doInBackground(String... params) { 
			try {
				
				String urlServidor = "http://192.168.25.31:8080/webservice/restaurante/buscarTodos";
				String url = Uri.parse(urlServidor).toString();
				String conteudo = getRESTFileContent(url);				
				
				// pegamos o resultado
				JSONObject jsonObject = new JSONObject(conteudo);
				JSONArray resultados = jsonObject.getJSONArray("teatro");
				
				// montamos o resultado
				String[] listaTeatros;
				if(genero.compareToIgnoreCase(getString(R.string.generos)) == 0){
				
					listaTeatros = new String[resultados.length()];
					
					for (int i = 0; i < resultados.length(); i++) {
						
						String endereco = new String();
						String cidade = new String();
						Teatro teatro = new Teatro();
						
						JSONObject tweet = resultados.getJSONObject(i);
						
						teatro.setNome(tweet.getString("nome"));
						teatro.setDescricao(tweet.getString("descricao"));
						teatro.setEndereco(tweet.getString("endereco"));
						teatro.setCidade(tweet.getString("cidade"));
						teatro.setGeneroArtistico(tweet.getString("generoArtistico"));
						teatro.setHorario(tweet.getString("horario"));
						teatro.setIdTeatro(tweet.getString("idTeatro"));
						teatros.add(teatro);
						
						endereco = tweet.getString("endereco");
						cidade = tweet.getString("cidade");
						String enderecoCompleto = endereco.replace(" ","+") + "+" + cidade.replace(" ", "+");
				
						String urlMapa = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=Av.+esperanca+566+guarulhos&destinations=" + enderecoCompleto + "&mode=driving&language=pt-BR&sensor=false";
						String mapa = Uri.parse(urlMapa).toString();
						String distancia = getRESTFileContent(mapa);
						
						JSONObject jsonObjectEnd = new JSONObject(distancia);
	
						JSONArray completoRows = jsonObjectEnd.getJSONArray("rows");
						JSONObject rows = completoRows.getJSONObject(0);
						
						JSONArray completoElements = rows.getJSONArray("elements");
						JSONObject elements = completoElements.getJSONObject(0);
						
						JSONObject distance = elements.getJSONObject("distance");
						
						String  distanciaKM = distance.getString("text");
						
						
						listaTeatros[i] = getString(R.string._t+Integer.valueOf(teatro.getIdTeatro()))  + " - " 
								+ teatro.getEndereco() + " - " + teatro.getCidade() + " - " + distanciaKM;
							
					}
				}else{
					teatros.clear();
					for(int i = 0; i < resultados.length(); i++){
						Teatro teatro = new Teatro();
						JSONObject tweet = resultados.getJSONObject(i);
						if(tweet.getString("generoArtistico").compareToIgnoreCase(genero) == 0){
							teatro.setNome(tweet.getString("nome"));
							teatro.setDescricao(tweet.getString("descricao"));
							teatro.setEndereco(tweet.getString("endereco"));
							teatro.setCidade(tweet.getString("cidade"));
							teatro.setGeneroArtistico(tweet.getString("generoArtistico"));
							teatro.setHorario(tweet.getString("horario"));
							teatro.setIdTeatro(tweet.getString("idTeatro"));
							teatros.add(teatro);
							
						}
						
					}
					
					listaTeatros = new String[teatros.size()];
					
					for(int i = 0; i < teatros.size(); i++){
						
						String endereco = new String();
						String cidade = new String();
						
						//Faz a busca da distância entre o ponto atual e o restaurante
						endereco = teatros.get(i).getEndereco();
						cidade = teatros.get(i).getCidade();
						String enderecoCompleto = endereco.replace(" ","+") + "+" + cidade.replace(" ", "+");
				
						String urlMapa = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=Av.+esperanca+566+guarulhos&destinations=" + enderecoCompleto + "&mode=driving&language=pt-BR&sensor=false";
						String mapa = Uri.parse(urlMapa).toString();
						String distancia = getRESTFileContent(mapa);
						
						JSONObject jsonObjectEnd = new JSONObject(distancia);
	
						JSONArray completoRows = jsonObjectEnd.getJSONArray("rows");
						JSONObject rows = completoRows.getJSONObject(0);
						
						JSONArray completoElements = rows.getJSONArray("elements");
						JSONObject elements = completoElements.getJSONObject(0);
						
						JSONObject distance = elements.getJSONObject("distance");
						
						String  distanciaKM = distance.getString("text");
						
						listaTeatros[i] = getString(R.string._t+Integer.valueOf(teatros.get(i).getIdTeatro())) + 
								" - " + teatros.get(i).getEndereco() + " - " + teatros.get(i).getCidade() + " - " + distanciaKM;
					}
				}
				return listaTeatros;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
		}
	}
}
