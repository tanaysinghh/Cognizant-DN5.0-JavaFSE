-- Exercise 1: Control Structures

-- Scenario 1: Give customers above 60 a 1% discount on their loan interest rate.
BEGIN
    FOR c IN (SELECT CustomerID, DOB FROM Customers) LOOP
        -- MONTHS_BETWEEN/12 gives age in years
        IF MONTHS_BETWEEN(SYSDATE, c.DOB) / 12 > 60 THEN
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = c.CustomerID;
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- Scenario 2: Flag customers with balance over 10000 as VIP.
BEGIN
    FOR c IN (SELECT CustomerID, Balance FROM Customers) LOOP
        IF c.Balance > 10000 THEN
            UPDATE Customers
            SET IsVIP = 'Y'
            WHERE CustomerID = c.CustomerID;
        END IF;
    END LOOP;
    COMMIT;
END;
/

-- Scenario 3: Print a reminder for every loan due in the next 30 days.
BEGIN
    FOR l IN (
        SELECT CustomerID, LoanID, EndDate
        FROM Loans
        WHERE EndDate BETWEEN SYSDATE AND SYSDATE + 30
    ) LOOP
        DBMS_OUTPUT.PUT_LINE(
            'Reminder: Customer ' || l.CustomerID ||
            ' has loan ' || l.LoanID ||
            ' due on ' || TO_CHAR(l.EndDate, 'DD-MON-YYYY')
        );
    END LOOP;
END;
/
