import sys
import csv

def verify_credentials(email, password):
    with open("credentials/credentials.csv", mode="r") as file:
        reader = csv.reader(file)
        next(reader)  # Skip the header row
        for row in reader:
            if row[0] == email:
                if row[1] == password:
                    return f"Login successful! Account Type: {row[2]}"
                else:
                    return "Incorrect password!"
    return "Email not found!"

if __name__ == "__main__":
    # Get email and password from the command-line arguments
    email = sys.argv[1]
    password = sys.argv[2]

    # Verify credentials and print the result
    result = verify_credentials(email, password)
    print(result)


