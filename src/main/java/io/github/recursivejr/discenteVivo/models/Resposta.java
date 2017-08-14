package io.github.recursivejr.discenteVivo.models;

public class Resposta {

    private int idResposta;
    private String resposta;
    private int alunoId;

    public Resposta(int idResposta, String resposta, int alunoId) {
        this.idResposta = idResposta;
        this.resposta = resposta;
        this.alunoId = alunoId;
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

	public int getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(int alunoId) {
		this.alunoId = alunoId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + alunoId;
		result = prime * result + idResposta;
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
		if (alunoId != other.alunoId)
			return false;
		if (idResposta != other.idResposta)
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
		return "Resposta [idResposta=" + idResposta + ", resposta=" + resposta + ", alunoId=" + alunoId + "]";
	}

    
    
}
