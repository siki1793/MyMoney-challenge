# Description
This is solution for [MyMoney challenge](https://codu.ai/coding-problem/mymoney)

## Requirements
The project is built using Java 1.8. It uses Gradle as the build system.

1. Java - 17 or 1.8 compatible
2. Gradle - 8.0 or use gradle wrapper

## Initial Instruction
- unzip `MyMoney.zip` file to a desired location
- change the unzipped directory
- perform the following, also attached `mymoney-1.0.jar` file just in case
- input files are preset with input1.txt and input2.txt

## Building the application using gradle

You can build and package the application in the form of a jar file using gradle wrapper from the project directory

```
./gradlew clean build
```

The above command will produce a standalone jar file named `mymoney-1.0.jar` in the `build/target` directory.

## Running Tests

The `gradle package` command runs all the unit tests and also packages the application in the form of a jar file. If you just want to run the tests without packaging it, then you use following command.

```
./gradlew clean test
```

## Running the application

You can run the jar file created by the `gradle package` command like so -

```
java -jar build/target/mymoney-1.0.jar
```

## Running the functional tests

The above command launches an interactive shell where you can type various mymoney commands and receive the output on the console.

The application can also take input commands from a file. You can pass multiple commands separated by a newline in the file like so -

```
java -jar build/target/mymoney-1.0.jar input1.txt
```

# Commands to implemented

| Command     | Description                                                 |
|-------------|-------------------------------------------------------------|
| `ALLOCATE`  | The money allocated in equity, debt and gold funds.         |
| `SIP`       | Monthly SIP payments.                                       |
| `CHANGE`    | Monthly change rate (loss or growth) for each type of fund. |
| `BALANCE`   | Balanced amount of each fund for a certain month.           |
| `REBALANCE` | Rebalanced amount of each month if applicable.              |
| `exit`      | Exit from the application.                                  |


# Code Coverage Report

![Code Coverage Report](https://drive.google.com/uc?id=1FV5uSpjIb9f-q5Qxv56VQ_UozQTE7LE_)

# Unit Test Report

![Unit Test Report](https://drive.google.com/uc?id=1wKT1nUYflcUekzUOGCymjzMGni2ftkBS)



# Interactive Example of all the commands
### Example 1
```
$ ALLOCATE 6000 3000 1000

$ SIP 2000 1000 500

$ CHANGE 4.00% 10.00% 2.00% JANUARY

$ CHANGE -10.00% 40.00% 0.00% FEBRUARY

$ CHANGE 12.50% 12.50% 12.50% MARCH

$ CHANGE 8.00% -3.00% 7.00% APRIL

$ CHANGE 13.00% 21.00% 10.50% MAY

$ CHANGE 10.00% 8.00% -5.00% JUNE

$ BALANCE MARCH
10593 7897 2272

$ REBALANCE
23619 11809 3936

$ exit
```

### Example 2
```
$ ALLOCATE 8000 6000 3500

$ SIP 3000 2000 1000

$ CHANGE 11.00% 9.00% 4.00% JANUARY

$ CHANGE -6.00% 21.00% -3.00% FEBRUARY

$ CHANGE 12.50% 18.00% 12.50% MARCH

$ CHANGE 23.00% -3.00% 7.00% APRIL

$ BALANCE MARCH
15937 14552 6187

$ BALANCE APRIL
23292 16055 7690

$ REBALANCE
CANNOT_REBALANCE

$ exit
```