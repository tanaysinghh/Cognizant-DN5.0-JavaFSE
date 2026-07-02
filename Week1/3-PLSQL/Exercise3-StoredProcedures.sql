-- Exercise 3: Stored Procedures

-- Scenario 1: Apply 1% monthly interest to all savings accounts.
CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
BEGIN
    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01)
    WHERE AccountType = 'SAVINGS';
    COMMIT;
END;
/

-- Scenario 2: Add a bonus % to the salary of everyone in a given department.
CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(
    p_department IN VARCHAR2,
    p_bonusPercent IN NUMBER
) AS
BEGIN
    UPDATE Employees
    SET Salary = Salary + (Salary * p_bonusPercent / 100)
    WHERE Department = p_department;
    COMMIT;
END;
/

-- Scenario 3: Transfer funds between two accounts, checking balance first.
CREATE OR REPLACE PROCEDURE TransferFunds(
    p_fromAccount IN NUMBER,
    p_toAccount IN NUMBER,
    p_amount IN NUMBER
) AS
    v_balance NUMBER;
BEGIN
    -- read the source account balance
    SELECT Balance INTO v_balance
    FROM Accounts
    WHERE AccountID = p_fromAccount;

    IF v_balance >= p_amount THEN
        UPDATE Accounts SET Balance = Balance - p_amount WHERE AccountID = p_fromAccount;
        UPDATE Accounts SET Balance = Balance + p_amount WHERE AccountID = p_toAccount;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Transfer successful.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Insufficient balance. Transfer cancelled.');
    END IF;
END;
/
