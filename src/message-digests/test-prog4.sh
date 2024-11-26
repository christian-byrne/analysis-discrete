#!/bin/bash

# Path to your compiled Java program
JAVA_PROGRAM="Prog4"

# Test cases: Array of test cases with input and expected outputs
# Format: "prime1 prime2 message expected_binary expected_decimal"
TEST_CASES=(
    "254967079 439768741 mylife 100010101001011101001001010101100110100011101111 152382375094511"
    "77074718869384034311184681244637 64590016126231 mylife 111011011001000100100111100101111100000111111111010000101110000100111000110000101101101100110110110110101110001111001101101001001011011111101101 20695005336894104716162983064176832087898093"
    "https://piazza.com/class/lzna9bl5o1s29g/post/323 (comments)"
    "2 2 test 0000000000000000 0"
    "23 7 3st;! 0000000000100000 32"
    "7 11 hashme 0000000001010110 86"
    "503 977 Aust1n 1101110110101001 56745"
    "31 71 abc 0000010011101110 1262"
    "31 71 ABC 0000011000011010 1562"
    "https://piazza.com/class/lzna9bl5o1s29g/post/346 (ty Aaron Lam)"
    "6911 6947 abcdef 0011010100110100 13620"
    "6911 6947 af 1111110110111111 64959"
    "7753 7789 133t! 1010010010110001 42161"
    "2147483647 2147483269 '\$4=M^a' 101111111010111001101000111010001000101101101001 210755805285225" # The dollar sign is escaped
    "8249872901 5858910019 '3f^xh8' 1100100010110110111010110101111000011011101110101000100011011101 14463006042771589341"
    "https://piazza.com/class/lzna9bl5o1s29g/post/342 (ty Ben Curtis and Brianna Yuki)"
    "6911 6947 af 1111110110111111 64959"
    "5 7 abcd 0000000000000111 7"
    "61 19 cat 0000011101001000 1864"
    "53 89 &#(@_+ 0000100111111111 2559"
    "4231 857 yellow 1000111100101000 36648"
    "144403552893599 2860486313 Clouds 0010101100111100010100010110010010000011010001010100101110110110 3115454534374476726"
)

# Colors for output
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[0;33m'
NC='\033[0m' # No Color

# Counters for passed and failed tests
PASSED=0
FAILED=0

# Ensure dependencies are available
if ! command -v java &>/dev/null; then
    echo -e "${RED}Error: Java is not installed or not in the PATH.${NC}"
    exit 1
fi

# Recompile
echo "Compiling $JAVA_PROGRAM.java..."
javac "$JAVA_PROGRAM.java"
if [[ $? -ne 0 ]]; then
    echo -e "${RED}Compilation failed. Please fix the errors and try again.${NC}"
    exit 1
fi
if [[ ! -f "$JAVA_PROGRAM.class" ]]; then
    echo -e "${RED}Error: Compiled Java program ($JAVA_PROGRAM) not found.${NC}"
    exit 1
fi

echo "Running test cases..."

# Current Piazza link
current_link=""
start_time=$(date +%s%3N)  # Start overall timer in milliseconds

# Iterate over each test case
for test_case in "${TEST_CASES[@]}"; do
    # Check if the line is a Piazza link
    if [[ "$test_case" == https://piazza.com/* ]]; then
        # Print the link when starting a new group
        current_link="$test_case"
        echo -e "\n${YELLOW}Running test cases from: $current_link${NC}"
        continue
    fi

    # Split the test case into variables
    IFS=' ' read -r p q message expected_binary expected_decimal <<< "$test_case"

    # Remove single quotes from the message for Java input
    sanitized_message=$(echo "$message" | sed "s/^'//;s/'$//")

    # Run the Java program and capture the output
    output=$(java $JAVA_PROGRAM "$p" "$q" "$sanitized_message" 2>&1)  # Capture stdout and stderr

    # Extract the Binary and Decimal results from the output
    actual_binary=$(echo "$output" | grep "Binary:" | awk '{print $2}')
    actual_decimal=$(echo "$output" | grep "Decimal:" | awk '{print $2}')

    # Compare outputs
    if [[ "$actual_binary" == "$expected_binary" && "$actual_decimal" == "$expected_decimal" ]]; then
        echo -e "${GREEN}  Test passed${NC} for inputs: $p $q $message"
        ((PASSED++))
    else
        echo -e "${RED}  Test failed${NC} for inputs: $p $q $message"
        echo "    Piazza link: $current_link"
        echo "    Expected: Binary=$expected_binary Decimal=$expected_decimal"
        echo "    Got:      Binary=$actual_binary Decimal=$actual_decimal"
        echo "    Full program output:"
        echo "$output"
        ((FAILED++))
    fi
done

# Summary
end_time=$(date +%s%3N)
total_time=$((end_time - start_time))
total_seconds=$((total_time / 1000))
total_milliseconds=$((total_time % 1000))

echo -e "\nTests completed in ${total_seconds}.${total_milliseconds}s."
echo -e "  ${GREEN}Passed: $PASSED${NC}"
echo -e "  ${RED}Failed: $FAILED${NC}"

# Exit with appropriate code
if [[ $FAILED -gt 0 ]]; then
    exit 1
else
    exit 0
fi
