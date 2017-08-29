package io.github.recursivejr.discenteVivo.models;

public class Resposta {

    private int idResposta;
    private String resposta;
    private String matAluno;
    
	public Resposta(int idResposta, String resposta, String matAluno) {
		this.idResposta = idResposta;
		this.resposta = resposta;
		this.matAluno = matAluno;
	}
    
    public Resposta() {
    	
    }

	public int getIdResposta() {
		return idResposta;
	}

	public void setIdResposta(int idResposta) {
		this.idResposta = idResposta;
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
		result = prime * result + idResposta;
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
		if (idResposta != other.idResposta)
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
		return "Resposta [idResposta=" + idResposta + ", resposta=" + resposta + ", matAluno=" + matAluno + "]";
	}
    
}
