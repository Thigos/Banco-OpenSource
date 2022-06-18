package Modules;

import java.util.Calendar;

public class Cliente {
	private String nome, rg, cpf, dtNasc;
	private Conta conta;
	private Calendar dataCriacao;
	
	public Cliente(String nome, String rg, String cpf, String dtNasc, Conta conta, Calendar dataCriacao) {
		this.nome = nome;
		this.rg = rg;
		this.cpf = cpf;
		this.dtNasc = dtNasc;
		this.conta = conta;
		this.dataCriacao = dataCriacao;
	}
	
	public Calendar getDataCriacao() {
		return dataCriacao;
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public void saque(double valor) {
		conta.setSaldo(valor);
	}
	
	public void deposito(double valor) {
		conta.setSaldo(valor);
	}
	
	public double saldo() {
		return conta.getSaldo();
	}
	
	
}
