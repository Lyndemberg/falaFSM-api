package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class Enquete {

	private int id;
	private String emailAdmin;
    private String nome;
    private String descricao;
    private String foto;
    private List<String> comentarios;
    private List<String> opcoes;
    private List<Resposta> respostas;
    private List<Curso> cursos;
    private List<Setor> setores;
    
	public Enquete(int id, String emailAdmin, String nome, String descricao, String foto, List<String> comentarios,
			List<String> opcoes, List<Resposta> respostas, List<Curso> cursos, List<Setor> setores) {
		this.id = id;
		this.emailAdmin = emailAdmin;
		this.nome = nome;
		this.descricao = descricao;
		this.foto = foto;
		this.comentarios = comentarios;
		this.opcoes = opcoes;
		this.respostas = respostas;
		this.cursos = cursos;
		this.setores = setores;
	}
    
	public Enquete() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailAdmin() {
		return emailAdmin;
	}

	public void setEmailAdmin(String emailAdmin) {
		this.emailAdmin = emailAdmin;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<String> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<String> comentarios) {
		this.comentarios = comentarios;
	}

	public List<String> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<String> opcoes) {
		this.opcoes = opcoes;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comentarios == null) ? 0 : comentarios.hashCode());
		result = prime * result + ((cursos == null) ? 0 : cursos.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((emailAdmin == null) ? 0 : emailAdmin.hashCode());
		result = prime * result + ((foto == null) ? 0 : foto.hashCode());
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((opcoes == null) ? 0 : opcoes.hashCode());
		result = prime * result + ((respostas == null) ? 0 : respostas.hashCode());
		result = prime * result + ((setores == null) ? 0 : setores.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enquete other = (Enquete) obj;
		if (comentarios == null) {
			if (other.comentarios != null)
				return false;
		} else if (!comentarios.equals(other.comentarios))
			return false;
		if (cursos == null) {
			if (other.cursos != null)
				return false;
		} else if (!cursos.equals(other.cursos))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (emailAdmin == null) {
			if (other.emailAdmin != null)
				return false;
		} else if (!emailAdmin.equals(other.emailAdmin))
			return false;
		if (foto == null) {
			if (other.foto != null)
				return false;
		} else if (!foto.equals(other.foto))
			return false;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (opcoes == null) {
			if (other.opcoes != null)
				return false;
		} else if (!opcoes.equals(other.opcoes))
			return false;
		if (respostas == null) {
			if (other.respostas != null)
				return false;
		} else if (!respostas.equals(other.respostas))
			return false;
		if (setores == null) {
			if (other.setores != null)
				return false;
		} else if (!setores.equals(other.setores))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Enquete [id=" + id + ", emailAdmin=" + emailAdmin + ", nome=" + nome + ", descricao=" + descricao
				+ ", foto=" + foto + ", comentarios=" + comentarios + ", opcoes=" + opcoes + ", respostas=" + respostas
				+ ", cursos=" + cursos + ", setores=" + setores + "]";
	}
	
}
