import os
import xmltodict
import json

# Read the XML data from environment variable
xml_data = os.environ.get('XML_DATA')

# Parse the XML data into a Python data structure
data_dict = xmltodict.parse(xml_data)

# Convert the Python data structure into JSON
json_data = json.dumps(data_dict)

# Write the JSON data to stdout
print(json_data)
