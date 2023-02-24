package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePaymentService;

	public ContractService(OnlinePaymentService onlinePaymentService) {
		this.onlinePaymentService = onlinePaymentService;
	}

	public void processContract(Contract contract, int months) {
		
		LocalDate date = contract.getDate();
		double installment = contract.getTotalValue() / months;
		
		for (int i = 1; i <= months; i++) {
			LocalDate plusDate = date.plusMonths(i);
			
			double interestValue = onlinePaymentService.interest(installment, i);
			double paymentFeeValue = onlinePaymentService.paymentFee(installment + interestValue);
			
			double tax = interestValue + paymentFeeValue;
			
			contract.addInstallments(new Installment(plusDate, installment + tax));
		}

	}
	
}
