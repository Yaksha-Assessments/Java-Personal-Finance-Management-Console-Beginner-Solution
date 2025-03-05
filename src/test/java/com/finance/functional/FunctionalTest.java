package com.finance.functional;

import static com.finance.testutils.TestUtils.businessTestFile;
import static com.finance.testutils.TestUtils.currentTest;
import static com.finance.testutils.TestUtils.testReport;
import static com.finance.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.finance.models.Transaction;
import com.finance.services.TransactionManager;

public class FunctionalTest {

	private TransactionManager transactionManager;

	@BeforeEach
	public void setUp() {
		transactionManager = new TransactionManager();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddExpense() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			yakshaAssert(currentTest(), transactionManager.getAllTransactions().size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetAllTransactions() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			List<Transaction> transactions = transactionManager.getAllTransactions();
			yakshaAssert(currentTest(), transactions.size() == 1, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetTransactionsByCategory() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			transactionManager.addExpense(200.0, "Snacks", "Food");
			List<Transaction> transactions = transactionManager.getTransactionsByCategory("Food");
			yakshaAssert(currentTest(), transactions.size() == 2, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testUpdateTransaction() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			Transaction updatedTransaction = transactionManager.getAllTransactions().get(0);
			transactionManager.updateTransaction(0, 600.0, "Updated Groceries", "Food");
			Transaction updated = transactionManager.getAllTransactions().get(0);
			yakshaAssert(currentTest(), updated != null && updated.getAmount() == 600.0, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}

	@Test
	public void testGetBalance() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			double balance = transactionManager.getBalance(10000);
			yakshaAssert(currentTest(), balance == 9500, businessTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, businessTestFile);
		}
	}
}
