package com.giftok.gateway.payload;

public class CertCreationInfo {
	private int amount;
	private int currencyType;
	private String email;
	private String cardHash;

	public CertCreationInfo() {
	}

	public CertCreationInfo(int amount, int currencyType, String email, String cardHash) {
		this.amount = amount;
		this.currencyType = currencyType;
		this.email = email;
		this.cardHash = cardHash;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(int currencyType) {
		this.currencyType = currencyType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardHash() {
		return cardHash;
	}

	public void setCardHash(String cardHash) {
		this.cardHash = cardHash;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((cardHash == null) ? 0 : cardHash.hashCode());
		result = prime * result + currencyType;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		CertCreationInfo other = (CertCreationInfo) obj;
		if (amount != other.amount)
			return false;
		if (cardHash == null) {
			if (other.cardHash != null)
				return false;
		} else if (!cardHash.equals(other.cardHash))
			return false;
		if (currencyType != other.currencyType)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CertCreationInfo [amount=" + amount + ", currencyType=" + currencyType + ", email=" + email
				+ ", cardHash=" + cardHash + "]";
	}

}
