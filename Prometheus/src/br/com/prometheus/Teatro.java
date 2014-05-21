package br.com.prometheus;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

@SuppressWarnings("unused")
public class Teatro extends Activity {

	private static final long serialVersionUID = -2453917363154639986L;
	private String idTeatro;
	private String nome;
	private String descricao;
	private String endereco;
	private String cidade;
	private String latitude;
	private String longitude;
	private String generoArtistico;
	private String horario;
	
	
	public String getIdTeatro() {
		return idTeatro;
	}
	
	public void setIdTeatro(String idTeatro) {
		this.idTeatro = idTeatro;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getGeneroArtistico() {
		return generoArtistico;
	}
	
	public void setGeneroArtistico(String generoArtistico) {
		this.generoArtistico = generoArtistico;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
}
