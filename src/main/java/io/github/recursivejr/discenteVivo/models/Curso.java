package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class Curso {

    private String nome;
    private List<Aluno> alunos;
    private List<Enquete> enquetes;

    public Curso(String nome, List<Aluno> alunos, List<Enquete> enquetes) {
		super();
		this.nome = nome;
		this.alunos = alunos;
		this.enquetes = enquetes;
	}

	public Curso() {

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
    
    public void setEnquete(Enquete enquete) {
        enquetes.add(enquete);
    }

    public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAluno(Aluno aluno) {
		alunos.add(aluno);
	}
	
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alunos == null) ? 0 : alunos.hashCode());
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
		Curso other = (Curso) obj;
		if (alunos == null) {
			if (other.alunos != null)
				return false;
		} else if (!alunos.equals(other.alunos))
			return false;
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
		return "Curso [nome=" + nome + ", alunos=" + alunos + ", enquetes=" + enquetes + "]";
	}
}
