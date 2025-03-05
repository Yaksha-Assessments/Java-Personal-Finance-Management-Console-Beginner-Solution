package com.finance.exception;

import static com.finance.testutils.TestUtils.currentTest;
import static com.finance.testutils.TestUtils.exceptionTestFile;
import static com.finance.testutils.TestUtils.testReport;
import static com.finance.testutils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.finance.models.Transaction;
import com.finance.services.TransactionManager;

class TransactionManagerExceptionTest {

	private TransactionManager transactionManager;

	@BeforeEach
	public void setUp() {
		transactionManager = new TransactionManager();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	// Test for InvalidAmountException when adding an expense with a non-positive
	// amount
	@Test
	public void testAddExpenseWithInvalidAmount() throws IOException {
		try {
			transactionManager.addExpense(-500.0, "Invalid Amount Expense", "Food");
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (InvalidAmountException ex) {
			Pattern pattern = Pattern.compile("(?i)amount\\s*must\\s*be\\s*positive\\.*");
			yakshaAssert(currentTest(), pattern.matcher(ex.getMessage()).find(), exceptionTestFile);
		}
	}

	// Test for InvalidAmountException when updating an expense with a non-positive
	// amount
	@Test
	public void testUpdateTransactionWithInvalidAmount() throws IOException {
		try {
			transactionManager.addExpense(500.0, "Groceries", "Food");
			Transaction transaction = transactionManager.getAllTransactions().get(0);
			transactionManager.updateTransaction(0, -100.0, "Invalid Update", "Food");
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (InvalidAmountException ex) {
			Pattern pattern = Pattern.compile("(?i)amount\\s*must\\s*be\\s*positive\\.*");
			yakshaAssert(currentTest(), pattern.matcher(ex.getMessage()).find(), exceptionTestFile);
		} catch (Exception ex) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}

	// Test for IndexOutOfBoundsException when updating an expense with an invalid
	// index
	@Test
	public void testUpdateTransactionWithInvalidIndex() throws IOException {
		try {
			transactionManager.updateTransaction(-1, 500.0, "Invalid Index Update", "Food");
			yakshaAssert(currentTest(), false, exceptionTestFile);
		} catch (IndexOutOfBoundsException ex) {
			System.out.println(ex.getMessage());
			Pattern pattern = Pattern.compile("(?i)transaction\\s*not\\s*found\\s*at\\s*index\\s*:\\s*-1");
			yakshaAssert(currentTest(), pattern.matcher(ex.getMessage()).find(), exceptionTestFile);
		} catch (InvalidAmountException e) {
			yakshaAssert(currentTest(), false, exceptionTestFile);
		}
	}
}
