import os
import csv
import json

script_dir = os.path.dirname(os.path.abspath(__file__))
csv_path = os.path.join(script_dir, "students", "students.csv")
json_path = os.path.join(script_dir, "students", "students.json")

def read_csv():
    rows = []
    with open(csv_path, mode="r", encoding='utf-8-sig') as file:
        reader = csv.DictReader(file)
        for row in reader:
            rows.append(row)
    return rows

def write_json(data, file_path):
    with open(json_path, mode="w", encoding='utf-8-sig') as file:
        json.dump(data, file, indent=4, ensure_ascii=False)

if __name__ == "__main__":
    data = read_csv()
    write_json(data, json_path)
    print("Data written successfully.")