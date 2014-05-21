package br.com.prometheus;

import android.app.Activity;


public class Apresentacao extends Activity {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -4119920616798476210L;
	
	private String idApresentacao;
	public String getIdApresentacao() {
		return idApresentacao;
	}
	public void setIdApresentacao(String idApresentacao) {
		this.idApresentacao = idApresentacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		this.preco = preco;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getIdTeatro() {
		return idTeatro;
	}
	public void setIdTeatro(String idTeatro) {
		this.idTeatro = idTeatro;
	}
	public String getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(String idGenero) {
		this.idGenero = idGenero;
	}
	private String descricao;
	private String preco;
	private String nome;
	private String idTeatro;
	private String idGenero;
	
}
