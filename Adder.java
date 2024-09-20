
class Adder {
  public static void main(String[] args) {
    int a = 10;
    int b = 20;
    int sum = a + b;
    System.out.println("Sum of " + a + " and " + b + " is " + sum);
  }

  public static String noCarryAddition(String a, String b) {
    if (a == null && b == null) {
      return null;
    }
    if (a == null) {
      return b;
    }
    if (b == null) {
      return a;
    }
    if (a.isEmpty() && b.isEmpty()) {
      return "";
    }
    char[] aCharList = a.toCharArray();
    int aLength = aCharList.length;

    char[] bCharList = b.toCharArray();
    int bLength = bCharList.length;

    char[] sumCharList = new char[Math.max(aLength, bLength)];

    int maxLength = Math.max(aLength, bLength);
    for (int i = 0; i < maxLength; i++) {
      if (i > aLength - 1) {
        sumCharList[i] = bCharList[i];
      } else if (i > bLength - 1) {
        sumCharList[i] = aCharList[i];
      } else {
        int sum = Character.getNumericValue(aCharList[i]) + Character.getNumericValue(bCharList[i]);
        sum = sum % 10;
        sumCharList[i] = Character.forDigit(sum, 10);
      }
    }

    return new String(sumCharList);
  }
}