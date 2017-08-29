package io.github.recursivejr.discenteVivo.models;

import java.util.List;

public class Aluno extends Usuario {

    private String matricula;
    private List<Curso> cursos;
    
	public Aluno(String nome, String email, String login, String senha, Endereco endereco, String matricula,
			List<Curso> cursos) {
		super(nome, email, login, senha, endereco);
		this.matricula = matricula;
		this.cursos = cursos;
	}

    public Aluno() {
    	super();
    }

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cursos == null) ? 0 : cursos.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (cursos == null) {
			if (other.cursos != null)
				return false;
		} else if (!cursos.equals(other.cursos))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Aluno [matricula=" + matricula + ", cursos=" + cursos + "]";
	}
    
}
