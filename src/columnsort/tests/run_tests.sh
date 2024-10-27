#!/bin/bash

# Set this flag to `true` to print only failed test cases.
PRINT_ONLY_FAILURES=true

# clear the terminal so testcase output is clear
clear

cd ..

javac Prog3.java

for test in "tests/testcases/"*.dat;
do
  echo "Running $test"
  # Always print the test file being run
  if ! $PRINT_ONLY_FAILURES; then
    echo "Running $test"
  fi

  java Prog3 $test > output.txt

  # Only print the header if not in failure-only mode
  if ! $PRINT_ONLY_FAILURES; then
    head -n 4 output.txt
  fi

  # Strip header of output, leaving sort data
  tail -n +5 output.txt > sort.txt

  # Check if output is empty, indicating no sort generated
  if ! [ -s sort.txt ]; then
    if ! $PRINT_ONLY_FAILURES; then
      echo ""
      echo "No sort generated (this is expected if the program errored or when no integers to sort!)"
      echo "---------------"
    fi
  # Check if sorted correctly
  elif sort -n -c sort.txt; then
    # Successful sort
    if ! $PRINT_ONLY_FAILURES; then
      echo "Sorted successfully"
      echo "---------------"
    fi
  else
    # Failed sort - Always print error details regardless of the flag
    head -n 4 output.txt
    echo "ERROR IN SORT! Wrong placed element printed below"
    sort -nc sort.txt  # Print the first out-of-place element
    echo "---------------"
  fi

done

# Summary messages should only be printed when not in failure-only mode
if ! $PRINT_ONLY_FAILURES; then
  echo "Testcase Run Complete!"
  echo "Please be sure to:"
  echo "1. Run this testcase file on lectura for maximum accuracy of results (you want to test your code works on lectura especially!)"
  echo "2. Verify that n, r, and s are correct for each file. You can check n by opening each testcase in your IDE and looking at the number of lines. r and s, you should check by manual calculation against the constraints."
  echo "3. Remember that this script ONLY checks if the output is sorted correctly. It does not validate that your output format is correct, nor does it validate that n, r, or s are correct, be careful!"
  echo "4. The provided testcases should be fairly comprehensive, but you can add more by adding a custom .dat file in the /testcases/ directory, and the script will automatically run it."
fi
