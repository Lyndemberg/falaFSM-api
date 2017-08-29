package io.github.recursivejr.discenteVivo.models;

public class Resposta {

    private int idEnquete;
    private String resposta;
    private String matAluno;
    
	public Resposta(int idEnquete, String resposta, String matAluno) {
		this.idEnquete = idEnquete;
		this.resposta = resposta;
		this.matAluno = matAluno;
	}
    
	public Resposta() {
		
	}

	public int getIdEnquete() {
		return idEnquete;
	}

	public void setIdEnquete(int idEnquete) {
		this.idEnquete = idEnquete;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getMatAluno() {
		return matAluno;
	}

	public void setMatAluno(String matAluno) {
		this.matAluno = matAluno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEnquete;
		result = prime * result + ((matAluno == null) ? 0 : matAluno.hashCode());
		result = prime * result + ((resposta == null) ? 0 : resposta.hashCode());
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
		Resposta other = (Resposta) obj;
		if (idEnquete != other.idEnquete)
			return false;
		if (matAluno == null) {
			if (other.matAluno != null)
				return false;
		} else if (!matAluno.equals(other.matAluno))
			return false;
		if (resposta == null) {
			if (other.resposta != null)
				return false;
		} else if (!resposta.equals(other.resposta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Resposta [idEnquete=" + idEnquete + ", resposta=" + resposta + ", matAluno=" + matAluno + "]";
	}
	   
}
