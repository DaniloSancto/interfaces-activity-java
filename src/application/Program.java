package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.Contract;
import model.entities.Installment;
import model.services.ContractService;
import model.services.PaypalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		System.out.println("Entre os dados do contrato:");
		System.out.print("Numero: ");
		int number = sc.nextInt();
		System.out.print("Data (dd/MM/yyyy): ");
		sc.nextLine();
		LocalDate date = LocalDate.parse(sc.nextLine(), fmt);
		System.out.print("Valor do contrato: ");
		double value = sc.nextDouble();
		
		Contract contract = new Contract(number, date, value);
		
		System.out.print("Entre com o numero de parcelas: ");
		int months = sc.nextInt();
		
		ContractService service = new ContractService(new PaypalService());		
		service.processContract(contract, months);
		
		System.out.println("Parcelas:");
		
		for(Installment installment : contract.getInstallments()) {
			System.out.println(fmt.format(installment.getDueDate()) + " - " + String.format("%.2f", installment.getAmount()));
		}
		
		sc.close();
	}
	
}
