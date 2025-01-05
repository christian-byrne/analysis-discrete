fn gen_bit_strings(n: u32, current: String, result: &mut Vec<String>) {
    if n == 0 {
        result.push(current);
    } else {
        gen_bit_strings(n - 1, current.clone() + "0", result);
        gen_bit_strings(n - 1, current.clone() + "1", result);
    }
}


fn has_consecutive_zeros(s: &str) -> bool {
    if s.len() < 2 {
        return false;
    }

    let bytes = s.as_bytes(); // Convert to byte slice for efficient indexing

    // Check the first two characters (bits)
    if bytes[0] == b'0' && bytes[1] == b'0' {
        return true;
    }

    // Recursively check the rest of the string
    has_consecutive_zeros(&s[1..])
}

fn main() {
    // let n = 5;
    // let mut result = Vec::new();
    // gen_bit_strings(n, "".to_string(), &mut result);
    // for s in &result {
    //     println!("{}", s);
    // }
    // // Print the length and assert that it is correct
    // let actual_length = result.len();
    // let expected_length = 2u32.pow(n);
    // println!("Expected length: {}, Actual length: {}", expected_length, actual_length);

    let s = "010101111";
    println!("Has consecutive zeros: {}", has_consecutive_zeros(s));
    let s2 = "0101010111";
    println!("Has consecutive zeros: {}", has_consecutive_zeros(s2));
    let s3 = "01";
    println!("Has consecutive zeros: {}", has_consecutive_zeros(s3));
    let s4 = "0";
    println!("Has consecutive zeros: {}", has_consecutive_zeros(s4));
    let s5 = "1111011";
    println!("Has consecutive zeros: {}", has_consecutive_zeros(s5));
    // Now test for a string with consecutive zeros
    let s6 = "0011";
    println!("Has consecutive zeros: {}", has_consecutive_zeros(s6));
}
 
