package com.finance.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.exception.InvalidAmountException;
import com.finance.exception.InvalidCategoryException;
import com.finance.models.Transaction;

public class TransactionManager {

	private List<Transaction> transactions; // Store all transactions
	private Map<String, Double> categoryBudgets; // Store budgets for each category (not used anymore)

	// Constructor
	public TransactionManager() {
		transactions = new ArrayList<>();
		categoryBudgets = new HashMap<>();
	}

	// Method to add a new expense
	public void addExpense(double amount, String description, String category)
			throws InvalidAmountException, InvalidCategoryException {
		if (amount <= 0) {
			throw new InvalidAmountException("Expense amount must be positive.");
		}

		// If the category doesn't exist, create a new one (no budget check needed)
		if (!categoryBudgets.containsKey(category)) {
			categoryBudgets.put(category, 0.0); // Default category with no budget
		}

		// Add the expense to the list of transactions
		Transaction expense = new Transaction(amount, new java.util.Date(), description, category);
		transactions.add(expense);
	}

	// Method to update an existing transaction (expense)
	public void updateTransaction(int index, double amount, String description, String category)
			throws InvalidAmountException, InvalidCategoryException {
		if (index < 0 || index >= transactions.size()) {
			throw new IndexOutOfBoundsException("Transaction not found at index: " + index);
		}

		Transaction transaction = transactions.get(index);

		// Validation checks
		if (amount <= 0) {
			throw new InvalidAmountException("Amount must be positive.");
		}
		if (!categoryBudgets.containsKey(category)) {
			throw new InvalidCategoryException("Invalid category: " + category);
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
