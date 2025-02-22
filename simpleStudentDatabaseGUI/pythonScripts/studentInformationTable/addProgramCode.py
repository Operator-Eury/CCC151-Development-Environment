import os
import csv
import json

script_dir = os.path.dirname(os.path.abspath(__file__))
csv_path = os.path.join(script_dir, "programs", "programs.csv")

def read_csv():
    rows = []
    with open(csv_path, mode="r", encoding='utf-8-sig') as file:
        reader = csv.DictReader(file)
        for row in reader:
            rows.append(row)
    return rows

if __name__ == "__main__":
    data = read_csv()
    print("Data written successfully.")
