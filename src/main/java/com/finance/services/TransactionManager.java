package com.finance.services;

import java.util.ArrayList;
import java.util.List;

import com.finance.exception.InvalidAmountException;
import com.finance.models.Transaction;

public class TransactionManager {

	private List<Transaction> transactions; // Store all transactions

	// Constructor
	public TransactionManager() {
		transactions = new ArrayList<>();
	}

	// Method to add a new expense
	public void addExpense(double amount, String description, String category) throws InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException("Expense amount must be positive.");
		}

		// Add the expense to the list of transactions
		Transaction expense = new Transaction(amount, new java.util.Date(), description, category);
		transactions.add(expense);
	}

	// Method to update an existing transaction (expense)
	public void updateTransaction(int index, double amount, String description, String category)
			throws InvalidAmountException {
		if (index < 0 || index >= transactions.size()) {
			throw new IndexOutOfBoundsException("Transaction not found at index: " + index);
		}

		Transaction transaction = transactions.get(index);

		// Validation checks
		if (amount <= 0) {
			throw new InvalidAmountException("Amount must be positive.");
		}

		// Update transaction
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setCategory(category);
	}

	// Method to get the list of all transactions (expenses)
	public List<Transaction> getAllTransactions() {
		return transactions;
	}

	// Method to get the list of all transactions (expenses) for a specific category
	public List<Transaction> getTransactionsByCategory(String category) {
		List<Transaction> filteredTransactions = new ArrayList<>();
		for (Transaction transaction : transactions) {
			if (transaction.getCategory().equals(category)) {
				filteredTransactions.add(transaction);
			}
		}
		return filteredTransactions;
	}

	// Method to get the balance (income minus total expenses)
	public double getBalance(double monthlyIncome) {
		double expenseTotal = 0;

		for (Transaction transaction : transactions) {
			expenseTotal += transaction.getAmount();
		}

		return monthlyIncome - expenseTotal;
	}
}
