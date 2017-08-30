package io.github.recursivejr.discenteVivo.models;

public class Opcao {
	
	private int idOpcao;
	private int idEnquete;
	private String opcao;
	
	public Opcao(int idOpcao, int idEnquete, String opcao) {
		this.idOpcao = idOpcao;
		this.idEnquete = idEnquete;
		this.opcao = opcao;
	}
	
	public Opcao() {
		
	}

	public int getIdOpcao() {
		return idOpcao;
	}

	public void setIdOpcao(int idOpcao) {
		this.idOpcao = idOpcao;
	}

	public int getIdEnquete() {
		return idEnquete;
	}

	public void setIdEnquete(int idEnquete) {
		this.idEnquete = idEnquete;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEnquete;
		result = prime * result + idOpcao;
		result = prime * result + ((opcao == null) ? 0 : opcao.hashCode());
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
		Opcao other = (Opcao) obj;
		if (idEnquete != other.idEnquete)
			return false;
		if (idOpcao != other.idOpcao)
			return false;
		if (opcao == null) {
			if (other.opcao != null)
				return false;
		} else if (!opcao.equals(other.opcao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Opcao [idOpcao=" + idOpcao + ", idEnquete=" + idEnquete + ", opcao=" + opcao + "]";
	}

}
