package br.com.prometheusServ;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


public class ConsumirJson {
	
	public static String acessar(String endereco){
		try {
			URL url = new URL(endereco);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			
			String conteudo = new String(getBytes(is));
			return conteudo;
		} catch (Exception e) {
			Log.e("Prometheus", "Falha ao acessar Web service", e);
			return null;
		}
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
			Log.e("NGVL", "Falha ao acessar Web service", e);
		}
		return null;
	}
	
	public static byte[] getBytes(InputStream is) {
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
}
