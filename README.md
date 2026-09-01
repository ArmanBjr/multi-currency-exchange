# ArmanReza Exchange

JavaFX desktop application for a multi-currency exchange platform — university advanced programming project (Ferdowsi University of Mashhad).

Users can register, sign in, manage a wallet, trade currencies (USD, EUR, GBP, Toman, Yen), view charts, place orders, and reset passwords via email.

## Tech stack

- **Java 11** + **JavaFX 17** (FXML)
- **MySQL** for users, wallets, rates, and orders
- **Apache POI** — order export to Excel
- **OpenCSV** — historical rate import
- **Commons Math** — currency rate forecasting
- **JavaMail** — password reset emails (optional)

## Prerequisites

- JDK 11+
- Maven 3.8+
- MySQL 8.x

## Setup

1. **Clone and configure environment**

   ```bash
   git clone https://github.com/ArmanBjr/ArmanReza.git
   cd ArmanReza
   cp .env.example .env   # then export variables (see below)
   ```

2. **Create the database**

   ```sql
   CREATE DATABASE exchange;
   ```

   Apply the schema:

   ```bash
   mysql -u root -p exchange < docs/schema.sql
   ```

3. **Import sample currency rates** (optional)

   ```bash
   mvn -q exec:java -Dexec.mainClass="com.example.demo1.User.ExcelToDatabase"
   ```

   Uses `src/main/resources/Data/price.csv`.

4. **Environment variables**

   | Variable | Default | Description |
   |----------|---------|-------------|
   | `DB_URL` | `jdbc:mysql://localhost:3306/exchange` | MySQL JDBC URL |
   | `DB_USER` | `root` | Database user |
   | `DB_PASSWORD` | *(empty)* | Database password |
   | `SMTP_USER` | — | Gmail address for password reset |
   | `SMTP_APP_PASSWORD` | — | Gmail app password |

   On Windows PowerShell:

   ```powershell
   $env:DB_PASSWORD = "your-mysql-password"
   $env:SMTP_USER = "your@gmail.com"
   $env:SMTP_APP_PASSWORD = "your-app-password"
   ```

## Run

**Client (JavaFX UI):**

```bash
mvn clean javafx:run
```

**Socket login server** (optional, port 12345):

```bash
mvn -q exec:java -Dexec.mainClass="com.example.demo1.Server.Server"
```

## Project layout

```
src/main/java/com/example/demo1/
├── SignApplication.java      # App entry point
├── HomePageController.java   # Main dashboard & trading
├── CurrencyManagement/       # Wallet & orders
├── CoinPages/                # Per-currency charts
├── Server/                   # TCP auth server & admin
└── User/                     # Models, DAO, forecasting

src/main/resources/
├── com/example/demo1/        # FXML & CSS
├── Data/price.csv            # Sample rate history
└── images/
```

## Authors

**Arman Bijari** & **Reza** — [GitHub](https://github.com/ArmanBjr)

## License

Educational project — provided as-is for portfolio reference.
