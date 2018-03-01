package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class Setor {

    private String nome;
    private List<Enquete> enquetes;
    
	public Setor(String nome, List<Enquete> enquetes) {
		this.nome = nome;
		this.enquetes = enquetes;
	}

    public Setor() {
    	
    }

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Enquete> getEnquetes() {
		return enquetes;
	}

	public void setEnquetes(List<Enquete> enquetes) {
		this.enquetes = enquetes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enquetes == null) ? 0 : enquetes.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Setor other = (Setor) obj;
		if (enquetes == null) {
			if (other.enquetes != null)
				return false;
		} else if (!enquetes.equals(other.enquetes))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Setor [nome=" + nome + ", enquetes=" + enquetes + "]";
	}
	
}
