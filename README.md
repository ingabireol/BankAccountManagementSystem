# Bank Account Management System

A JavaFX application that simulates a banking system with multiple account types, transaction processing, and account management features.


### Features

- **Multiple Account Types:**
  - Savings Account - Requires minimum balance, earns interest
  - Current Account - Supports overdraft facility
  - Fixed Deposit Account - Higher interest rate, withdrawal restrictions until maturity

- **Core Banking Operations:**
  - Deposit funds into accounts
  - Withdraw funds with appropriate validations
  - View account balance and details
  - Track transaction history

- **User Interface:**
  - JavaFX-based graphical user interface
  - Account creation and management
  - Transaction processing
  - Transaction history visualization

## Technology Stack

- Java 21
- JavaFX for UI components
- Maven for dependency management

## Object-Oriented Programming Concepts Demonstrated

### 1. Inheritance
The account hierarchy demonstrates inheritance:
- `Account` (abstract base class)
  - `SavingsAccount` (extends Account)
  - `CurrentAccount` (extends Account)
  - `FixedDepositAccount` (extends Account)

### 2. Polymorphism
Methods like `deposit()` and `withdraw()` exhibit polymorphic behavior across different account types:
- `SavingsAccount` prevents withdrawal below minimum balance
- `CurrentAccount` allows overdraft up to a limit
- `FixedDepositAccount` prevents withdrawal before maturity

### 3. Abstraction
- `Account` is an abstract class that defines the blueprint for all account types
- `IBankOperations` interface defines the common banking operations

### 4. Encapsulation
- All classes use private fields with public getters/setters
- Implementation details are hidden from other classes

### 5. Data Structures
- `TransactionHistory` implements a linked list to efficiently manage transaction records

## Getting Started

### Prerequisites
- JDK 21 or higher
- Maven 3.8.5 or higher

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Build the project:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn javafx:run
   ```
   
   Alternatively, use the included Maven wrapper:
   ```
   ./mvnw javafx:run    # On Unix-based systems
   mvnw.cmd javafx:run  # On Windows
   ```

### Login Credentials
- Username: `admin`
- Password: `password`

## Application Workflow

1. **Login Screen**
   - Enter admin credentials to access the system

2. **Account Selection Screen**
   - Access existing accounts by account number
   - Create new accounts with different types and configurations

3. **Account Operations Screen**
   - View account details and balance
   - Deposit and withdraw funds
   - View transaction history

## Implementation Details

### Account Types

#### Savings Account
- Requires minimum balance
- Earns interest at a specified rate
- Cannot withdraw below minimum balance

#### Current Account
- Supports overdraft facility
- Applies overdraft fees when account goes negative
- Allows flexible deposits and withdrawals

#### Fixed Deposit Account
- Fixed term investment
- Higher interest rate
- No withdrawals allowed before maturity date
- No additional deposits allowed after creation

### Transaction Management
- Each transaction is recorded with:
  - Unique ID
  - Amount
  - Type (deposit, withdrawal, interest, fee)
  - Date and time
  - Description
- Transactions are stored in a linked list for efficient insertion

### Exception Handling
Custom exceptions are used to handle specific banking scenarios:
- `InsufficientFundsException` - When withdrawal exceeds available balance
- `MinimumBalanceException` - When withdrawal would go below minimum balance
- `ImmatureWithdrawalException` - When trying to withdraw from a fixed deposit before maturity

