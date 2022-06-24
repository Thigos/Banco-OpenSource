import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Scanner;

import Modules.Cliente;
import Modules.Conta;

public class Test {
	float JUROS = 0.5f;
	float CDI = 12.65f;
	
	private Cliente poupanca(Cliente cliente) {
		int anoDeposito = cliente.getDataCriacao().get(Calendar.YEAR);
		int mesDeposito = cliente.getDataCriacao().get(Calendar.MONTH);
		Calendar now = Calendar.getInstance();
		int difAno = now.get(Calendar.YEAR) - anoDeposito;
		int difMes = now.get(Calendar.MONTH) - mesDeposito;

		double porcentJuros = JUROS*(difMes+(difAno*12));
		
		System.out.println("Juros Recebido: " + (cliente.saldo()*porcentJuros)/100);
		
		double valor = ((cliente.saldo()*porcentJuros)/100) + cliente.saldo();
		cliente.deposito(valor);
		
		return cliente;
	}
	
	private double previsao(float valorAplicacao, float valoMensal, Calendar dtInic, Calendar dtFinal) {
		int difAno = dtFinal.get(Calendar.YEAR) - dtInic.get(Calendar.YEAR);
		int difMes = dtFinal.get(Calendar.MONTH) - dtInic.get(Calendar.MONTH);
		DecimalFormat df = new DecimalFormat("#.00");
		DecimalFormatSymbols decimalFormatSymbols = DecimalFormatSymbols.getInstance();
		decimalFormatSymbols.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(decimalFormatSymbols);
		
		int meses = difMes+(difAno*12);
		int dias = meses*30;
		
		float porcentJuros = CDI/12;
		
		float valorReal = valorAplicacao;
		
		float jurosTotal = 0f;
		
		
		for (int i = 1; i<= meses; i++) {
			float juros = (valorReal*porcentJuros)/100;
			valorReal = valorReal+valoMensal+juros;
			
			jurosTotal = juros+jurosTotal;
			
		}
		
		
		System.out.println("Valor Investido: R$" + (valorAplicacao+(valoMensal*meses)));
		System.out.println("Juros Total: R$" +jurosTotal);
		
		double ir = 0;
		
		if(dias <= 180) {
			ir = jurosTotal*0.225;
		}else if(dias <= 360) {
			ir = jurosTotal*0.2;
		}else if(dias <= 720) {
			ir = jurosTotal*0.175;
		}else if(dias > 720) {
			ir = jurosTotal*0.15;
		}
		
		System.out.println("IR: R$" + ir);
		
		return valorReal-ir;
		
	}
	
	@org.junit.Test
	public void Teste() {
		Calendar dataCriacao = Calendar.getInstance();
		dataCriacao.set(Calendar.MONTH, Calendar.JUNE);
		dataCriacao.set(Calendar.YEAR, 2021);
		Conta conta = new Conta();
		Cliente cliente = new Cliente("Teste", "000000", "00000", "000", conta, dataCriacao);
		
		int deposito = 1500;
		
		cliente.deposito(deposito);
		System.out.println(cliente.saldo());
				
		assertTrue(cliente.saldo() == cliente.getConta().getSaldo());
		
		cliente = poupanca(cliente);
		System.out.println("Cliente com conta Poupança: " + cliente.saldo());
		
		assertTrue(poupanca(cliente).saldo() == cliente.saldo());
		
		System.out.println("Previsão: "+ previsao(1000f, 100f, dataCriacao, Calendar.getInstance()));
		
		int saque = 200;
		assertFalse(saque > conta.getSaldo());
		cliente.saque(saque);
	}
	
}
