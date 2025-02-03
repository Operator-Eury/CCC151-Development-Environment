import sys
import csv
import os

# Get the directory of the script
script_dir = os.path.dirname(os.path.abspath(__file__))

# Construct the correct path to credentials.csv
csv_path = os.path.join(script_dir, "credentials", "credentials.csv")

def verify_credentials(email, password):
    if not os.path.exists(csv_path):
        return f"FileNotFoundError: CSV file not found at {csv_path}"

    with open(csv_path, mode="r") as file:
        reader = csv.reader(file)
        next(reader)  # Skip the header row
        for row in reader:
            if row[0] == email:
                return f"Login successful! Account Type: {row[2]}" if row[1] == password else "Incorrect password!"
    return "Email not found!"

if __name__ == "__main__":
    email = sys.argv[1]
    password = sys.argv[2]

    # Verify credentials and print the result
    result = verify_credentials(email, password)
    print(result)