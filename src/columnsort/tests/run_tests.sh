tests_dir=./testcases

# clear the terminal so testcase output is clear
clear

cd ..

javac Prog3.java

for test in "$tests_dir"/*
do
  echo "Running $test"
  java Prog3 $test > output.txt

  # print out header of the output
  head -n 4 output.txt

  # strip header of output, leaving sort
  tail -n +5 output.txt > sort.txt

  # if output empty, no sort generated
  if ! [ -s sort.txt ]; then
    echo ""
    echo "No sort generated (this is expected if the program errored or when no integers to sort!)"
    echo "---------------"
  # check if sorted correctly
  elif sort -n -c sort.txt; then
    echo "sorted successfully"
    echo "---------------"
  else
    echo "ERROR IN SORT! Wrong placed element printed below"

    # print out the first out of place element.
    sort -nc sort.txt

    echo "---------------"
  fi

done

  echo "Testcase Run Complete!"
  echo "Please be sure to:"
  echo "1. Run this testcase file on lectura for maximum accuracy of results (you want to test your code works on lectura especially!)"
  echo "2. Verify that n, r, and s are correct for each file. You can check n by opening each testcase in your IDE and looking at the number of lines. r and s, you should check by manual calculation against the constraints."
  echo "3. Remember that this script ONLY checks if the output is sorted correctly. It does not validate that your output format is correct, nor does it validate that n, r, or s are correct, be careful!"
  echo "4. The provided testcases should be fairly comprehensive, but you can add more by adding a custom .dat file in the /testcases/ directory, and the script will automatically run it."